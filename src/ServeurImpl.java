import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
//Cette class n'est plus utilisée
/**
 * Implémentation de l'interface permettant au client d'éxécuter des méthodes sur le serveur
 * @author mathieu
 *
 */

@SuppressWarnings("serial")
public class ServeurImpl extends UnicastRemoteObject implements Serveur {

	BlackJack bj;
	
	/**
	 * Constructeur
	 * @param bj
	 * 			un objet de classe BlackJack
	 * @see BlackJack
	 * @throws RemoteException
	 * 
	 */
	public ServeurImpl(BlackJack bj) throws RemoteException {
		super();
		this.bj= bj;
	}

	/**
	 * Connecte le joueur et cree un joueur sur le serveur et lui distribue 2 cartes
	 * @param numJoueur
	 * 			le numero du joueur
	 * @param srv
	 * 			l'interface pour communiquer avec ce joueur
	 * @throws RemoteException
	 */
	@Override
	public void connexion(String numJoueur, Client srv) throws RemoteException {
			System.out.println("Connexion de "+srv);
			this.bj.creerJoueur(numJoueur, srv);
			System.out.println("Creation du joueur : "+numJoueur);
			this.bj.hit(numJoueur);
			this.bj.hit(numJoueur);
	}

	/**
	 * Demande au croupier de tirer une carte
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void hit(String numJoueur) throws RemoteException {
		if(this.bj.lesJoueurs.get(numJoueur).EstElimine()) {
			this.bj.elimination(numJoueur);
		}
		else {
			this.bj.hit(numJoueur);
		}
		
	}

	/**
	 * S'arrete pour la manche actuelle : ne demande plus de carte et attends le score final
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void stand(String numJoueur) throws RemoteException {
		this.bj.stand(numJoueur);
		//Tirage du croupier
		this.bj.tirageCroupier();
		//Calcul des gains
		this.bj.calculGain();
	}

	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void afficherMain(String numJoueur) throws RemoteException {
		this.bj.afficherMain(numJoueur);
	}

	/**
	 * Demande la main du joueur au serveur pour l'afficher ensuite
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return la liste des cartes de la main du joueur
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Carte> returnMain(String numJoueur) throws RemoteException {
		return this.bj.lesJoueurs.get(numJoueur).getMain();
	}

	/**
	 * Donne le nombre de joueurs
	 * @return le nombre de joueur : type entier
	 * @throws RemoteException
	 */
	@Override
	public int listJoueur() throws RemoteException {
		return this.bj.lesJoueurs.size();
	}
	
	/**
	 * Donne le score final au joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return donne le resulat de la manche
	 * @throws RemoteException
	 */
	public int score(String numJoueur) throws RemoteException {
		return this.bj.lesJoueurs.get(numJoueur).calculScore();
	}

	/**
	 * Cree une table avec le joueur comme createur
	 * @param numJoueur
	 * 		Chaine de caractere definissant le nom du joueur
	 * @param srv
	 * 		Interface pour que le serveur puisse acceder au client
	 * @see Client,Joueur
	 */
	@Override
	public void tablationJoueur(String numJoueur, Client srv) {
		
	}

	/**
	 * Affiche la liste des tables disponibles pour le joueur dans le casino
	 */
	@Override
	public void afficherListTable() {
		
	}
	
}
