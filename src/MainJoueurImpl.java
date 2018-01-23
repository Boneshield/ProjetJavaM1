import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainJoueurImpl extends UnicastRemoteObject implements MainJoueur {

	private BlackJack bj;
	private String numjoueur;
	
	public MainJoueurImpl(BlackJack bj, String numJoueur) throws RemoteException {
		super();
		this.bj= bj;
		this.numjoueur=numJoueur;
		bj.creerJoueur(numJoueur);
	}
	
	@Override
	public void hit(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		bj.hit(numJoueur);
	}

	@Override
	public void stand(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		bj.stand(numJoueur);
	}

	@Override
	public void changeAsValue(String numJoueur) throws RemoteException{
		// TODO Auto-generated method stub
		bj.changeAsValue(numJoueur);
	}
	
}
