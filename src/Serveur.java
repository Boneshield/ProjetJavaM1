import java.rmi.RemoteException;

public interface Serveur extends java.rmi.Remote {
	
	//Envoie le numéro du joueur au serveur
	void connexion(String numJoueur, String srv) throws RemoteException;
	
	//Affiche la main du joueur
	void afficherMain(String numJoueur) throws RemoteException;
	
	//Demande au croupier de tirer une carte
	void hit(String numJoueur) throws RemoteException;
		
	//S'arrete pour la manche actuelle(ne demande plus de carte)
	void stand(String numJoueur) throws RemoteException;
	
	//Permet au client de changer la valeur de l'as (1 ou 11)
	public void changeAsValue(String numJoueur) throws RemoteException;
}
