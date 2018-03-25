import java.rmi.RemoteException;

/**
 * Hebergeur de parties de Black Jack
 * Il y a 1 a 6 joueurs a une table
 * @author mathieu
 * @see BlackJack,Joueur
 */

public class Table {
	protected BlackJack partie;
	private String numTable;
	private int taille;
	private Croupier croupier;
	
	/**
	 * Constructeur
	 * Verifie que la taille fournie est bien comprise entre 1 et 6 sinon mets la table à une taille de 1
	 * @param taille
	 * 		Entier definissant le nombre de joueur a la table
	 */
	public Table(String numTable, int taille) {
		this.partie = new BlackJack();
		this.numTable = numTable;
		if(taille < 1 || taille > 6) {
			System.out.println("La taille doit être comprise entre 1 et 6");
			System.out.println("Taille mise à 6 par défaut");
			this.taille = 6;
		}
		else {
			this.taille = taille;
		}
	}
	
	/**
	 * Retourne le numero(nom) de la table
	 * @return numTable
	 * 		Chaine designant le numero de la table
	 */
	public String getNum() {
		return this.numTable;
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
	 * Fait quitter la table a un joueur si c'est le créateur
	 * de la table on kick tous les autres joueurs
	 * @param numJoueur
	 * 		Le nom du joueur
	 */
	public void quitTable(String numJoueur) {
		System.out.println("Joueur "+numJoueur+" a quitte la table");
		this.partie.lesJoueurs.remove(numJoueur);
		if(numJoueur == this.numTable) {
			this.partie.informJoueurs("Le créateur de la partie a quitté");
			if(!this.partie.estEnCours()) {
				for(Joueur joueur : this.partie.lesJoueurs.values()) {
					this.partie.lesJoueurs.remove(joueur.getNumJoueur());
				}
			}
		}
	}
	
	public int getNbJoueurCo() {
		return (this.partie.lesJoueurs.size() + this.partie.enAttente.size());
	}
	
	
	/**
	 * Affiche la taille de la table et la liste des joueurs a table
	 * @return Chaine de caratere affichant les infos de la table 
	 */
	public String toString() {
		return (this.partie.lesJoueurs.size()+"/"+this.taille);
	}
}

