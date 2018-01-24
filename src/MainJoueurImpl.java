import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainJoueurImpl extends UnicastRemoteObject implements MainJoueur {

	private BlackJack bj;
	private String numjoueur;
	
	public MainJoueurImpl(BlackJack bj) throws RemoteException {
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
	public void connexion(String numJoueur, Serveur srv) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.creerJoueur(numJoueur, srv);
		System.out.println("Creation du joueur : "+numJoueur);
	}

	@Override
	public void afficherMain(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.afficherMain(numJoueur);
	}
	
}
