import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CasinoServeurImpl extends UnicastRemoteObject implements CasinoServeur {

	Casino cn;
	
	/**
	 * Constructeur
	 * @param cn
	 * 			un objet de classe Casino
	 * @see Casino
	 * @throws RemoteException
	 * 
	 */
	public CasinoServeurImpl(Casino cn) throws RemoteException {
		super();
		this.cn= cn;
	}

	/**
	 * Cree une table pour le joueur
	 * @param taille
	 * 		taille entier de 1 a 6
	 * @param nomJoueur
	 * 		chaine de caractere definissant nom du joueur
	 */
	public void creerTable(int taille, String nomJoueur) {
		this.cn.creerTable(taille);
		
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
	public void connexion(int ntable, String numJoueur, Client srv) throws RemoteException {
		// TODO Auto-generated method stub
			System.out.println("Connexion de "+srv);
			this.cn.listTables.get(ntable).joinTable(numJoueur, srv);
			System.out.println("Creation du joueur : "+numJoueur);
			this.cn.listTables.get(ntable).partie.hit(numJoueur);
			this.cn.listTables.get(ntable).partie.hit(numJoueur);
	}

	/**
	 * Demande au croupier de tirer une carte
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void hit(int ntable, String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		if(this.cn.listTables.get(ntable).partie.lesJoueurs.get(numJoueur).EstElimine()) {
			this.cn.listTables.get(ntable).partie.elimination(numJoueur);
		}
		else {
			this.cn.listTables.get(ntable).partie.hit(numJoueur);
		}	
	}

	/**
	 * S'arrete pour la manche actuelle : ne demande plus de carte et attends le score final
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void stand(int ntable, String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.cn.listTables.get(ntable).partie.stand(numJoueur);
		//Tirage du croupier
		this.cn.listTables.get(ntable).partie.tirageCroupier();
		//Calcul des gains
		this.cn.listTables.get(ntable).partie.calculGain();
	}

	/**
	 * Permet au client de changer la valeur de l'as (1 ou 11)
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void changeAsValue(int ntable, String numJoueur) throws RemoteException{
		// TODO Auto-generated method stub
		this.cn.listTables.get(ntable).partie.changeAsValue(numJoueur);
	}

	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	@Override
	public void afficherMain(int nbtable, String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		this.cn.listTables.get(nbtable).partie.afficherMain(numJoueur);
	}

	/**
	 * Demande la main du joueur au serveur pour l'afficher ensuite
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return la liste des cartes de la main du joueur
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Carte> returnMain(int nbtable, String numJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		return this.cn.listTables.get(nbtable).partie.lesJoueurs.get(numJoueur).getMain();
	}

	/**
	 * Donne le nombre de joueurs
	 * @return le nombre de joueur : type entier
	 * @throws RemoteException
	 */
	@Override
	public int listJoueur(int nbtable) throws RemoteException {
		// TODO Auto-generated method stub
		return this.cn.listTables.get(nbtable).partie.lesJoueurs.size();
	}
	
	/**
	 * Donne le score final au joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return donne le resulat de la manche
	 * @throws RemoteException
	 */
	public int score(int nbtable, String numJoueur) throws RemoteException {
		return this.cn.listTables.get(nbtable).partie.lesJoueurs.get(numJoueur).calculScore();
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
	public void tablationJoueur(int nbtable, String numJoueur, Client srv) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Affiche la liste des tables disponibles pour le joueur dans le casino
	 */
	@Override
	public void afficherListTable() {
		// TODO Auto-generated method stub
		
	}
	
}
