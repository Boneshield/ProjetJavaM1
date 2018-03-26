import java.rmi.RemoteException;

/**
 * Interface pour executer les commandes du casino
 * @author mathieu
 * 
 * @see CasinoServeurImpl
 */
public interface CasinoServeur extends java.rmi.Remote {

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
	 * Connecte le joueur a la table numTable
	 * @param numTable
	 * 		Numero de la table
	 * @param numJoueur
	 * 		Numero du joueur
	 * @param srv
	 * 		Interface du joueur
	 * @throws RemoteException
	 * @return
	 * 		code de retour pour savoir si la connexion s est bien passee
	 */
	 int connexionTable(String numTable, String numJoueur, Client srv) throws RemoteException; 
	
	 /**
		 * Cree une table pour le joueur
		 * @param taille
		 * 		taille entier de 1 a 6
		 * @param nomJoueur
		 * 		chaine de caractere definissant nom du joueur
		 */
	void creerTable(int taille, String nomJoueur) throws RemoteException;
	 
	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void afficherMain(String ntable, String numJoueur) throws RemoteException;
	
	/**
	 * Fait quitter la table au joueur
	 * @param numJoueur
	 * 		Chaine de caractere designant le numero du joueur
	 * @throws RemoteException
	 */
	void quitterTable(String numTable, String numJoueur) throws RemoteException;
	
	/**
	 * Demande la taille de la main du joueur au serveur pour l afficher ensuite
	 * @param ntable
	 * 			le numero de la table
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return la liste des cartes de la main du joueur
	 * @throws RemoteException
	 */
	int returnTailleMain(String ntable, String numJoueur) throws RemoteException;
	
	/**
	 * Demande au croupier de tirer une carte
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void hit(String ntable, String numJoueur) throws RemoteException;
	
	/**
	 * S'arrete pour la manche actuelle : ne demande plus de carte et attends le score final
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @throws RemoteException
	 */
	void stand(String ntable, String numJoueur) throws RemoteException;
	
	/**
	 * Donne le nombre de joueurs
	 * @return le nombre de joueur : type entier
	 * @throws RemoteException
	 */
	int listJoueur(String ntable) throws RemoteException; 
	
	/**
	 * Donne le score final au joueur
	 * @param numJoueur
	 * 			le numéro du joueur
	 * @return donne le résulat de la manche
	 * @throws RemoteException
	 */
	int score(String ntable, String numJoueur) throws RemoteException;
	
	/**
	 * Liste les tables pour le joueur 
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	void listTables(String numJoueur) throws RemoteException;
}
