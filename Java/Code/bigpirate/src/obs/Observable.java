package obs;

/**
 * Une classe implémente l'interface Observable lorsqu'il s'agit d'un objet représentant la réalité et étant sujet à des modifications fréquentes
 * Elle notifie alors ses Observer de ses changements
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public interface Observable {
	
	/**
	 * Ajoute un observeur à la liste d'observeurs
	 * @param o l'observeur à ajouter
	 */
	public void registerObserver(Observer o);

	/**
	 * Supprime un observeur de la liste d'observeurs
	 * @param o l'observeur à supprimer
	 */
	public void removeObserver(Observer o);
	
	/**
	 * Si l'observable a changé, notifie les observeurs d'un changement
	 */
	public void notifyObservers();
	
	
}
