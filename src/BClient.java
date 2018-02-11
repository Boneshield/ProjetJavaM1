import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 * Main du client
 * @author mathieu
 *
 */
public class BClient {

	/**
	 * Main du client
	 * Connexion au serveur et creation de l'interface pour le serveur
	 * Affichage du menu
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Serveur bl = (Serveur) Naming.lookup("rmi://localhost/BlackJack");
			String numJoueur;
			boolean stand = false;
			System.out.println("Veuillez entrer un nom de joueur :");
			//lecture du nom/pseudo/numéro du client
			Scanner lecture;
			lecture = new Scanner(System.in);
			numJoueur = lecture.next();
			
			//String srv = "rmi://localhost/BlackJack";
			ClientImpl srv = new ClientImpl();
			bl.connexion(numJoueur, srv);
			System.out.println("Connecté au serveur");
			System.out.println("Vous etes le joueur "+numJoueur);
			System.out.println("Affichage du score :");
			System.out.println(bl.score(numJoueur));
			
			//affichage menu
			while(true){
				if(!stand) {
				System.out.println("******* MENU *******");
				System.out.println("Il y a "+bl.listJoueur()+" joueurs");
				System.out.println(" ");
				System.out.println("Que voulez vous faire ?");
				System.out.println("1.Main (Afficher la main du joueur)");
				System.out.println("2.Hit (Tirer une carte)");
				System.out.println("3. Stand (Arreter de miser)");
				System.out.println("4. Changer la valeur de l'AS");
				System.out.println("choix : ");
				
				//lecture du choix du client
				int choix = lecture.nextInt();
			
				switch(choix) {
					case 1:
						//Main
						System.out.println("Voici votre main");
						bl.afficherMain(numJoueur);
						System.out.println("Affichage du score :");
						System.out.println(bl.score(numJoueur));
						break;
					case 2:
						//hit : demande une carte
						System.out.println("Vous demandez une carte");
						bl.hit(numJoueur);
						System.out.println("Affichage du score :");
						System.out.println(bl.score(numJoueur));
						if(bl.score(numJoueur) > 21) {
							System.out.println("Vous avez été éliminé");
							bl.hit(numJoueur);
							System.exit(0);
						}
						break;
					case 3:
						//stand : passe son tour et attends le score
						System.out.println("Vous decidez de vous arreter");
						stand = true;
						bl.stand(numJoueur);
						System.exit(0);
					case 4:
						//carte.getNomCarte() == Figure.AS
						System.out.println("Vous changez la valeur de l'AS");
						bl.changeAsValue(numJoueur);
						break;
					default:
						System.out.println("Le choix doit être 1, 2 ou 3");
				}
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
