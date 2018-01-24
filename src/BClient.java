import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class BClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MainJoueur bl = (MainJoueur) Naming.lookup("rmi://localhost/BlackJack");
			String numJoueur;
			System.out.println("Veuillez entrer un nom de joueur :");
			//lecture du choix du client
			Scanner lecture;
			lecture = new Scanner(System.in);
			numJoueur = lecture.next();
			Serveur srv = new ServeurImpl();
			bl.connexion(numJoueur, srv);
			System.out.println("Connecté au serveur");
			
			//affichage menu
			while(true){
				System.out.println("******* MENU *******");
				System.out.println("Que voulez vous faire ?");
				System.out.println("1.Hit (Tirer une carte)");
				System.out.println("2. Stand (Arreter de miser)");
				System.out.println("3. Changer la valeur de l'AS");
				System.out.println("choix : ");
				
				//lecture du choix du client
				int choix = lecture.nextInt();
			
				switch(choix) {
					case 1:
						//hit : demande une carte
						System.out.println("Vous demandez une carte");
						bl.hit(numJoueur);
						break;
					case 2:
						//stand : passe son tour
						System.out.println("Vous decidez de vous arreter");
						bl.stand(numJoueur);
						break;
					case 3:
						//carte.getNomCarte() == Figure.AS
						System.out.println("Vous changez la valeur de l'AS");
						bl.changeAsValue(numJoueur);
						break;
					default:
						System.out.println("Le choix doit être 1, 2 ou 3");
				}
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
	}

}
