
/**
 * Classe qui defini un joueur par son nom, sa main qui est une liste de carte, son etat stand et son interface pour le serveur
 * @author mathieu
 *
 * @see Personne
 */

public class Joueur extends Personne{
	
	private String numJoueur;
	private boolean etatMise;
	private int miseActuelle;
	protected Client srv;
	
	/**
	 * Constructeur joueur classique
	 * @param numJoueur
	 * 		Nom du joueur
	 * @param srv
	 * 		Interface pour que le serveur communique avec le joueur
	 */
	public Joueur(String numJoueur, Client srv) {
		super();
		this.numJoueur = numJoueur;
		this.etatMise = false;
		this.miseActuelle = 0;
		this.srv = srv;
	}
	
	/**
	 * Retourne le numero du joueur courant
	 * @return Une chaine de caractere qui correpond à son nom/numero
	 */
	public String getNumJoueur() {
		return this.numJoueur;
	}
	
	/**
	 * Hit : tire une carte pour ce joueur depuis le jeu de carte
	 * Affiche la nouvelle main au joueur
	 * @see JeuDeCarte
	 */
	public void hit(JeuDeCarte jeu) {
		super.hit(jeu);
	}
	
	/**
	 * Retourne vrai si le joueur a deja misé au moins une fois et faux sinon
	 * @return
	 */
	public boolean getEtatMise() {
		return this.etatMise;
	}
	
	/**
	 * Change l etat du joueur si il a mise ou non
	 * @param etatMise
	 */
	public void setEtatMise(boolean etatMise) {
		this.etatMise = etatMise;
	}
	
	/**
	 * Retourne la mise actuelle du joueur
	 * @return
	 */
	public int getMiseActuelle() {
		return this.miseActuelle;
	}
	
	/**
	 * Change la mise actuelle en la mise passée en paramètre
	 * @param mise
	 */
	public void setMiseActuelle(int mise) {
		this.miseActuelle = mise;
	}
	
	
}