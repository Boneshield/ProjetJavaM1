import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class Personne {

	private ArrayList<Carte> main;
	private boolean stand = false;
	
	public Personne() {
		this.main = new ArrayList<Carte>();
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
		for(int i=0;i<this.main.size();i++) {
			score = score + this.main.get(i).getValeurCarte();
		}
		return score;
	}
	
	/**
	 * Hit : tire une carte pour ce joueur depuis le jeu de carte
	 * Affiche la nouvelle main au joueur
	 * @see JeuDeCarte
	 */
	public void hit(JeuDeCarte jeu) {
		Carte carte = jeu.TireCarte();
		this.main.add(carte);
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
	
	/**
	 * Retourne l'etat du joueur pour savoir si il est stand ou non
	 * @return boolean stand
	 */
	public boolean isStand() {
		return this.stand;
	}
	
	/**
	 * Permet de changer l'état du joueur
	 * @param stand
	 * 		Etat du joueur stand ou non
	 */
	public void setStand(boolean stand) {
		this.stand = stand;
	}
	
	/**
	 * Permet de vider la main du joueur. Vide son arraylist
	 */
	public void viderMain() {
		this.main.clear();
	}
	
}
