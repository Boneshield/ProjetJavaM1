import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurImpl extends UnicastRemoteObject implements Serveur {

	private BlackJack bj;
	
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
	public void connexion(String numJoueur, String srv) throws RemoteException {
		// TODO Auto-generated method stub
		Client clt;
		try {
			clt = (Client) Naming.lookup(srv);
			this.bj.creerJoueur(numJoueur, clt);
			System.out.println("Creation du joueur : "+numJoueur);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void afficherMain(String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.bj.afficherMain(numJoueur);
	}
	
}
