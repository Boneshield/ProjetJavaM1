import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends java.rmi.Remote {
	
	//Afficher la main du client au client
	void afficherMainJoueur(String mainJoueur) throws RemoteException;
		
	//Afficher le score final au client
	void afficherScore(String scoreJoueur) throws RemoteException; 
	
}
