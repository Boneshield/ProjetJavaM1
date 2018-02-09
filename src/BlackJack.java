import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui gere les joueurs, le croupier et calcule les scores
 * @author mathieu
 * 
 */
public class BlackJack {
	
	private JeuDeCarte jeu;
	
	/**
	 * Classe qui defini un joueur par son nom, sa main qui est une liste de carte, son etat stand et son interface pour le serveur
	 * @author mathieu
	 *
	 */
	class Joueur {
		private String numJoueur;
		private ArrayList<Carte> main;
		private boolean stand = false;
		private Client srv;
		
		/**
		 * Constructeur joueur classique
		 * @param numJoueur
		 * 		Nom du joueur
		 * @param srv
		 * 		Interface pour que le serveur communique avec le joueur
		 */
		public Joueur(String numJoueur, Client srv) {
			this.numJoueur = numJoueur;
			this.srv = srv;
			this.main = new ArrayList<Carte>();
			this.srv = srv;
		}
		
		/**
		 * Constructeur joueur croupier
		 */
		public Joueur() {
			this.main = new ArrayList<Carte>();
		}
		
		/**
		 * Retourne le numero du joueur courant
		 * @return Une chaine de caractere qui correpond à son nom/numero
		 */
		public String getNumJoueur() {
			return this.numJoueur;
		}
		
		/**
		 * Retourne la main du joueur
		 * @return Une liste de carte qui correspond à la main du joueur
		 */
		public ArrayList<Carte> getMain() {
			return this.main;
		}
		
		/**
		 * Construit les cartes de la main du joueur (sur le serveur) en chaine de caractere
		 * @return une chaine de caractere qui est une liste des cartes du joueur
		 */
		public String afficheMain() {
			String main = "Main :\n";
			for(int i=0;i<this.main.size();i++) {
				main = (main+" "+i+" "+this.main.get(i).ToString()+"\n");
			}
			return main;
		}
		
		/**
		 * Calcule le score de la main du joueur
		 * @return Le score de la somme des valeurs des cartes de la main du joueur
		 */
		public int calculScore() {
			int score = 0;
			for(int i=0;i<main.size();i++) {
				score = score + main.get(i).getValeurCarte();
			}
			return score;
		}
		
