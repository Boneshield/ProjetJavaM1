import java.util.ArrayList;

/**
 * Hebergeur de tables, peut creer des tables et des croupiers ainsi que les supprimer
 * @author mathieu
 * @see Table
 */

public class Casino {

	ArrayList<Table> listTables;
	
	/**
	 * Constructeur
	 */
	public Casino() {
		this.listTables = new ArrayList<Table>();
	}
	
	/**
	 * Permet de creer une table avec une taille passee en parametre
	 * @param taille
	 * 		Entier qui est le nombre de joueur a table
	 * @return Table nouvelle table
	 */
	public void creerTable(int taille)	{
		Table table = new Table(taille);
		this.listTables.add(table);
		int dernier = this.listTables.lastIndexOf(table);
		Croupier croupier = this.creerCroupier();
		this.listTables.get(dernier).setCroupier(croupier);	
	}
	
	/**
	 * Cree un nouveau croupier
	 * @return Croupier nouveau croupier
	 */
	public Croupier creerCroupier() {
		return new Croupier();
	}
	
	/**
	 * Liste les tables disponibles dans le casino
	 */
	public void listeTable() {
		System.out.println("Voici la liste des tables :");
		for(int i = 0;i<this.listTables.size();i++) {
			this.listTables.get(i).toString();
		}
	}
}
