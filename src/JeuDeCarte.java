import java.util.ArrayList;

public class JeuDeCarte {
	//un jeu de carte dispose de 52 cartes
	private ArrayList<Carte> jeu;
		
	public JeuDeCarte() {
		jeu = new ArrayList<Carte>();
		
		//Création du jeu avec toute les cartes 
		for(Couleur couleur : Couleur.values()) {
			int valeur;
			valeur = 1;
			for(Figure figure : Figure.values()) {
				//Création de la carte et ajout au jeu de carte
				if(figure == Figure.AS) {
					valeur = 11;
				}
				if(figure == Figure.VALET || figure == Figure.DAME || figure == Figure.ROI) {
					valeur = 10;
				}
				Carte c = new Carte(figure,valeur,couleur);
				jeu.add(c);
				if(figure == Figure.AS) {
					valeur = 1;
				}
				valeur++;
			}
		}
	}
	
	//Tire une carte aléatoire du jeu de carte
	public Carte TireCarte() {
		int random = (int )(Math.random() * 51 + 1);
		return this.jeu.get(random);
	}
		
}
