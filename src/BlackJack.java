import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe qui gere les joueurs, le croupier et calcule les scores
 * @author mathieu
 * 
 */
public class BlackJack {
	
	private JeuDeCarte jeu;
	protected HashMap<String, Joueur> lesJoueurs;
	protected LinkedList<Joueur> enAttente; 
	private Croupier croupier;
	private boolean enCours;
	private Table table;
	
	/**
	 * Constructeur
	 * Cree la liste des joueurs, un croupier ainsi qu'un jeu de carte
	 * @see Joueur, JeuDeCarte
	 */
	public BlackJack(Table table) {
		lesJoueurs = new HashMap<String, Joueur>();
		enAttente = new LinkedList<Joueur>();
		Croupier croupier = new Croupier();
		this.croupier = croupier;
		this.jeu = new JeuDeCarte();
		this.enCours = false;
		this.table = table;
	}
	
	/**
	 * Ajoute un joueur dans la partie
	 * @param numJoueur
	 * 		Le nom du joueur
	 * @param srv
	 * 		L'interface du joueur
	 */
	public void creerJoueur(String numJoueur, Client srv) {
		if(this.enCours) {
			//partie en cours mets le joueur en attente de la fin de la partie
			System.out.println(numJoueur+" rejoins la file d'attente");
			this.enAttente.add(new Joueur(numJoueur, srv));
			try {
				this.enAttente.getLast().srv.afficherTexte("Une partie est en cours. En attente de fin de la partie");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		else {
			//Lancement du timer d'attente si c'est le premier joueur
			if(this.lesJoueurs.isEmpty()) {
				//Ajout du joueur à la partie
				this.lesJoueurs.put(numJoueur, new Joueur(numJoueur, srv));
				this.informJoueurs("Le joueur "+numJoueur+" rejoins la partie\nelle commence dans 10s");
				System.out.println("En attente d'autres joueurs (10s)");
				//Attente de 10 secondes
				new CountDown(10);
				this.enCours = true;
				System.out.println("La partie commence !");
				this.informJoueurs("La partie commence !");
				this.distribuerJoueur();
				this.distribuerCroupier();
			}
			else {
				//Ajout du joueur à la partie
				this.lesJoueurs.put(numJoueur, new Joueur(numJoueur, srv));
				this.informJoueurs("Le joueur "+numJoueur+" rejoins la partie");
			}
		}
	}
	
	/**
	 * Distribution cartes par le croupier deux par personne
	 * 
	 */
	public void distribuerJoueur() {
		//tant que un joueur n'a pas deux cartes, lui donner une carte
		System.out.println("tirage des joueurs");
		for(Joueur joueur : this.lesJoueurs.values()) {
			System.out.println("tirage du joueur "+joueur.getNumJoueur());
			do {
				this.lesJoueurs.get(joueur.getNumJoueur()).hit(jeu);
			} while(this.lesJoueurs.get(joueur.getNumJoueur()).getMain().size() != 2);
		}
	}
	
	/**
	 * Le croupier pioche ses cartes
	 */
	public void distribuerCroupier() {
		System.out.println("tirage du croupier");
		this.informJoueurs("Le croupier pioche");
		//Tirage du croupier
		this.croupier.hit(jeu);
		this.informJoueurs("Le croupier a pioché :"+this.croupier.afficheMain());
		this.croupier.hit(jeu);
	}
	
	/**
	 * Hit : Demande de carte par un joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void hit(String numJoueur) {
		//Demande une carte pour un joueur si il n'a pas encore stand
		if(this.lesJoueurs.get(numJoueur).isStand() == false)
		{
			this.lesJoueurs.get(numJoueur).hit(jeu);
		}
	}
	
	/**
	 * Elimination du joueur si son score est superieur à 21
	 * Il est enleve de la liste des joueurs en jeu
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void elimination(String numJoueur) {
		//Si le score du joueur dépasse 21 alors il est éliminé
		if(lesJoueurs.get(numJoueur).EstElimine()) {
			try {
				this.lesJoueurs.get(numJoueur).srv.afficherMainJoueur("Vous avez été éliminé...vous quittez la table");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			this.informJoueurs("Le joueur "+numJoueur+" a ete elimine");
			System.out.println("Joueur "+numJoueur+" a ete elimine");
			this.table.quitTable(numJoueur);
			//Si c'est le seul joueur en jeu alors la partie recommence avec les joueurs en attente
			if(this.lesJoueurs.isEmpty()) {
				this.recommencerPartie();
			}
		}
	}
	
	/**
	 * Stand : Arret du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void stand(String numJoueur) {
		//Un joueur s'arrete (ne demande plus de carte)
		this.lesJoueurs.get(numJoueur).setStand(true);
		//Tirage du croupier seulement si tout les joueurs sont stand
		if(this.allStand() && !this.croupier.isStand()) {
			this.tirageCroupier();
			this.calculGain();
			this.recommencerPartie();
		}
	}
	
	/**
	 * Tirage du croupier
	 * le croupier tire des cartes selon les regles definie.
	 * Si son score est de 16 ou moins il tire une carte sinon il stand
	 */
	public void tirageCroupier() {
		//Tirage de carte du croupier
		this.informJoueurs("Tirage du croupier");
		while(this.croupier.calculScore() < 17) {
			if(this.croupier.calculScore() <= 16) {
				Carte carte = jeu.TireCarte();
				this.croupier.getMain().add(carte);
			}
		}
		this.croupier.setStand(true);
	}	
	
	/**
	 * Comptage des gains en comparant les scores des joueurs avec celui du croupier
	 *  
	 */
	public void calculGain() {
		//Compare les scores joueur/banque
		if(this.croupier.isStand() == true) {
			//Quatre cas de figure
				for(Joueur joueur : this.lesJoueurs.values()) {
					int scoreJoueur = this.lesJoueurs.get(joueur.getNumJoueur()).calculScore();
					int mise = this.lesJoueurs.get(joueur.getNumJoueur()).getMiseActuelle();
					int miseGagnee = mise*2;
					//croupier perdant
					if(this.croupier.calculScore() > 21) {
						//Tout les joueurs gagnent
						System.out.println("Tout les joueurs gagnent");
						this.table.gestionMises.ajoutCredit(joueur.getNumJoueur(), miseGagnee);
						try {
							joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier");
							joueur.srv.afficherTexte("Vous remportez 2 fois votre mise : "+miseGagnee);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}

					if(this.croupier.calculScore() < scoreJoueur) {
						//Joueur gagnant
						System.out.println("Le joueur "+joueur.getNumJoueur()+" a gagne");
						this.table.gestionMises.ajoutCredit(joueur.getNumJoueur(), miseGagnee);
						try {
							joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier");
							joueur.srv.afficherTexte("Vous remportez 2 fois votre mise : "+miseGagnee);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}

					if(this.croupier.calculScore() > scoreJoueur && this.croupier.calculScore() <= 21)
					{
						//Joueur perdant
						System.out.println("Le joueur "+joueur.getNumJoueur()+" a perdu");
						try {
							joueur.srv.afficherScore("Vous avez perdu avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier");
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					if(this.croupier.calculScore() == scoreJoueur) {
						//Si le score est de 21 pour chacun
						if(this.croupier.calculScore() == 21 && scoreJoueur == 21) {
							//Egalité à 21 avec quatre cas
							//Si 21 avec 3 cartes vs 21 avec 2 cartes
							if(this.croupier.getMain().size() == 3 && this.lesJoueurs.get(joueur.getNumJoueur()).getMain().size() == 2) {
								//Joueur gagnant
								System.out.println("Le joueur "+joueur.getNumJoueur()+" a gagne avec un blackjack");
								this.table.gestionMises.ajoutCredit(joueur.getNumJoueur(), miseGagnee);
								try {
									joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier, BlackJack pour vous");
									joueur.srv.afficherTexte("Vous remportez 2 fois votre mise : "+miseGagnee);
								} catch (RemoteException e) {
									e.printStackTrace();
								}
							}
							//Si 21 avec 2 cartes vs 21 3 cartes
							if(this.croupier.getMain().size() == 2 && this.lesJoueurs.get(joueur.getNumJoueur()).getMain().size() == 3) {
								//Joueur perdant
								System.out.println("Le joueur "+joueur.getNumJoueur()+" a perdu avec un blackjack");
								try {
									joueur.srv.afficherScore("Vous avez perdu avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier, BlackJack pour la banque");
								} catch (RemoteException e) {
									e.printStackTrace();
								}
							}
							else {
								//Sinon egalite parfaite
								System.out.println("Egalite");
								this.table.gestionMises.ajoutCredit(joueur.getNumJoueur(), mise);
								try {
									joueur.srv.afficherScore("Vous avez un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier, Egalite parfaite");
									joueur.srv.afficherTexte("Vous reprennez votre mise : "+mise);
								} catch (RemoteException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		System.out.println("Fin de partie");
	}

	/**
	 * Recommence une partie sur avec les joueurs actuellement connectes
	 * integre les joueurs en liste d attente
	 */
	public void recommencerPartie() {
		//Réinitialiser tout les joueurs
		for(Joueur joueur : this.lesJoueurs.values()) {
			//Vider les mains de tout les joueurs
			joueur.viderMain();
			//Repasser le stand et l'etat de la mise des joueurs à false
			joueur.setStand(false);
			joueur.setEtatMise(false);
			joueur.setMiseActuelle(0);
		}
		//Réinitialiser le croupier
			//Vider sa main
			this.croupier.viderMain();
			//Repasser son stand à false
			this.croupier.setStand(false);
			
		//Change le statut de la partie
		this.enCours = false;	
		//Ajout des joueurs en attente si il y en a
		while(!this.enAttente.isEmpty()) {
			this.lesJoueurs.put(this.enAttente.getFirst().getNumJoueur(),this.enAttente.getFirst());
			this.informJoueurs("Le joueur "+this.enAttente.removeFirst().getNumJoueur()+" rejoins la partie");
		}
		//la partie peut recommencer
		//Attente de 10 secondes
		System.out.println("La partie recommence dans 10s");
		this.informJoueurs("La partie recommence dans 10s");
		new CountDown(10);
		this.enCours = true;
		System.out.println("La partie commence");
		this.distribuerJoueur();
		this.distribuerCroupier();
		this.verifMiseInitiale();
	}
	
	
	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void afficherMain(String numJoueur) {
		try {
			this.lesJoueurs.get(numJoueur).srv.afficherMainJoueur(numJoueur+" "+this.lesJoueurs.get(numJoueur).afficheMain());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//On verifie les jetons du joueur a ce moment là
		this.verifMiseInitiale();
	}

	/**
	 * Affiche la liste des joueurs
	 * @return Chaine de caractere faisant la liste des joueurs
	 */
	public String listJoueur() {
		String listJoueur = "Joueur :\n";
		for(Joueur joueur : this.lesJoueurs.values()) {
			listJoueur = (listJoueur+" "+joueur+" "+joueur.getNumJoueur()+"\n");
		}
		return listJoueur; 
	}
	
	/**
	 * Ecrit a tous les joueurs un information
	 * @param info
	 * 		chaine de caractere designant une info
	 */
	public void informJoueurs(String info) {
		for(Joueur joueur : this.lesJoueurs.values()) {
			try {
				joueur.srv.afficherTexte(info);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Retourne vrai si tout les joueurs sont stand et faux sinon
	 * @return
	 */
	public boolean allStand() {
		int nbjoueur = 0, nbstand = 0;
		for(Joueur joueur : this.lesJoueurs.values()) {
			if(joueur.isStand()) {
				nbstand++;
			}
			nbjoueur++;
		}
		if(nbjoueur == nbstand) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Retourne vrai si une partie est en cours et faux sinon
	 * @return
	 */
	public boolean estEnCours() {
		if(this.enCours) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Fait miser le joueur une mise en jetons
	 * @param numJoueur
	 * 			le numero du joueur
	 * @param mise
	 * 			la mise du joueur en jetons
	 */
	public void miser(String numJoueur, int mise) {
		//Le joueur surenchérit
		if(this.lesJoueurs.get(numJoueur).getEtatMise()) {
			this.informJoueurs("Le joueur "+numJoueur+" surenchérit de "+mise);
			System.out.println("Le joueur "+numJoueur+" surenchérit de "+mise);
			mise = mise+this.lesJoueurs.get(numJoueur).getMiseActuelle();
			this.lesJoueurs.get(numJoueur).setMiseActuelle(mise);
			System.out.println("Le joueur a donc mise "+mise);
		}
		else {
			//Si le joueur mise pour la première fois
			this.lesJoueurs.get(numJoueur).setEtatMise(true);
			this.lesJoueurs.get(numJoueur).setMiseActuelle(mise);
			this.informJoueurs("Le joueur "+numJoueur+" mise "+mise+" jetons");
			System.out.println("Le joueur "+numJoueur+" mise "+mise+" jetons");
		}
	}
	
	/**
	 * Retourne vrai si tous les joueurs ont au moins misé une fois
	 * @return
	 */
	public boolean miseUneFois() {
		for(Joueur joueur : this.lesJoueurs.values()) {
			if(!joueur.getEtatMise()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Fait quitter la partie aux joueurs qui n'ont pas assez de jetons pour jouer
	 */
	public void verifMiseInitiale() {
		//Pour chaque joueur
		for(Joueur joueur : this.lesJoueurs.values()) {
			int solde = this.table.gestionMises.getSolde(joueur.getNumJoueur());
			//Si solde insuffisant
			if(solde <= this.table.getMiseMinimale()) {
				//Quitter table
				this.lesJoueurs.remove(joueur.getNumJoueur());
			}
		}
	}
}
	

