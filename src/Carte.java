
public class Carte {
	private Figure nom;
	private int valeur;
	private Couleur couleur;
	
	public Carte(Figure nom, int valeur, Couleur couleur) {
		this.nom = nom;
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	public Figure getNomCarte() {
		return this.nom;
	}
	
	public int getValeurCarte() {
		return this.valeur;
	}
	
	public Couleur getCouleurCarte() {
		return this.couleur;
	}
	
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	public String ToString() {
		return ("Carte : "+ this.nom +" de "+this.couleur+" : score : "+this.valeur);
	}
}
