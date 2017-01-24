package terrains;


/**
 * La classe Obstacle modélise toutes les cases sur lesquelles les personnages humains ne peuvent se déplacer
 * Seul le fantôme peut y accéder
 * Elle hérite de Terrain car c'est une case du plateau
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */


public abstract class Obstacle extends Terrain {
	
	//Constructeur
	
	/**
	 * Constructeur
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Obstacle (int x, int y) {
		super(x,y);
	}
	
}