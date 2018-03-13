import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


/**
 * Implémentation de l'interface pour que le serveur affiche des informations au client
 * @author mathieu
 * 
 */

public class ClientImpl extends UnicastRemoteObject implements Client {
	
	/**
	 * Constructeur
	 * 
	 * @throws RemoteException si il y a un problème au niveau de RMI
	 */
	protected ClientImpl() throws RemoteException {
		super();
	}
	
	
	/**
	 * Affiche la main du client chez le client
	 * @param mainJoueur
	 * 			La main du joueur en chaine de caractère
	 * 
	 */
	@Override
	public void afficherMainJoueur(String mainJoueur) throws RemoteException {
		System.out.println(mainJoueur);
		
	}
	
	/**
	 * Affiche le score final au client, le résulat de la manche
	 * @param scoreJoueur
	 * 				Le résultat de la manche en chaine de caractère
	 * 
	 */
	public void afficherScore(String scoreJoueur) throws RemoteException {
		System.out.println(scoreJoueur);
	}


	@Override
	public void afficherTexte(String texte) throws RemoteException {
		System.out.println(texte);
	}

}
