package terrains;

/**
 * La classe Cachette modélise les cachettes cocotier où les moussaillons peuvent se cacher à l'aide d'un atout cocotier
 * La classe Cachette hérite de TerrainMoussaillon car seuls les moussaillons peuvent s'y trouver
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Cachette extends TerrainMoussaillon {

	//Constructeur
	
	/**
	 * Constructeur
	 * @param nbJoueur Nombre de joueurs de la partie
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Cachette(int nbJoueur,int x, int y) {
		super(nbJoueur,x,y);
	}
	
}
