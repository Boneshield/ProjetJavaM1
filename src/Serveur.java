import java.rmi.RemoteException;
import java.rmi.server.ObjID;
import java.util.ArrayList;

public interface Serveur extends java.rmi.Remote {
	
	//Envoie le numéro du joueur au serveur
	void connexion(String numJoueur, Client srv) throws RemoteException;
	
	//Affiche la main du joueur
	void afficherMain(String numJoueur) throws RemoteException;
	
	//Demande la main du joueur au serveur pour l'afficher ensuite
	ArrayList<Carte> returnMain(String numJoueur) throws RemoteException;
	
	//Demande au croupier de tirer une carte
	void hit(String numJoueur) throws RemoteException;
		
	//S'arrete pour la manche actuelle(ne demande plus de carte)
	void stand(String numJoueur) throws RemoteException;
	
	//Permet au client de changer la valeur de l'as (1 ou 11)
	void changeAsValue(String numJoueur) throws RemoteException;
	
	//Donne le nombre de joueurs
	int listJoueur() throws RemoteException; 
	
	//Donne le score final au joueur
	int score(String numJoueur) throws RemoteException;
}
