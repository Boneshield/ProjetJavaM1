import 
java.rmi.RemoteException;
import java.rmi.server.ObjID;
import java.util.ArrayList;

/**
 * Interface permettant au client d'éxécuter des méthodes sur le serveur
 * @author mathieu
 *
 */

public interface Serveur extends java.rmi.Remote {
	
	/**
	 * Connecte le joueur et crée un joueur sur le serveur
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @param srv
	 * 			l'interface pour communiquer avec ce joueur
	 * @throws RemoteException
	 */
	void connexion(String numJoueur, Client srv) throws RemoteException;
	
	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void afficherMain(String numJoueur) throws RemoteException;
	
	/**
	 * Demande la main du joueur au serveur pour l'afficher ensuite
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @return la liste des cartes de la main du joueur
	 * @throws RemoteException
	 */
	ArrayList<Carte> returnMain(String numJoueur) throws RemoteException;
	
	/**
	 * Demande au croupier de tirer une carte
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void hit(String numJoueur) throws RemoteException;
	
	/**
	 * S'arrete pour la manche actuelle : ne demande plus de carte et attends le score final
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void stand(String numJoueur) throws RemoteException;
	
	/**
	 * Permet au client de changer la valeur de l'as (1 ou 11)
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void changeAsValue(String numJoueur) throws RemoteException;
	
	/**
	 * Donne le nombre de joueurs
	 * @return le nombre de joueur : type entier
	 * @throws RemoteException
	 */
	int listJoueur() throws RemoteException; 
	
	/**
	 * Donne le score final au joueur
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @return donne le résulat de la manche
	 * @throws RemoteException
	 */
	int score(String numJoueur) throws RemoteException;
}
