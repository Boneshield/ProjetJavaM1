import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

/**
 * Main du serveur avec gestion de RMI
 * @author mathieu
 *
 */

public class BServeur {

	/**
	 * Main du serveur
	 * Creation de l'interface pour le client et creation du registre de nom pour rmi
	 * @param args
	 * 		Argument passe au main 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServeurImpl bi;
		BlackJack bl = new BlackJack();
		
		try {
			bi = new ServeurImpl(bl);
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
					Serveur b = (Serveur) Naming.lookup("rmi://localhost/BlackJack");
					//attente des joueurs
					System.out.println("BlackJack Serveur");
					System.out.println("*****************");
					System.out.println("attente de joueur");
				
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
