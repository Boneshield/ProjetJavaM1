import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implémentation des méthodes distantes du casino
 * @author mathieu
 * 
 * @see Casino
 * @see CasinoServeur
 */
@SuppressWarnings("serial")
public class CasinoServeurImpl extends UnicastRemoteObject implements CasinoServeur {

	private Casino cn;
	
	/**
	 * Constructeur
	 * @param cn
	 * 			Un objet de classe Casino
	 * @see Casino
	 * @throws RemoteException
	 * 
	 */
	public CasinoServeurImpl(Casino cn) throws RemoteException {
		super();
		this.cn = cn;
	}

	/**
	 * Cree une table pour le joueur
	 * @param taille
	 * 		taille entier de 1 a 6
	 * @param nomJoueur
	 * 		chaine de caractere definissant nom du joueur
	 * @param miseMinimale
	 * 		mise minimale que l on peut miser a cette table
	 * @param miseMaximale
	 * 		mise maximale que l on peut miser a cette table
	 */
	public void creerTable(String nomJoueur, int taille, int miseMinimale, int miseMaximale) {
		//Si la table existe déjà
		if(this.cn.existeTable(nomJoueur)) {
			try {
				this.cn.salleAttente.get(nomJoueur).srv.afficherTexte("La table existe déjà");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		else {
			this.cn.creerTable(nomJoueur, taille, miseMinimale, miseMaximale);
			System.out.println("Creation table par "+nomJoueur);
		}
		
	}
	
	/**
	 * Connecte le joueur et cree un joueur sur le serveur et lui distribue 2 cartes
	 * @param numJoueur
	 * 			le numero du joueur
	 * @param srv
	 * 			l'interface pour communiquer avec ce joueur
	 * @throws RemoteException
	 */
	public void connexion(String numJoueur, Client srv) throws RemoteException {
			System.out.println("Connexion de "+numJoueur);
			this.cn.arriveJoueur(numJoueur, srv);
			System.out.println("Listing des tables pour "+numJoueur);
			this.cn.listeTable(numJoueur);
	}

	/**
	 * Connecte le joueur a la table numTable si il y a de la place
	 * @param numTable
	 * 			le numero de la table
	 * @param numJoueur
	 * 			le numero du joueur
	 * @param srv
	 * 			l interface pour communiquer avec le joueur
	 * @throws RemoteException
	 * @return
	 * 			code de retour pour savoir si la connexion s'est bien passee
	 */
	public int connexionTable(String numTable, String numJoueur, Client srv) throws RemoteException {
		//Si la table n'existe pas
		if(!this.cn.existeTable(numTable)) {
			return 5;
		}
		//Si la table est complète
		if(this.cn.listTables.get(numTable).getNbJoueurCo() >= this.cn.listTables.get(numTable).getTaille()) {
			return 4;
		}
		else {
			System.out.println("Connexion du joueur : "+numJoueur+" a la table "+numTable);
			this.cn.listTables.get(numTable).joinTable(numJoueur, srv);
			//On retire le joueur de la salle d'attente
			this.cn.salleAttente.remove(numJoueur);
			return 0;
		}
	}
	
	/**
	 * Fait quitter la table au joueur
	 * @param numJoueur
	 * 		Chaine de caractere designant le numero du joueur
	 * @throws RemoteException
	 */
	public void quitterTable(String numTable, String numJoueur) throws RemoteException {
		this.cn.listTables.get(numTable).quitTable(numJoueur);
		if(numTable.equals(numJoueur)) {
			System.out.println("La table "+numTable+" a ete supprimee");
			this.cn.supprimerTable(numTable);
		}
	}
	
	
	/**
	 * Demande au croupier de tirer une carte
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	public void hit(String numTable, String numJoueur) throws RemoteException {
		if(this.cn.listTables.get(numTable).partie.lesJoueurs.get(numJoueur).EstElimine()) {
			this.cn.listTables.get(numTable).partie.elimination(numJoueur);
		}
		else {
			this.cn.listTables.get(numTable).partie.hit(numJoueur);
		}	
	}

	/**
	 * S'arrete pour la manche actuelle : ne demande plus de carte et attends le score final
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	public void stand(String numTable, String numJoueur) throws RemoteException {
		this.cn.listTables.get(numTable).partie.stand(numJoueur);
	}

	/**
	 * Affiche la main du joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	public void afficherMain(String numTable, String numJoueur) throws RemoteException {
		this.cn.listTables.get(numTable).partie.afficherMain(numJoueur);
	}

	/**
	 * Demande la taille de la main du joueur au serveur pour l'afficher ensuite
	 * @param ntable
	 * 			le numero de la table
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return la liste des cartes de la main du joueur
	 * @throws RemoteException
	 */
	public int returnTailleMain(String numTable, String numJoueur) throws RemoteException {
		return this.cn.listTables.get(numTable).partie.lesJoueurs.get(numJoueur).getMain().size();
	}

	/**
	 * Donne le nombre de joueurs
	 * @return le nombre de joueur : type entier
	 * @throws RemoteException
	 */
	public int listJoueur(String numTable) throws RemoteException {
		return this.cn.listTables.get(numTable).partie.lesJoueurs.size();
	}
	
	/**
	 * Donne le score final au joueur
	 * @param numJoueur
	 * 			le numero du joueur
	 * @return donne le resulat de la manche
	 * @throws RemoteException
	 */
	public int score(String numTable, String numJoueur) throws RemoteException {
		return this.cn.listTables.get(numTable).partie.lesJoueurs.get(numJoueur).calculScore();
	}

	/**
	 * Liste les tables pour le joueur 
	 * @param numJoueur
	 * 			le numero du joueur
	 * @throws RemoteException
	 */
	public void listTables(String numJoueur) throws RemoteException {
		this.cn.listeTable(numJoueur);
	}

	/**
	 * Affiche le solde disponible en jeton sur le compte du joueur
	 * @param numJoueur
	 * 		le numero du joueur
	 * @throws RemoteException
	 */
	public int consulterSolde(String numJoueur) throws RemoteException {
		return this.cn.gestionMises.getSolde(numJoueur);
	}

	/**
	 * Le joueur mise un montant en jetons
	 * @param numJoueur
	 * 		le numero du joueur
	 * @param mise
	 * 		le nombre de jetons mises
	 * @throws RemoteException
	 */
	public void miser(String numTable, String numJoueur, int mise) throws RemoteException {
		this.cn.gestionMises.miser(numJoueur, mise);
	}

	/**
	 * Ajoute une somme en jetons sur le compte du joueur
	 * @param numJoueur
	 * 		le numero du joueur
	 * @param mise
	 * 		le nombre de jetons ajoutes au compte 
	 * @throws RemoteException
	 */
	public void crediter(String numJoueur, int mise) throws RemoteException {
		this.cn.gestionMises.ajoutCredit(numJoueur, mise);
	}

	
	
}
