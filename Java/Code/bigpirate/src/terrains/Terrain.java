package terrains;

/**
 * La classe Terrain modélise toute case du plateau
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 */

public abstract class Terrain {

	//Attributs
	
	/**
	 * Booléen modélisant la présence du fantôme
	 * True signifie que le fantôme se trouve sur cette case
	 * Un fantôme pouvant se trouver sur toute case du tableau, toute case doit hériter de cet attribut
	 */
	private boolean fantome;
	
	/**
	 * Numéro de ligne de la case dans le plateau
	 */
	private int x;
	
	/**
	 * Numéro de colonne de la case dans le plateau
	 */
	private int y;
	
	
	//Constructeur
	
	/**
	 * Constructeur
	 * Attribue False à fantome car le fantome ne se trouve pas la nouvelle case créée
	 * Attribue les coordonnées de la case dans le plateau à x et y
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Terrain(int x, int y) {
		this.fantome = false;	
		this.x = x;
		this.y = y;
	}

	
	//Méthodes
	
	/**
	 * Permet d'obtenir le numéro de la ligne de la case dans le plateau
	 * @return le numéro de la ligne de la case dans le plateau
	 */
	public int getX() {
		return x;
	}

	/**
	 * Permet d'obtenir le numéro de la colonne de la case dans le plateau
	 * @return le numéro de la colonne de la case dans le plateau
	 */
	public int getY() {
		return y;
	}

	/**
	 * Permet de savoir si le fantôme se trouve sur la case.
	 * @return un booléen True si le fantôme est sur la case.
	 */
	public boolean isFantome() {
		return fantome;
	}

	/**
	 * Permet de changer la position du fantôme
	 * @param fantome booléen True si l'on veut placer le fantôme sur cette case
	 */
	public void setFantome(boolean fantome) {
		this.fantome = fantome;
	}
	
	
	//Main
	
	public static void main(String[] args) {
		/* Tests de l'utilisation des méthodes d'un Terrain */
		Sentier t = new Sentier(3,2,7);
		System.out.println("x = "+t.getX()+" et y = "+t.getY());
		System.out.println(t.isFantome());
		t.setFantome(true);
		System.out.println(t.isFantome());
		t.setFantome(false);
		System.out.println(t.isFantome());
	}

}
