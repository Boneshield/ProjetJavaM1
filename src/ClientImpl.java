import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {

	protected ClientImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afficherMain(String mainJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(mainJoueur);
	}
	
	public void afficherScore(String scoreJoueur) throws RemoteException {
		System.out.println(scoreJoueur);
	}

}
