
/**
 * Hebergeur de parties de Black Jack
 * Il y a 1 a 6 joueurs a une table
 * @author mathieu
 *@see BlackJack,Joueur
 */

public class Table {
	BlackJack partie;
	private int taille;
	
	/**
	 * Constructeur
	 * Verifie que la taille fournie est bien comprise entre 1 et 6 sinon mets la table à une taille de 1
	 * @param taille
	 * 		Entier definissant le nombre de joueur a la table
	 */
	public Table(int taille) {
		this.partie = new BlackJack();
		if(taille < 1 || taille > 6) {
			this.taille = 1;
		}
		else {
			this.taille = taille;
		}
		
	}
	
	public Table() {
		
	}
}
