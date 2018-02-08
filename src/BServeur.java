import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;


public class BServeur {

	
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
					
					
					
					/*while(bi.bj.lesJoueurs.size() != 2) {
						
					}
					System.out.println("Joueurs Trouve");
					while(bi.bj.lesJoueurs.size() == 2) {
							System.out.println("Distribution");
							bi.bj.distribuer();
							
							for(BlackJack.Joueur joueur : bi.bj.lesJoueurs.values()) {
								System.out.println("Tour du joueur "+joueur.getNumJoueur());
								System.out.println("Joueur : "+bl.lesJoueurs.get(joueur.getNumJoueur()).getNumJoueur());
								bi.bj.lesJoueurs.get(joueur.getNumJoueur()).afficheMain();
								Client c = (Client) Naming.lookup("rmi://localhost/BlackJack");
								c.afficherMainJoueur(joueur.getNumJoueur());
							}
							bi.bj.tirageCroupier();
							bi.bj.calculGain();
							System.out.println("Fin de partie");
					}*/
				
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
