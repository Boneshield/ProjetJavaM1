/**
 * Compteur pour gérer l'attente des clients et du serveur
 * @author mathieu
 *
 */
public class CountDown {
	
	//on attends un temps de t secondes 
	public CountDown(int t) {
		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t = t - 1;
		}while(t != 0);
	}
}