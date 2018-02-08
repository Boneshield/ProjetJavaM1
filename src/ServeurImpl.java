import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ObjID;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServeurImpl extends UnicastRemoteObject implements Serveur {

	BlackJack bj;
	
	public ServeurImpl(BlackJack bj) throws RemoteException {
		super();
		this.bj= bj;
	}
	
	@Override
	public void hit(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.hit(numJoueur);
	}

	@Override
	public void stand(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.stand(numJoueur);
	}

	@Override
	public void changeAsValue(String numJoueur) throws RemoteException{
		// TODO Auto-generated method stub
		this.bj.changeAsValue(numJoueur);
	}

	@Override
	public void connexion(String numJoueur, Client srv) throws RemoteException {
		// TODO Auto-generated method stub
			System.out.println("Connexion de "+srv);
			this.bj.creerJoueur(numJoueur, srv);
			System.out.println("Creation du joueur : "+numJoueur);
			this.bj.hit(numJoueur);
			this.bj.hit(numJoueur);
	}

	@Override
	public void afficherMain(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.afficherMain(numJoueur);
	}

	@Override
	public ArrayList<Carte> returnMain(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		return this.bj.lesJoueurs.get(numJoueur).getMain();
	}

	@Override
	public int listJoueur() throws RemoteException {
		// TODO Auto-generated method stub
		return this.bj.lesJoueurs.size();
	}
	
	public int score(String numJoueur) throws RemoteException {
		return this.bj.lesJoueurs.get(numJoueur).calculScore();
	}
	
}
