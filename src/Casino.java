import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Hebergeur de tables, peut creer des tables et des croupiers ainsi que les supprimer
 * @author mathieu
 * 
 * @see Table
 */

public class Casino {

	protected HashMap<String, Table> listTables;
	protected HashMap<String, Joueur> salleAttente; 
	
	/**
	 * Constructeur
	 */
	public Casino() {
		this.listTables = new HashMap<String, Table>();
		this.salleAttente = new HashMap<String, Joueur>();
	}

	/**
	 * Permet de creer une table avec une taille passee en parametre et son numero
	 * @param taille
	 * 		Entier qui est le nombre de joueur a table
	 * @param unmTable
	 * 		Chaine de caractere designant le 
	 * @return Table nouvelle table
	 */
	public void creerTable(String numTable, int taille)	{
		Table table = new Table(numTable, taille);
		this.listTables.put(numTable,table);
		Croupier croupier = this.creerCroupier();
		this.listTables.get(numTable).setCroupier(croupier);	
	}
	
	/**
	 * Supprime une table de la liste des tables
	 * @param numTable
	 */
	public void supprimerTable(String numTable) {
		this.listTables.remove(numTable);
	}
	
	/**
	 * Cree un nouveau croupier
	 * @return Croupier nouveau croupier
	 */
	public Croupier creerCroupier() {
		return new Croupier();
	}
	
	/**
	 * Ajoute le joueur dans la salle d'attente
	 * @param numJoueur
	 * 		Chaine de caractere designant le nom du joueur
	 * @param srv
	 * 		Interface du client
	 */
	public void arriveJoueur(String numJoueur, Client srv) {
		this.salleAttente.put(numJoueur, new Joueur(numJoueur, srv));
		System.out.println("Joueur "+numJoueur+" arrive en salle d attente");
	}
	
	/**
	 * Liste les tables disponibles dans le casino pour le joueur passé en paramètre
	 */
	public void listeTable(String numJoueur) {
		for(Table table : listTables.values()) {
			try {
				this.salleAttente.get(numJoueur).srv.afficherTexte(table.getNum()+" ("+table.toString()+")");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Retourne vrai si la table existe et faux sinon
	 * @param numTable
	 * @return
	 */
	public boolean isTable(String numTable) {
		if(this.listTables.containsKey(numTable)) {
			return true;
		}
		else {
			return false;
		}
	}
}
