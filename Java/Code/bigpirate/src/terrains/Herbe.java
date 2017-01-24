package terrains;


/**
 * La classe Herbe modélise les cases herbes du plateau
 * Elle hérite de Obstacle car le pirate et les moussaillons ne peuvent s'y trouver
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Herbe extends Obstacle {
	
	//Constructeur
	
	/**
	 * Constructeur
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
 	 */
	public Herbe(int x, int y) {
		super(x,y);	
	}
	
}
