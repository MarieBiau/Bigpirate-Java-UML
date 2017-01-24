package terrains;

/**
 * La classe TerrainMoussaillon modélise toute case du plateau accessible par des moussaillons
 * Elle hérite de Terrain car c'est une case du plateau
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 */

public abstract class TerrainMoussaillon extends Terrain {

	//Attributs
	
	/**
	 * Tableau de booléens modélisant la présence de chaque moussaillon
	 * La case 1 correspond au moussaillon 1 etc
	 * True à la case 1 signifie que le moussaillon 1 se trouve sur la case, etc
	 */
	private boolean [] moussaillons;

	
	//Constructeur
	
	/**
	 * Constructeur
	 * Attribue un tableau de nbJoueurs cases à moussaillons
	 * Un TerrainMoussaillon peut contenir autant de moussaillons que de moussaillons présents dans la partie
	 * @param nbJoueurs nombre de moussaillons de la partie
	 */
	public TerrainMoussaillon(int nbJoueurs, int x, int y) {
		super(x,y);
		int i;
		this.moussaillons = new boolean [nbJoueurs];
		for (i=0;i<nbJoueurs;i++) {
			moussaillons[i] = false;
		}
	}
	
	
	//Méthodes
	
	/**
	 * Permet de savoir quels moussaillons sont sur la case
	 * @return un tableau de booléens modélisant la présence de chaque moussaillon
	 */
	public boolean[] getMoussaillons() {
		return moussaillons;
	}

	/**
	 * Permet de changer la position d'un moussaillon
	 * @param number le numéro du moussaillon (allant de 1 au nombre de joueurs de la partie)
	 * @param presence booléen True si l'on veut que le moussaillon soit sur cette case
	 */
	public void setMoussaillon(int number, boolean presence) {
		if (number <= moussaillons.length && number > 0) {
			moussaillons[number-1] = presence;
		}
	}
	
	/**
	 * Permet de savoir si un moussaillon se situe sur la case
	 * @return booléen True s'il y a un moussaillon sur la case, False sinon
	 */
	public boolean isMoussaillon() {
		for (int i=0;i<moussaillons.length;i++) {
			if (moussaillons[i]) {
				return true;
			}
		}
		return false;
	}
	
	
	//Main
	
	public static void main(String[] args) {
		
		/* Tests de l'utilisation des méthodes d'un TerrainMoussaillon */
		
		Sentier t = new Sentier(3,2,7);
		System.out.println("Il n'y a aucun moussaillon sur la case");
		for (int i=0;i<3;i++) {
			System.out.println((t.getMoussaillons())[i]);
		}
		System.out.println(t.isMoussaillon());
		System.out.println("");
		
		System.out.println("Les moussaillons 2 et 3 sont sur la case");
		t.setMoussaillon(2,true);
		t.setMoussaillon(3,true);	
		for (int i=0;i<3;i++) {
			System.out.println((t.getMoussaillons())[i]);
		}
		System.out.println(t.isMoussaillon());
		System.out.println("");
		
		System.out.println("Le moussaillon 2 est sur la case");
		t.setMoussaillon(2,false);
		for (int i=0;i<3;i++) {
			System.out.println((t.getMoussaillons())[i]);
		}
		System.out.println(t.isMoussaillon());

	}
	
}
