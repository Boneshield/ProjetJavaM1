import java.util.ArrayList;

/**
 * Classe permettant l'isolement du croupier du casino
 * @author mathieu
 *
 */
public class Croupier extends Joueur {
	
	private ArrayList<Carte> main;
	private boolean stand = false;
	
	/**
	 * Constructeur
	 */
	public Croupier() {
		this.main = new ArrayList<Carte>();
	}
	
}
