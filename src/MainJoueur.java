import java.rmi.RemoteException;

public interface MainJoueur {
	
	//Demande au croupier de tirer une carte
	void hit() throws RemoteException;
		
	//S'arrete pour la manche actuelle(ne demande plus de carte)
	void stand() throws RemoteException;
	
	//Permet au client de changer la valeur de l'as (1 ou 11)
	public void changeAsValue() throws RemoteException;
}
