/**
 * Compteur pour gérer l'attente des clients et du serveur
 * @author mathieu
 *
 */
public class CountDown {
	
	//on attends un temps de t secondes 
	public CountDown(int t) {
			try {
				Thread.sleep(1000*t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}