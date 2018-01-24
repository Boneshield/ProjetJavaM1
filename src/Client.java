import java.rmi.RemoteException;

public interface Client extends java.rmi.Remote {
	
	
	void afficherMain(String mainJoueur) throws RemoteException;
		
	
	void afficherScore(String scoreJoueur) throws RemoteException; 
	
}
