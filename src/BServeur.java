import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

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
					
					while(bl.lesJoueurs.size() <= 2) {
						
					}
					System.out.println("Distribution");
					bl.distribuer();
					for(int i=0;i<bl.lesJoueurs.size();i++) {
						System.out.println("Tour du joueur "+i);
						System.out.println("Joueur : "+bl.lesJoueurs.get(i).getNumJoueur());
						bl.lesJoueurs.get(i).afficheMain();
					}
				
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
