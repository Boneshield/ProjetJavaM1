import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
			//Lookup au serveur RMI
			CasinoServeur cl = (CasinoServeur) Naming.lookup("rmi://localhost/BlackJack");
			
			String numJoueur = null;		//Numéro du joueur (son nom)
			String numTable = null;			//Numéro de la table (son nom)
			int choix = 0;					//Indicateur du choix du client
			int taille = 0;					//Taille de la table si le joueur crée une table
			int miseJoueur;					//Mise entrée par le client
			int miseMinimale = 0;			//Mise minimale de la table créée par le joueur
			int miseMaximale = 0;			//Mise maximale de la table créée par le joueur
			boolean stand = false;			//Indicateur si le joueur est stand ou non
			boolean statut = false;			//Indicateur si le joueur quitte la table ou non
			boolean carteEnMain = false;	//Indicateur si le joueur a des cartes dans sa main
			boolean miseFaite = false;		//Indicateur si le joueur a misé 
			Scanner lecture;				//Lecteur des saisies du joueur
			
			//Création de l'interface client pour le serveur
			ClientImpl srv = new ClientImpl();
			System.out.println("BlackJack version client 4");
			
			//Saisie du nom du joueur avec vérification de la chaine de caractère
			lecture = new Scanner(System.in);
			do {
				//lecture du nom/pseudo/numéro du client
				System.out.println("Veuillez entrer un nom de joueur :");
				numJoueur = lecture.next();
			}while(!Pattern.matches("[0-9a-zA-Z]*", numJoueur));
			
			//Boucle de client du casino
			while(true) {
				//Retour ici après avoir quitté une table
				
				//Connexion du joueur a la salle d'attente du casino et affichage des tables
				System.out.println("Affichage de la liste des tables");
				cl.connexion(numJoueur, srv);
				
				System.out.println("choisir une table(1), en créer une(2) ou afficher la liste des tables(3)?");
				System.out.println("Votre solde est de : "+cl.consulterSolde(numJoueur));
				do{
					System.out.println("Entrer 1, 2 ou 3:");
					try {
						choix = lecture.nextInt();
					}catch (InputMismatchException e){
						choix = 3;
						lecture.nextLine();
					}
					//Choix/Création/Affichage d'une table
					switch(choix) {
						case 1:
							//Choix d'une table
							do {
								System.out.println("Veuillez choisir une table : ");
								//lecture du choix de table du client
								numTable = lecture.next();
							}while(!Pattern.matches("[0-9a-zA-Z]*", numTable));
							//Connexion du joueur à la table choisie
							choix = cl.connexionTable(numTable,numJoueur, srv);
							if(choix == 4) {
								//Table pleine
								System.out.println("La table est complète !");
								choix = 3;
							}
							if(choix == 5) {
								//Table inexistante
								System.out.println("La table n'existe pas !");
								choix = 3;
							}
							break;
						case 2:
							//Creation d'une table
								System.out.println("Creation table");
								//Saisie de la taille table
								do {
									System.out.println("Veuillez entrer la taille de la table :");
									try {
										taille = lecture.nextInt();
									} catch (InputMismatchException e) {
										System.out.println("Taille incorrecte !");
										lecture.nextLine();
									}
								}while(taille < 1 || taille > 6);
								//Saisie de la mise minimale de la table
								do {
									System.out.println("Veuillez entrer la mise minimale de la table :(1 jeton mimimum)");
									try {
										miseMinimale = lecture.nextInt();
									} catch (InputMismatchException e) {
										System.out.println("Mise incorrecte !");
										lecture.nextLine();
									}
									//Informations sur l'erreur de la saisie de la mise
									if(miseMinimale < 1 || miseMinimale > 200) {
										System.out.println("La mise doit être entre 1 et 200 jetons !");
									}
								}while(miseMinimale < 1 || miseMinimale > 200);
								//Saisie de la mise maximale de la table
								do {
									System.out.println("Veuillez entrer la mise maximale de la table :(200 jetons maximum)");
									try {
										miseMaximale = lecture.nextInt();
									} catch (InputMismatchException e) {
										System.out.println("Mise incorrecte !");
										lecture.nextLine();
									}
									//Informations sur l'erreur de la saisie de la mise
									if(miseMinimale < 1 || miseMinimale > 200) {
										System.out.println("La mise doit être entre 1 et 200 jetons !");
									}
									if(miseMaximale < miseMinimale) {
										System.out.println("La mise maximale ne peut pas être plus petite que la mise minimale !");
									}
								}while(miseMaximale < 1 || miseMaximale > 200 || miseMinimale > miseMaximale);
								numTable = numJoueur;
								
								cl.creerTable(numTable, taille, miseMinimale, miseMaximale);
								System.out.println("Table créée");
								//Connexion du joueur à sa propre table
								choix = cl.connexionTable(numTable,numJoueur, srv);
								if(choix == 4) {
									//Table pleine
									System.out.println("La table est complète !");
									choix = 3;
								}
								if(choix == 5) {
									//Table inexistante
									System.out.println("La table n'existe pas !");
									choix = 3;
								}
							break;
						case 3:
							//Affichage des tables
							System.out.println("Affichage de la liste des tables");
							cl.listTables(numJoueur);
							break;
						default:
							//Erreur
							System.out.println("Ce choix n'est pas possible !");
							choix = 3;
							break;
					}
				}while(choix != 0);
				
				System.out.println("En attente du serveur");

				//Si le joueur est en dans la file d'attente, on doit attendre
				//Tant qu'il n'a pas de carte dans sa main, il attend le serveur
				while(!carteEnMain) {
					try {
						if(cl.returnTailleMain(numTable, numJoueur) != 0 || cl.listJoueur(numTable) == 0) {
							carteEnMain = true;
							miseFaite = false;
							miseMinimale = cl.getMiseMiniTable(numTable, numJoueur);
							miseMaximale = cl.getMiseMaxiTable(numTable, numJoueur);
							break;
						}
					}
					catch (Exception e) {
						//ignore l'erreur
					}
				}
				
				//Boucle de partie
				while(carteEnMain){
					//Stand permet de savoir si on affiche le menu ou si on est en fin de partie
					if(!stand) {						
						System.out.println("******* MENU *******");
						System.out.println("Table : "+numTable);
						System.out.println("Il y a "+cl.listJoueur(numTable)+" joueurs");
						System.out.println(" ");
						System.out.println("Vous etes le joueur "+numJoueur);
						System.out.println("Votre solde de jetons : "+cl.consulterSolde(numJoueur));
						System.out.println("Mise min-max : "+miseMinimale+"-"+miseMaximale);
						System.out.println("Voici votre main");
						cl.afficherMain(numTable,numJoueur);
						//Si le solde du joueur n'est pas suffisant pour miser, il quitte la table
						if(cl.consulterSolde(numJoueur) < miseMinimale) {
							cl.quitterTable(numTable, numJoueur);
							carteEnMain = false;
							break;
						}
						System.out.println("Affichage du score :");
						System.out.println(cl.score(numTable,numJoueur));
						System.out.println(" ");
						
						//Si c'est le premier tour, on fait la mise initiale
						while(!miseFaite) {
								System.out.println("Veuillez entrer votre mise :");
								try {
									miseJoueur = lecture.nextInt();
									if(miseJoueur >= miseMinimale && miseJoueur <= miseMaximale) {
										System.out.println("Vous avez misé : "+miseJoueur);
										cl.miser(numTable, numJoueur, miseJoueur);
										miseFaite = true;
									}
									if(!miseFaite) {
										System.out.println("Erreur de saisie !");
										lecture.nextLine();
									}
								} catch (InputMismatchException e) {
									System.out.println("Mise incorrecte !");
								}	
							}
						
						System.out.println("Que voulez vous faire ?");
						System.out.println("1.Hit (Tirer une carte)");
						System.out.println("2.Stand (Arreter de jouer)");
						System.out.println("3.Enchérir sur la mise ");
						System.out.println("4.Quitter la table");
						System.out.println("choix : ");
						
						//lecture du choix du client
						choix = lecture.nextInt();
					
						switch(choix) {
							case 1:
								//hit : demande une carte
								System.out.println("Vous demandez une carte");
								cl.hit(numTable,numJoueur);
								System.out.println("Voici votre main");
								cl.afficherMain(numTable,numJoueur);
								System.out.println("Affichage du score :");
								System.out.println(cl.score(numTable,numJoueur));
								if(cl.score(numTable,numJoueur) > 21) {
									cl.hit(numTable,numJoueur);
									System.out.println("Vous quittez la table"+numTable);
									cl.quitterTable(numTable, numJoueur);
									carteEnMain = false;
								}
								break;
							case 2:
								//stand : passe son tour et attends le score
								System.out.println("Vous decidez de vous arreter");
								stand = true;
								cl.stand(numTable,numJoueur);
								//Attente des autres joueurs si il y en a
								if(cl.listJoueur(numTable) > 1) {
									System.out.println("Attente des autres joueurs");
								}
								break;
							case 3:
								System.out.println("Vous décidez d'enchérir sur votre mise");
								System.out.println("Veuillez entrer le montant de jetons à rajouter :");
								miseFaite = false;
								while(!miseFaite) {
									System.out.println("Veuillez entrer votre mise :");
									try {
										miseJoueur = lecture.nextInt();
										if(miseJoueur > miseMinimale && miseJoueur < miseMaximale) {
											System.out.println("Vous avez misé : "+miseJoueur);
											cl.miser(numTable, numJoueur, miseJoueur);
											miseFaite = true;
										}
										if(!miseFaite) {
											System.out.println("Erreur de saisie !");
											lecture.nextLine();
										}
									} catch (InputMismatchException e) {
										System.out.println("Mise incorrecte !");
									}	
								}
								break;
							case 4:
								System.out.println("Vous quittez la table"+numTable);
								cl.quitterTable(numTable, numJoueur);
								//retour en salle d'attente
								statut = true;
								carteEnMain = false;
								break;
							default:
								System.out.println("Le choix doit être 1, 2, 3 ou 4");
						}
					}
					else {
						//Si stand = true on recommence la partie ou on quitte la table
						new CountDown(3);
						System.out.println("Fin de partie voulez vous recommencer ? (oui(1) non(2)) :");
						//lecture du choix du client
						choix = lecture.nextInt();
						if(choix == 1) {
							//recommencer partie
							stand = false;
							miseFaite = false;
							//attente que la partie recommence
							new CountDown(10);
						}
						else if(choix == 2) {
							//quitter table
							System.out.println("Vous quittez la table "+numTable);
							cl.quitterTable(numTable, numJoueur);
							carteEnMain = false;
							break;
						}
					}
					if(statut) {
						break;
					}
				}
				stand = false;
				statut = false;
				miseFaite = false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