		/**
		 * Hit : tire une carte pour ce joueur depuis le jeu de carte
		 * Affiche la nouvelle main au joueur
		 * @see JeuDeCarte
		 */
		public void hit() {
			Carte carte = jeu.TireCarte();
			this.main.add(carte);
			try {
				srv.afficherMainJoueur(this.afficheMain());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * Change la valeur de la carte passee en parametre(marche seulement pour l'AS)
		 * Met la valeur a 1 si elle est a 11 et inversement
		 * @param carte
		 * 		la carte doit etre un AS pour pouvoir etre change
		 */
		public void changeAsValue(Carte carte) {
			//si la valeur de l'as est de 11 alors elle passe à 1 sinon elle passe à 11
			if(carte.getValeurCarte() == 11) {
				carte.setValeur(1);
			}
			else
				carte.setValeur(11);
		}
		
		/**
		 * Retourne vrai si ce joueur possède un score supérieur à 21
		 * @return un boolean selon le score du joueur
		 * @see calculScore()
		 */
		public boolean EstElimine() {
			return (calculScore() > 21);
		}
		
		/**
		 * Passe ce joueur en stand
		 */
		public void stand() {
			this.stand = true;
		}
	}
	
	HashMap<String, Joueur> lesJoueurs;
	Joueur croupier;
	
	/**
	 * Constructeur
	 * Cree la liste des joueurs, un croupier ainsi qu'un jeu de carte
	 * @see Joueur, JeuDeCarte
	 */
	public BlackJack() {
		lesJoueurs = new HashMap<String, Joueur>();
		Joueur croupier = new Joueur();
		this.croupier = croupier;
		this.jeu = new JeuDeCarte();
	}
	
	/**
	 * Ajoute un joueur dans la liste
	 * @param numJoueur
	 * 		Le nom du joueur
	 * @param srv
	 * 		L'interface du joueur
	 */
	public void creerJoueur(String numJoueur, Client srv) {
		this.lesJoueurs.put(numJoueur, new Joueur(numJoueur, srv));
	}
	
	/**
	 * Distribution cartes par le croupier deux par personne
	 * 
	 */
	public void distribuer() {
		//tant que le un joueur n'a pas deux cartes, lui donner une carte
		System.out.println("tirage des joueurs");
		for(Joueur joueur : lesJoueurs.values()) {
			do {
				System.out.println("tirage du joueur "+joueur.getNumJoueur());
				lesJoueurs.get(joueur.getNumJoueur()).hit();
				System.out.println("taille de la main de "+lesJoueurs.get(joueur.getNumJoueur()).main.size());
			} while(lesJoueurs.get(joueur.getNumJoueur()).main.size() != 2);
		}
		System.out.println("tirage croupier");
		//Tirage du croupier
		this.croupier.hit();
		this.croupier.hit();
	}
	/**
	 * Hit : Demande de carte par un joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void hit(String numJoueur) {
		//Demande une carte pour un joueur si il n'a pas encore stand
		if(lesJoueurs.get(numJoueur).stand == false)
		{
			lesJoueurs.get(numJoueur).hit();
			this.elimination(numJoueur);
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
				lesJoueurs.get(numJoueur).srv.afficherMainJoueur("Vous avez été éliminé");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lesJoueurs.remove(numJoueur);
		}
	}
	
	/**
	 * Stand : Arret du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void stand(String numJoueur) {
		//Un joueur s'arrete (ne demande plus de carte)
		if(lesJoueurs.get(numJoueur).stand == false)
		{
			lesJoueurs.get(numJoueur).stand();
			this.tirageCroupier();
		}
	}
	
	/**
	 * Change la valeur de l'AS du joueur dont le nom est passe en parametre
	 * mais verifie qu'il possede un AS avant 
	 * @param numJoueur
	 * 		lLe nom du joueur
	 */
	public void changeAsValue(String numJoueur) {
		//On parcours les cartes du joueur
		for(int i=0;i<lesJoueurs.get(numJoueur).main.size();i++) {
			//Si il y a un as, on peut changer sa valeur
			if(lesJoueurs.get(numJoueur).main.get(i).getNomCarte() == Figure.AS) {
				lesJoueurs.get(numJoueur).changeAsValue(lesJoueurs.get(numJoueur).main.get(i));
			}
		}
	}
	
	/**
	 * Tirage du croupier
	 * le croupier tire des cartes selon les regles definie.
	 * Si son score est de 16 ou moins il tire une carte sinon il stand
	 */
	public void tirageCroupier() {
		//Vérification que tous les joueurs soit stand
		for(Joueur joueur : lesJoueurs.values()) {
			if(lesJoueurs.get(joueur.getNumJoueur()).stand == false)
			{
				System.out.println("Un joueur n'est pas encore stand : "+joueur.getNumJoueur());
			}
		}
		//Tirage de carte du croupier
		while(croupier.calculScore() < 17) {
			if(croupier.calculScore() <= 16) {
				croupier.hit();
			}
		}
		croupier.stand();
	}	
	
	/**
	 * Comptage des gains en comparant les scores des joueurs avec celui du croupier
	 *  
	 */
	public void calculGain() {
		//Compare les scores joueur/banque
		if(croupier.stand == true) {
			//Quatre cas de figure
			if(croupier.calculScore() > 21) {
				//Tout les joueurs gagnent
				System.out.println("Tout les joueurs gagnent");
				
			}
			for(Joueur joueur : lesJoueurs.values()) {
				int scoreJoueur = lesJoueurs.get(joueur).calculScore();
				if(croupier.calculScore() < scoreJoueur) {
					//Joueur gagnant
					System.out.println("Le joueur a gagne");
					try {
						joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+croupier.calculScore()+" pour le croupier");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(croupier.calculScore() > scoreJoueur)
				{
					//Joueur perdant
					System.out.println("Le joueur a perdu");
					try {
						joueur.srv.afficherScore("Vous avez perdu avec un score de "+scoreJoueur+" contre "+croupier.calculScore()+" pour le croupier");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(croupier.calculScore() == scoreJoueur) {
					//Si le score est de 21 pour chacun
					if(croupier.calculScore() == 21 && lesJoueurs.get(joueur).calculScore() == 21) {
						//Egalité à 21 avec quatre cas
					//Si 21 avec 3 cartes vs 21 avec 2 cartes
					if(croupier.main.size() == 3 && lesJoueurs.get(joueur).main.size() == 2) {
						//Joueur gagnant
						System.out.println("Le joueur a gagne avec un blackjack");
						try {
							joueur.srv.afficherScore("Vous avez gagné avec un score de "+scoreJoueur+" contre "+croupier.calculScore()+" pour le croupier, BlackJack pour vous");
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Si 21 avec 2 cartes vs 21 3 cartes
					if(croupier.main.size() == 2 && lesJoueurs.get(joueur).main.size() == 3) {
						//Joueur perdant
						System.out.println("Le joueur a perdu avec un blackjack");
						try {
							joueur.srv.afficherScore("Vous avez perdu avec un score de "+scoreJoueur+" contre "+croupier.calculScore()+" pour le croupier, BlackJack pour la banque");
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						//Sinon egalite parfaite
						System.out.println("Egalite");
						try {
							joueur.srv.afficherScore("Vous avez un score de "+scoreJoueur+" contre "+croupier.calculScore()+" pour le croupier, Egalite parfaite");
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
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void afficherMain(String numJoueur) {
		// TODO Auto-generated method stub
		try {
			lesJoueurs.get(numJoueur).srv.afficherMainJoueur(numJoueur);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*public void attenteJoueur() {
		// TODO Auto-generated method stub
			while(lesJoueurs.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				notify();
			}
		}*/
	
	
	/*public static void main(String args[]) {
		BlackJack bl;
		Client clt;
		
		try {
			clt = new ClientImpl();
			bl = new BlackJack();
			//mon joueur test s'appelle lucas, cela ne veut en aucun cas dire que j'ai pris/copié le code de lucas, n'est-ce pas ?
			bl.creerJoueur("lucas", clt);
			System.out.println(bl.lesJoueurs.get("lucas").numJoueur);
			
			bl.distribuer();
			bl.lesJoueurs.get("lucas").afficheMain();
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}
	

