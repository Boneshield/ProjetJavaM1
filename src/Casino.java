import java.util.HashMap;

/**
 * Hebergeur de tables, peut creer des tables et des croupiers ainsi que les supprimer
 * @author mathieu
 *
 */

public class Casino {

	HashMap<String, Table> listTables;
	
	
	public Casino() {
		this.listTables = new HashMap<>();
	}
}
