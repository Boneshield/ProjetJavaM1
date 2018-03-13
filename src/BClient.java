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
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			CasinoServeur cl = (CasinoServeur) Naming.lookup("rmi://localhost/BlackJack");
			String numJoueur = null, numTable = null;
			int choix = 0, taille = 0;
			boolean stand = false;
						
			//Création interface client pour le serveur
			ClientImpl srv = new ClientImpl();
			System.out.println("BlackJack version client 3");
			
			System.out.println("Veuillez entrer un nom de joueur :");
			//lecture du nom/pseudo/numéro du client
			Scanner lecture;
			lecture = new Scanner(System.in);
			numJoueur = lecture.next();
			
			//Retour après avoir quitté une table
			
			//Connexion du joueur a la salle d'attente et affichage des tables
			System.out.println("Affichage de la liste des tables");
			cl.connexion(numJoueur, srv);
			
			
			System.out.println("choisir une table(1) ou bien en créer une(2) ?");
			
			do{
				System.out.println("Entrer 1 ou 2 :");
				choix = lecture.nextInt();
				//choix d'une table
				if(choix == 1) {
					System.out.println("Veuillez choisir une table : ");
					//lecture du choix de table du client
					numTable = lecture.next();
				}
				//Creation d'une table
				if(choix == 2) {
					System.out.println("Creation table");
					numTable = numJoueur;
					System.out.println("Veuillez entrer la taille de la table :");
					taille = lecture.nextInt();
					cl.creerTable(taille, numTable);
					System.out.println("Table créée");
				}
			}while(choix != 1 && choix != 2);
			
			//Connexion du joueur au jeu
			System.out.println("Connecté a la table "+numTable);
			System.out.println("Vous etes le joueur "+numJoueur);
			cl.connexionTable(numTable,numJoueur, srv);
			System.out.println("Affichage du score :");
			System.out.println(cl.score(numTable,numJoueur));
			
			//affichage menu
			while(true){
				if(!stand) {
				System.out.println("******* MENU *******");
				System.out.println("Table : "+numTable);
				System.out.println("Il y a "+cl.listJoueur(numTable)+" joueurs");
				System.out.println(" ");
				System.out.println("Que voulez vous faire ?");
				System.out.println("1.Main (Afficher la main du joueur)");
				System.out.println("2.Hit (Tirer une carte)");
				System.out.println("3.Stand (Arreter de miser)");
				System.out.println("4.Quitter la table");
				System.out.println("choix : ");
				
				//lecture du choix du client
				choix = lecture.nextInt();
			
				switch(choix) {
					case 1:
						//Main
						System.out.println("Voici votre main");
						cl.afficherMain(numTable,numJoueur);
						System.out.println("Affichage du score :");
						System.out.println(cl.score(numTable,numJoueur));
						break;
					case 2:
						//hit : demande une carte
						System.out.println("Vous demandez une carte");
						cl.hit(numTable,numJoueur);
						System.out.println("Affichage du score :");
						System.out.println(cl.score(numTable,numJoueur));
						if(cl.score(numTable,numJoueur) > 21) {
							System.out.println("Vous avez été éliminé");
							cl.hit(numTable,numJoueur);
							System.exit(0);
						}
						break;
					case 3:
						//stand : passe son tour et attends le score
						System.out.println("Vous decidez de vous arreter");
						stand = true;
						cl.stand(numTable,numJoueur);
						
						System.exit(0);
					case 4:
						System.out.println("Vous quittez la table"+numTable);
						cl.quitterTable(numTable, numJoueur);
						//retour en salle d'attente
						break;
					default:
						System.out.println("Le choix doit être 1, 2, 3, ou 4");
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
