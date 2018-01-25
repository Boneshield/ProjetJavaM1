import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImpl extends UnicastRemoteObject implements Client {

	protected ClientImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	//Afficher la main du client au client
	public void afficherMain(ArrayList<Carte> mainJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<mainJoueur.size();i++) {
			System.out.println("carte "+i);
			mainJoueur.get(i).ToString();
		}
	}
	
	//Afficher le score final au client
	public void afficherScore(String scoreJoueur) throws RemoteException {
		System.out.println(scoreJoueur);
	}

}
