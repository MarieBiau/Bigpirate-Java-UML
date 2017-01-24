package obs;

/**
 * Une classe implémente l'interface Observer lorsqu'elle veut être informée d'une modification d'un observable
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public interface Observer {
	
	/**
	 * Méthode de mise à jour appelée lorsque l'observable a changé
	 * @param o l'observable observé qui a changé
	 */
	public void update(Observable o);
	
}
	
