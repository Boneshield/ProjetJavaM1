
/**
 * Hebergeur de parties de Black Jack
 * Il y a 1 a 6 joueurs a une table
 * @author mathieu
 *@see BlackJack,Joueur
 */

public class Table {
	protected BlackJack partie;
	private int taille;
	private Croupier croupier;
	
	/**
	 * Constructeur
	 * Verifie que la taille fournie est bien comprise entre 1 et 6 sinon mets la table à une taille de 1
	 * @param taille
	 * 		Entier definissant le nombre de joueur a la table
	 */
	public Table(int taille) {
		this.partie = new BlackJack();
		if(taille < 1 || taille > 6) {
			System.out.println("La taille doit être comprise entre 1 et 6");
			System.out.println("Mise à 1 par défaut");
			this.taille = 1;
		}
		else {
			this.taille = taille;
		}
	}
	
	/**
	 * Retourne la taille de la table
	 * @return taille
	 * 		Entier definissant le nombre de joueur a la table
	 */
	public int getTaille() {
		return this.taille;
	}
	
	/**
	 * Modifie le croupier de la table
	 * @param croupier
	 * 		Un croupier
	 */
	public void setCroupier(Croupier croupier) {
		this.croupier = croupier;
	}
	
	/**
	 * Ajoute le joueur numJoueur a la table
	 * @param numJoueur
	 * 		Le nom du joueur
	 */			
	public void joinTable(String numJoueur, Client srv) {
		this.partie.creerJoueur(numJoueur, srv);
	}
	
	/**
	 * Fait quitter la table a un joueur
	 * @param numJoueur
	 * 		Le nom du joueur 
	 */
	public void quitTable(String numJoueur) {
		this.partie.lesJoueurs.remove(numJoueur);
	}
	
	/**
	 * Affiche la taille de la table et la liste des joueurs a table
	 * @return Chaine de caratere affichant les infos de la table 
	 */
	public String toString() {
		return ("Taille de la table : "+this.taille+"\n"+this.partie.listJoueur());
	}
}

