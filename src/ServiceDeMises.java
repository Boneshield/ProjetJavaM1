import java.util.HashMap;
import java.util.Scanner;

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
	

/*********************************/
/*********************************/
/*A ajouter dans le main client/bj ??*/


public static void main(String[] args) {
	/*A virer*/
	Scanner lecture;
	lecture = new Scanner(System.in);
	String numJoueur = null;
	
	/**/
	int miseDebut = 0;
	int miseTable= 0;
	int miseEnJeu = 0;
	int miseTotale = miseTable + miseDebut + miseEnJeu;;
	
	/*A ajouter dans le main (bj) ? */
	ServiceDeMises gestionMises = new ServiceDeMises();
	gestionMises.creerCompte(numJoueur, 100);
	
	/*Mise de départ obligatoire a ajouter pour chaque table ?*/
	if(gestionMises.getSolde(numJoueur) < miseTable) {
		System.out.println("Votre crédit de jetons est insuffisant pour cette table !");
		/*Faire quitter la table*/
	}
	else {
			System.out.println("La mise de départ de cette table est : " + miseTable);
			//gestionMises.miseInitiale(numJoueur, miseTable);
	}
	
	/*Mise supplémentaire avant début du tour de jeu*/
	
	do {
	System.out.println("Entrer votre mise : ");
		miseDebut = lecture.nextInt();
		
		if(gestionMises.getSolde(numJoueur) < miseDebut) {
			System.out.println("Vous n'avez pas assez de crédit, misez moins !");
		}
		else {
			System.out.println("Vous venez de miser : " + miseDebut);
		}
	} while(gestionMises.getSolde(numJoueur) < miseDebut);
	
	
	/*Mise supplémentaire en jeu*/
	do {
		System.out.println("Entrer votre mise : ");
			miseEnJeu = lecture.nextInt();
			
			if(gestionMises.getSolde(numJoueur) < miseEnJeu) {
				System.out.println("Vous n'avez pas assez de crédit, misez moins !");
			}
			else {
				System.out.println("Vous venez de miser : " + miseEnJeu);
			}
		} while(gestionMises.getSolde(numJoueur) < miseEnJeu);
	
	/*Si le joueur gagne*/
	
		System.out.println("Féliciations vous remportez la mise!");
		//gestionMises.gagnerMise(numJoueur, miseTotale);
		System.out.println("Vous avez été crédité de : " + miseTotale*2 + " jetons");
		System.out.println("Votre nouveau solde de jetons : " + gestionMises.getSolde(numJoueur));
		
	/*Si il y a égalité*/
		System.out.println("Il y a égalité!");
		//gestionMises.egaliteMise(numJoueur, miseTotale);
		System.out.println("Vous avez été crédité de : " + miseTotale + " jetons");
		System.out.println("Votre solde de jetons reste le même : " + gestionMises.getSolde(numJoueur));

	/*Si le joueur perd, les mise sont déjà enlevées de son solde*/

		System.out.println("Vous avez perdu vos mises!");
		System.out.println("Vous avez perdu : " + miseTotale + " jetons");
		System.out.println("Votre nouveau solde de jetons : " + gestionMises.getSolde(numJoueur));
}
}
		
