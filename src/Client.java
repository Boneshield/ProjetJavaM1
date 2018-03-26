import java.rmi.RemoteException;

/**
 * Interface pour que le serveur affiche des informations au client
 * @author mathieu
 * 
 * @see ClientImpl
 */

public interface Client extends java.rmi.Remote {
	
	/**
	 * Affiche la main du client chez le client
	 * @param mainJoueur
	 * 				La main du joueur en chaine de caractère
	 */
	void afficherMainJoueur(String mainJoueur) throws RemoteException;
		
	/**
	 * Affiche le score final au client, le résulat de la manche
	 * @param scoreJoueur
	 * 				Le résultat de la manche en chaine de caractère
	 */
	void afficherScore(String scoreJoueur) throws RemoteException; 
	
	/**
	 * Affiche le texte transmis par le serveur
	 * @param texte
	 * 		Texte envoyé par le serveur
	 * @throws RemoteException
	 */
	void afficherTexte(String texte) throws RemoteException;

}
