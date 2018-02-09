import java.util.ArrayList;

/**
 * Paquet de carte contenant des cartes : arraylist de carte
 * @author mathieu
 * @see Carte
 */
public class JeuDeCarte {
	//un jeu de carte dispose de 52 cartes
	private ArrayList<Carte> jeu;
	
	/**
	 * Constructeur :
	 * 		Crée un jeu de carte avec 52 cartes correspondant aux combinaisons figure/couleur
	 */
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
	
	/**
	 * Tire une carte aléatoire du jeu de carte
	 * @return Un objet Carte provenant du paquet
	 */
	public Carte TireCarte() {
		int random = (int )(Math.random() * 51 + 1);
		return this.jeu.get(random);
	}
	
}
