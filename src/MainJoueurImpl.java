import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainJoueurImpl extends UnicastRemoteObject implements MainJoueur {

	private BlackJack bj;
	
	public MainJoueurImpl(BlackJack bj) throws RemoteException {
		super();
		this.bj= bj;
	}
	
	@Override
	public void hit() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stand() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAsValue() throws RemoteException{
		// TODO Auto-generated method stub
		
	}
	
}
