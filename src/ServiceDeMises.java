import java.util.HashMap;

/**
 * Service de gestion des mises des joueurs
 * @author mathieu
 *
 */
public class ServiceDeMises {
	
	private HashMap<String, CompteCasino> baseComptesJoueurs;
	
	/**
	 * Constructeur
	 */
	public ServiceDeMises() {
		baseComptesJoueurs = new HashMap<String, CompteCasino>();
	}
	
	/**
	 * Crée le compte d'un joueur qui viens d'arriver au casino
	 * @param numJoueur
	 * 		le nom du joueur
	 * @param credit
	 * 		la somme de depart en jetons
	 */
	public void creerCompte(String numJoueur, int credit) {
		baseComptesJoueurs.put(numJoueur, new CompteCasino(credit));
	}
	
	/**
	 * Retourne vrai si le compte du joueur existe
	 * @param numJoueur
	 * 		le numero du joueur
	 * @return
	 */
	public boolean compteExiste(String numJoueur) {
		return baseComptesJoueurs.containsKey(numJoueur);
	}
	
	/**
	 * Ajoute des jetons sur le compte du joueur
	 * @param numJoueur
	 * 		le numero du joueur
	 * @param credit
	 * 		le nombre de jetons a ajouter
	 */
	public void ajoutCredit(String numJoueur, int credit) {
		this.baseComptesJoueurs.get(numJoueur).ajoutCredit(credit);
	}
	
	/**
	 * Le joueur mise une somme en jeton
	 * @param numJoueur
	 * @param mise
	 */
	public void miser(String numJoueur, int mise) {
		this.baseComptesJoueurs.get(numJoueur).miser(mise);
	}
	
	/**
	 * Retourne le solde actuel du joueur
	 * @param numJoueur
	 * 		le numero du joueur
	 * @return
	 */
	public int getSolde(String numJoueur) {
		return this.baseComptesJoueurs.get(numJoueur).getJetons();
	}
}
		
