import java.rmi.RemoteException;
import java.util.ArrayList;
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
	
	/**
	 * Constructeur
	 * Cree la liste des joueurs, un croupier ainsi qu'un jeu de carte
	 * @see Joueur, JeuDeCarte
	 */
	public BlackJack() {
		lesJoueurs = new HashMap<String, Joueur>();
		Croupier croupier = new Croupier();
		this.croupier = croupier;
		this.jeu = new JeuDeCarte();
		this.enCours = false;
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
		}
		else {
			//Lancement du timer d'attente si c'est le premier joueur
			if(this.lesJoueurs.isEmpty()) {
				//Ajout du joueur à la partie
				this.lesJoueurs.put(numJoueur, new Joueur(numJoueur, srv));
				this.informJoueurs("Le joueur "+numJoueur+" rejoins la partie");
				System.out.println("En attente d'autres joueurs");
				//Attente de 30 secondes
				new CountDown(30);
				this.enCours = true;
				System.out.println("La partie commence");
				this.distribuer();
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
	public void distribuer() {
		//tant que un joueur n'a pas deux cartes, lui donner une carte
		System.out.println("tirage des joueurs");
		for(Joueur joueur : this.lesJoueurs.values()) {
			System.out.println("tirage du joueur "+joueur.getNumJoueur());
			do {
				this.lesJoueurs.get(joueur.getNumJoueur()).hit(jeu);
			} while(this.lesJoueurs.get(joueur.getNumJoueur()).getMain().size() != 2);
		}
		System.out.println("tirage du croupier");
		//Tirage du croupier
		this.croupier.hit(jeu);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.informJoueurs("Le joueur "+numJoueur+" a ete elimine");
			System.out.println("Joueur "+numJoueur+" a ete elimine");
			this.lesJoueurs.remove(numJoueur);
		}
	}
	
	/**
	 * Stand : Arret du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void stand(String numJoueur) {
		//Un joueur s'arrete (ne demande plus de carte)
		if(this.lesJoueurs.get(numJoueur).isStand() == false)
		{
			this.lesJoueurs.get(numJoueur).setStand(true);
			
			this.tirageCroupier();
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
					//croupier perdant
					if(this.croupier.calculScore() > 21) {
						//Tout les joueurs gagnent
						System.out.println("Tout les joueurs gagnent");
						try {
							joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier");
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if(this.croupier.calculScore() < scoreJoueur) {
						//Joueur gagnant
						System.out.println("Le joueur "+joueur.getNumJoueur()+" a gagne");
						try {
							joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier");
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
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
							// TODO Auto-generated catch block
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
								try {
									joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier, BlackJack pour vous");
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
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
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								//Sinon egalite parfaite
								System.out.println("Egalite");
								try {
									joueur.srv.afficherScore("Vous avez un score de "+scoreJoueur+" contre "+this.croupier.calculScore()+" pour le croupier, Egalite parfaite");
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
	}

	/**
	 * Recommence une partie sur avec les joueurs actuellement connectés
	 */
	public void recommencerPartie() {
		//Réinitialiser tout les joueurs
		for(Joueur joueur : this.lesJoueurs.values()) {
			//Vider les mains de tout les joueurs
			joueur.viderMain();
			//Repasser le stand des joueurs à false
			joueur.setStand(false);
			this.informJoueurs("La partie recommence");
		}
		//Réinitialiser le croupier
			//Vider sa main
			this.croupier.viderMain();
			//Repasser son stand à false
			this.croupier.setStand(false);
			
		//Ajout des joueurs en attente si il y en a
		while(!this.enAttente.isEmpty()) {
			this.lesJoueurs.put(this.enAttente.getFirst().getNumJoueur(),this.enAttente.getFirst());
			this.informJoueurs("Le joueur "+this.enAttente.removeFirst()+" rejoins la partie");
		}
	}
	
	
	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void afficherMain(String numJoueur) {
		// TODO Auto-generated method stub
		try {
			this.lesJoueurs.get(numJoueur).srv.afficherMainJoueur(numJoueur+" "+this.lesJoueurs.get(numJoueur).afficheMain());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Affiche la liste des joueurs
	 * @return Chaine de caratere faisant la liste des joueurs
	 */
	public String listJoueur() {
		String listJoueur = "Joueur :\n";
		for(Joueur joueur : this.lesJoueurs.values()) {
			listJoueur = (listJoueur+" "+joueur+" "+joueur.getNumJoueur()+"\n");
		}
		return listJoueur; 
	}
	
	/**
	 * Ecris a tous les joueurs un information
	 * @param info
	 * 		chaine de caractere designant une info
	 */
	public void informJoueurs(String info) {
		for(Joueur joueur : this.lesJoueurs.values()) {
			try {
				joueur.srv.afficherTexte(info);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	

