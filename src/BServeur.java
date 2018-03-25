import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

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
		CasinoServeurImpl ci;
		Casino cn = new Casino();
		//Création tables permanentes
			cn.creerTable("Casino1", 2);
			cn.creerTable("Casino2", 3);
			cn.creerTable("Casino3", 4);
			cn.creerTable("Casino4", 5);
			cn.creerTable("Casino5", 6);
		try {
			ci = new CasinoServeurImpl(cn);
			try {
				//Démarre le rmiregistry
				LocateRegistry.createRegistry(1099);
				//Déclaration auprès du serveur de noms
				Naming.rebind("BlackJack", ci);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
				try {
					@SuppressWarnings("unused")
					CasinoServeur cl = (CasinoServeur) Naming.lookup("rmi://localhost/BlackJack");
					//attente des joueurs
					System.out.println("BlackJack Serveur V3");
					System.out.println("*****************");
					System.out.println("attente de joueur");
				
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
		} catch (RemoteException e1) {
		e1.printStackTrace();
		}
	}

}
