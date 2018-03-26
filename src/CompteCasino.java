
/**
 * Compte contenant les soldes de jetons d'un joueur au casino
 * @author mathieu
 *
 */
public class CompteCasino {
	
	private int solde;		//Solde du compte en jetons
	
	/**
	 * Constructeur
	 * @param jetons
	 * 		le nombre de jetons de depart sur le compte
	 */
	public CompteCasino(int jetons) {
		this.solde=jetons;
	}
	
	/**
	 * Ajoute des jetons sur le compte
	 * @param credit
	 * 		le nombre de jetons a mettre sur le compte
	 */
	public void ajoutCredit(int credit) {
		this.solde += credit;
	}
	
	/**
	 * Retourne le solde actuel du compte
	 * @return
	 */
	public int getJetons() {
		return this.solde;
	}
		
	/**
	 * Mise une somme de jeton (retire les jetons du compte)
	 * @param mise
	 * On considère que les jetons sont enlevés a partir du moment ou ils ont été misés
	 * 
	 */
	public void miser(int mise) {
		solde -= mise;
	}
}
