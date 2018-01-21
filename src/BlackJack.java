import java.util.ArrayList;
import java.util.HashMap;

public class BlackJack {
	
	private JeuDeCarte jeu;
	
	class Joueur {
		private String numJoueur;
		private MainJoueur m;
		private ArrayList<Carte> main;
		private boolean stand = false;
		
		public Joueur(String numJoueur, MainJoueur m) {
			this.numJoueur = numJoueur;
			this.m = m;
		}
		
		public String getNumJoueur() {
			return this.numJoueur;
		}
		
		public void afficheMain() {
			System.out.println("Main du joueur");
			for(int i=0;i<main.size();i++) {
				System.out.println("carte "+i);
				main.get(i).ToString();
			}
		}
		
		public int calculScore() {
			int score = 0;
			for(int i=0;i<main.size();i++) {
				score = score + main.get(i).getValeurCarte();
			}
			return score;
		}
		
		public void hit() {
			Carte carte;
			carte = jeu.TireCarte();
			main.add(carte);
		}
		
		public void changeAsValue(Carte carte) {
			//si la valeur de l'as est de 11 alors elle passe à 1 sinon elle passe à 11
			if(carte.getValeurCarte() == 11) {
				carte.setValeur(1);
			}
			else
				carte.setValeur(11);
		}
		
		public boolean elimination() {
			return (calculScore() > 21);
		}
		
		public void stand() {
			this.stand = true;
		}
		
	}
	
	
	HashMap<String, Joueur> lesJoueurs;
	Joueur croupier;
	
	public BlackJack() {
		lesJoueurs = new HashMap<String, Joueur>();
		Joueur croupier = null;
		this.croupier = croupier;
	}
	
	public void creerJoueur(String numJoueur, MainJoueur m) {
		lesJoueurs.put(numJoueur, new Joueur(numJoueur, m));
	}
	
	//Distribution cartes par le croupier deux par personne 
	public void distribuer() {
		//tant que le un joueur n'a pas deux cartes, lui donner une carte
		for(int i=0;i<lesJoueurs.size();i++) {
			do {
				lesJoueurs.get(i).hit();
			} while(lesJoueurs.get(i).main.size() == 2);
		}
		//Tirage du croupier
		croupier.hit();
		croupier.hit();
	}
	
	//Tour des joueurs
		//Hit : Demande de carte par un joueur
	public void hit(String numJoueur) {
		//Demande une carte pour un joueur si il n'a pas encore stand
		if(lesJoueurs.get(numJoueur).stand == false)
		{
			lesJoueurs.get(numJoueur).hit();
		}
	}
	
		//Elimination du joueur si son score est supérieur à 21
	public void elimination(String numJoueur) {
		//Si le score du joueur dépasse 21 alors il est éliminé
		if(lesJoueurs.get(numJoueur).elimination()) {
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
	
	//Arret de tout les joueur
	//Tirage du croupier
	public void tirageCroupier() {
		//Vérification que tout les joueurs soit stand
		for(int i=0;i<lesJoueurs.size();i++) {
			if(lesJoueurs.get(i).stand == false)
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
				
			}
			for(int i=0;i<lesJoueurs.size();i++) {
				int scoreJoueur = lesJoueurs.get(i).calculScore();
				if(croupier.calculScore() < scoreJoueur) {
					//Joueur gagnant
					
				}
				if(croupier.calculScore() > scoreJoueur)
				{
					//Joueur perdant
					
				}
				if(croupier.calculScore() == scoreJoueur) {
					//Egalité avec quatre cas
						//Si les deux joueurs ont 21
					if(croupier.calculScore() == 21 && scoreJoueur == 21) {
						//Si 21 avec 2 cartes vs 21 3 cartes
						if(croupier.main.size() == 3 && lesJoueurs.get(i).main.size() == 2) {
							//Joueur gagnant
							
						}
						//Si 21 avec 2 cartes vs 21 3 cartes
						if(croupier.main.size() == 2 && lesJoueurs.get(i).main.size() == 3) {
							//Joueur perdant
							
						}
					}
					else {
						//Sinon egalite parfaite
						
					}
				}
			}
		}
	}
	
}
