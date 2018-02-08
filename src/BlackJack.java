import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class BlackJack {
	
	private JeuDeCarte jeu;
	
	class Joueur {
		private String numJoueur;
		private ArrayList<Carte> main;
		private boolean stand = false;
		private Client srv;
		
		//Joueur
		public Joueur(String numJoueur, Client srv) {
			this.numJoueur = numJoueur;
			this.srv = srv;
			this.main = new ArrayList<Carte>();
			this.srv = srv;
		}
		
		//Croupier
		public Joueur() {
			this.main = new ArrayList<Carte>();
		}
		
		//Retourne le numéro du joueur courant
		public String getNumJoueur() {
			return this.numJoueur;
		}
		
		//Retourne la main du joueur
		public ArrayList<Carte> getMain() {
			return this.main;
		}
		
		//Affiche les cartes de la main du joueur (sur le serveur)
		public String afficheMain() {
			String main = "Main :\n";
			for(int i=0;i<this.main.size();i++) {
				main =(main+" "+i+" "+this.main.get(i).ToString()+"\n");
			}
			return main;
		}
		
		//Calcule le score de la main du joueur
		public int calculScore() {
			int score = 0;
			for(int i=0;i<main.size();i++) {
				score = score + main.get(i).getValeurCarte();
			}
			return score;
		}
		
		//hit tire une carte pour le joueur
		public void hit() {
			Carte carte = jeu.TireCarte();
			//System.out.println("Ajout de la carte "+carte.ToString()+" dans la main du joueur "+this.numJoueur);
			this.main.add(carte);
			try {
				srv.afficherMainJoueur(this.afficheMain());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Change la valeur 'une carte (marche seulement pour l'as)
		public void changeAsValue(Carte carte) {
			//si la valeur de l'as est de 11 alors elle passe à 1 sinon elle passe à 11
			if(carte.getValeurCarte() == 11) {
				carte.setValeur(1);
			}
			else
				carte.setValeur(11);
		}
		
		//Retourne vrai si un joueur possède un score supérieur à 21
		public boolean EstElimine() {
			return (calculScore() > 21);
		}
		
		//Passe un joueur en stand
		public void stand() {
			this.stand = true;
		}
	}
	
	HashMap<String, Joueur> lesJoueurs;
	Joueur croupier;
	
	
	public BlackJack() {
		lesJoueurs = new HashMap<String, Joueur>();
		Joueur croupier = new Joueur();
		this.croupier = croupier;
		this.jeu = new JeuDeCarte();
	}
	
	public void creerJoueur(String numJoueur, Client srv) {
		this.lesJoueurs.put(numJoueur, new Joueur(numJoueur, srv));
	}
	
	//Distribution cartes par le croupier deux par personne 
	public void distribuer() {
		//tant que le un joueur n'a pas deux cartes, lui donner une carte
		System.out.println("tirage des joueurs");
		for(Joueur joueur : lesJoueurs.values()) {
			do {
				System.out.println("tirage du joueur "+joueur.getNumJoueur());
				lesJoueurs.get(joueur.getNumJoueur()).hit();
				System.out.println("taille de la main de lucas "+lesJoueurs.get(joueur.getNumJoueur()).main.size());
			} while(lesJoueurs.get(joueur.getNumJoueur()).main.size() != 2);
		}
		System.out.println("tirage croupier");
		//Tirage du croupier
		this.croupier.hit();
		this.croupier.hit();
	}
	
		//Hit : Demande de carte par un joueur
	public void hit(String numJoueur) {
		//Demande une carte pour un joueur si il n'a pas encore stand
		if(lesJoueurs.get(numJoueur).stand == false)
		{
			lesJoueurs.get(numJoueur).hit();
			this.elimination(numJoueur);
		}
	}
	
		//Elimination du joueur si son score est supérieur à 21
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
	
		//Stand : Arret du joueur
	public void stand(String numJoueur) {
		//Un joueur s'arrete (ne demande plus de carte)
		if(lesJoueurs.get(numJoueur).stand == false)
		{
			lesJoueurs.get(numJoueur).stand();
		}
	}
	
	public void changeAsValue(String numJoueur) {
		//On parcours les cartes du joueur
		for(int i=0;i<lesJoueurs.get(numJoueur).main.size();i++) {
			//Si il y a un as, on peut changer sa valeur
			if(lesJoueurs.get(numJoueur).main.get(i).getNomCarte() == Figure.AS) {
				lesJoueurs.get(numJoueur).changeAsValue(lesJoueurs.get(numJoueur).main.get(i));
			}
		}
	}
	
	//Tirage du croupier
	public void tirageCroupier() {
		//Vérification que tout les joueurs soit stand
		for(Joueur joueur : lesJoueurs.values()) {
			if(lesJoueurs.get(joueur.getNumJoueur()).stand == false)
			{
				System.out.println("Un joueur n'est pas encore stand");
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
	
	//Comptage des gains
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
	

