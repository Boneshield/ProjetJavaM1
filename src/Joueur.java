import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Classe qui defini un joueur par son nom, sa main qui est une liste de carte, son etat stand et son interface pour le serveur
 * @author mathieu
 *
 */

public class Joueur extends Personne{
	
	private String numJoueur;
	protected Client srv;
	
	/**
	 * Constructeur joueur classique
	 * @param numJoueur
	 * 		Nom du joueur
	 * @param srv
	 * 		Interface pour que le serveur communique avec le joueur
	 */
	public Joueur(String numJoueur, Client srv) {
		super();
		this.numJoueur = numJoueur;
		this.srv = srv;
	}
	
	/**
	 * Retourne le numero du joueur courant
	 * @return Une chaine de caractere qui correpond à son nom/numero
	 */
	public String getNumJoueur() {
		return this.numJoueur;
	}
	
	/**
	 * Hit : tire une carte pour ce joueur depuis le jeu de carte
	 * Affiche la nouvelle main au joueur
	 * @see JeuDeCarte
	 */
	public void hit(JeuDeCarte jeu) {
		super.hit(jeu);
		try {
			srv.afficherMainJoueur(this.afficheMain());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}