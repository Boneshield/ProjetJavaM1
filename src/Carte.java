
/**
 * Objet carte avec nom, couleur et valeur(score)
 * @author mathieu
 * @see Figure,Couleur
 */
public class Carte {
	private Figure nom;
	private int valeur;
	private Couleur couleur;
	
	/**
	 * Constructeur
	 * @param nom
	 * 			Nom de la carte correspondant a la figure
	 * @param valeur
	 * 			Valeur de la carte dans le jeu : donne le score de la carte
	 * @param couleur
	 * 			Couleur, type de la carte
	 */
	public Carte(Figure nom, int valeur, Couleur couleur) {
		this.nom = nom;
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	/**
	 * Retourne le nom de la carte
	 * 
	 */
	public Figure getNomCarte() {
		return this.nom;
	}
	
	/**
	 * Retourne la valeur de la carte
	 * 
	 */
	public int getValeurCarte() {
		return this.valeur;
	}
	
	/**
	 * Retourne la couleur de la carte
	 * 
	 */
	public Couleur getCouleurCarte() {
		return this.couleur;
	}
	
	/**
	 * Permet de changer la valeur de la carte (utilisé pour changer la valeur de l'AS)
	 * @param valeur
	 * 			Valeur de la carte
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * Retourne un chaine de caractere représentant une carte
	 * @return le nom de la carte + la couleur + la valeur
	 */
	public String ToString() {
		return ("Carte : "+ this.nom +" de "+this.couleur+" : score : "+this.valeur);
	}
}
