package personnages;

import java.util.*;
import terrains.*;

/**
 * La classe Fantôme représente le personnage du fantôme.
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Fantome extends Personnage {

	//Constructeur

	/**
	 * Constructeur
	 */
	public Fantome() {

	}


	//Méthodes

	/**
	 * Méthode toString
	 */
	public String toString() {
		return "Fantôme";
	}

	/**
	 * Permet de choisir quel parcours va effectuer le fantôme
	 * S'il peut atteindre un moussaillon alors il va sur cette case, sinon il se déplace aléatoirement
	 * @param parcours est la collection des différents déplacements que peut effectuer le fantôme
	 * @return un tableau de cases Terrain constituant le déplacement du fantôme
	 */
	public Terrain[] choisirParcours(HashSet<Terrain[]> parcours) {

		Iterator<Terrain[]> iterator = parcours.iterator();
		int choix;

		for (Terrain[] t:parcours) {
			for (int i=0;i<t.length;i++) {
				if (t[i] instanceof TerrainMoussaillon && !(t[i] instanceof Cachette)) {
					if (((TerrainMoussaillon)t[i]).isMoussaillon() && ((Sentier)t[i]).isTresor()) {
						return t;
					}
				}
			}
		}

		choix = (int)(Math.random()*parcours.size());
		for (int i=1;i<choix;i++) {
			iterator.next();
		}
		return iterator.next();

	}

	
	//Main
	
	public static void main(String[] args) {
		
		/* Tests de la création et du choix de parcours d'un fantôme */
		
		Plateau p = new Plateau();
		p.initialisation1(3);
		Fantome f = new Fantome();
		HashSet<Terrain[]> res = new HashSet<Terrain[]>();
		Terrain[] t = new Terrain[3];
		t = f.choisirParcours(p.cheminsFantome(3, res));
		for (int i=3; i>-1;i--){
			System.out.println("x = " + t[i].getX() + " y = " + t[i].getY());
		}
		
	}

}
