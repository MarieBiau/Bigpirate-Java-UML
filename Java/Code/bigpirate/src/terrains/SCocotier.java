package terrains;

/**
 * La case SCocotier modélise la case permettant à un moussaillon d'utiliser un atout cocotier pour se cacher
 * Il s'agit de la case devant la cachette
 * Elle hérite de la classe Sentier car le pirate et les moussaillons peuvent s'y trouver
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class SCocotier extends Sentier {

	//Constructeur
	
	/**
	 * Constructeur
	 * @param nbJoueur Nombre de joueurs de la partie
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public SCocotier(int nbJoueur,int x, int y) {
		super(nbJoueur,x,y);
	}
	
}
