import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Bserveur {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlackJack bl = new BlackJack();
		
		MainJoueurImpl bi;
		try {
			bi = new MainJoueurImpl(bl);
			try {
				//Démarre le rmiregistry
				LocateRegistry.createRegistry(1099);
				//Déclaration auprès du serveur de noms
				Naming.rebind("BlackJack", bi);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				try {
					BlackJack b = (BlackJack) Naming.lookup("rmi://localhost/BlackJack");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
	}

}
