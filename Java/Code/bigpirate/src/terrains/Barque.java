package terrains;

/**
 * La classe Barque modélise les cases permettant aux moussaillons de gagner lorsqu'ils ont un trésor
 * Elle hérite de la classe TerrainMoussaillon représentant les cases où le moussaillon peut marcher
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Barque extends TerrainMoussaillon {

	//Constructeur
	
	/**
	 * Constructeur
	 * @param nbJoueur Nombre de joueurs de la partie
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Barque(int nbJoueur,int x, int y) {
		super(nbJoueur,x,y);
	}
	
}
