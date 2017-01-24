package terrains;

import java.util.HashSet;
import java.util.Iterator;

import ramassables.*;

/**
 * La classe Sentier modélise tout sentier du plateau. Elle modélise également toute case accessible par le pirate.
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 */

public class Sentier extends TerrainMoussaillon {

	//Attributs
	
	/**
	 * Attribut permettant de savoir si le pirate se trouve sur la case
	 */
	private boolean pirate;

	/**
	 * Collection de trésors modélisant les trésors situés sur la case
	 */
	private HashSet<Tresor> tresors;
	
	/**
	 * Collection d'atouts perroquet modélisant les atouts perroquet situés sur la case
	 */
	private HashSet<Perroquet> perroquets;
	
	/**
	 * Collection d'atouts cocotier modélisant les atouts cocotier situés sur la case
	 */
	private HashSet<Cocotier> cocotiers;

	
	//Constructeur
	
	/**
	 * Constructeur
	 * Attribue False à pirate et instancie des collections vides à tresors, perroquets et cocotiers
	 * Un sentier est vide en début de partie
	 * @param nbJoueur nombre de joueurs de la partie
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Sentier (int nbJoueur, int x, int y) {
		super(nbJoueur,x,y);
		this.pirate = false;
		this.tresors = new HashSet<Tresor>();
		this.perroquets = new HashSet<Perroquet>();
		this.cocotiers = new HashSet<Cocotier>();
	}
	
	
	//Méthodes
	
	/**
	 * Permet de savoir si le pirate se trouve sur la case
	 * @return un booléen True si le pirate est sur la case
	 */
	public boolean isPirate() {
		return pirate;
	}

	/**
	 * Permet de faire changer le pirate de case
	 * @param pirate booléen modélisant la présence du pirate
	 * True signifie que le pirate se trouvera sur la case
	 */
	public void setPirate(boolean pirate) {
		this.pirate = pirate;
	}

	/**
	 * Permet d'obtenir la collection de trésors de la case
	 * @return une collection contenant les trésors de la case
	 */
	public HashSet<Tresor> getTresors() {
		return tresors;
	}
	
	/**
	 * Permet d'obtenir la collection de perroquets de la case
	 * @return une collection contenant les perroquets de la case
	*/
	public HashSet<Perroquet> getPerroquets() {
		return perroquets;
	}

	/**
	 * Permet d'obtenir la collection de cocotiers de la case
	 * @return une collection contenant les cocotiers de la case
	 */
	public HashSet<Cocotier> getCocotiers() {
		return cocotiers;
	}

	/**
	 * Permet de savoir si un trésor se trouve sur la case
	 * @return boolean True si un trésor est sur la case
	 */
	public boolean isTresor() {
		return !(tresors.isEmpty());
	}
	
	/**
	 * Permet de déposer des ramassables sur le sentier
	 * Ajoute tous les ramassables de la collection passée en paramètre dans la collection du sentier correspondante
	 * @param une collection de Ramassable à déposer
	 */
	public void deposer(HashSet<Ramassable> ramassable) {
		if(!ramassable.isEmpty()) {
			for (Ramassable r :ramassable) {	//on parcourt la collection de ramassables
				if (r instanceof Tresor) {		//si l'objet dans la collection passée en paramètre est bien un trésor
					Tresor res = (Tresor) r;	//on le cast dans la classe trésor
					this.tresors.add(res);		//on l'ajoute dans la collection de trésors de la case
				}
				if (r instanceof Perroquet) {
					Perroquet res = (Perroquet) r;
					this.perroquets.add(res);
				}
				if (r instanceof Cocotier) {
					Cocotier res = (Cocotier) r;
					this.cocotiers.add(res);
				}
			}
		}
	}

	/**
	 * Permet de ramasser des ramassables sur le sentier
	 * @return une collection de Ramassable contenant les ramassables
	 */
	public HashSet<Ramassable> ramasser() {
		HashSet<Ramassable> res = new HashSet<Ramassable>(); //on crée la collection de Ramassable à retourner
		if (!getPerroquets().isEmpty()) { //s'il y a bien un perroquet sur la case
			for (Perroquet p:perroquets) { //on parcourt la collection pour pouvoir manipuler les objets à l'intérieur
				res.add(p); //on ajoute les objets trouvés à la collection à retourner
			}
			perroquets.clear();
		}
		if (!getCocotiers().isEmpty()) {
			for (Cocotier c:cocotiers) {
				res.add(c);
			}
			cocotiers.clear();
		}
		if (!getTresors().isEmpty()) {
			Iterator<Tresor> iter = tresors.iterator();
			Ramassable t = iter.next();
			res.add(t);
			tresors.remove(t);
		}
		return res;
	}

	
	//Main
	
	public static void main(String[] args) {

		/* Tests de la création et de l'utilisation d'un Sentier et d'un Grotte */
		
		System.out.println("Création d'une grotte et d'un sentier avec 3 moussaillons dans la partie.");
		Grotte g = new Grotte (3,2,3);
		Sentier s = new Sentier(3,1,1);

		System.out.println("Affichage des collections de trésors de la grotte et du sentier.");
		System.out.println(g.isTresor());
		System.out.println("g = " + g.getTresors());
		System.out.println(s.isTresor());
		System.out.println("s = " + s.getTresors());
		System.out.println("");
		
		System.out.println("Echange de 3 trésors de la grotte vers le sentier.");
		s.deposer(g.ramasser());
		s.deposer(g.ramasser());
		s.deposer(g.ramasser());
		
		System.out.println("Affichage des collections de trésors de la grotte et du sentier.");
		System.out.println("g = " + g.getTresors());
		System.out.println("s = " + s.getTresors());
		System.out.println("");
		
		System.out.println("Echange de 3 trésors du sentier vers la grotte.");
		g.deposer(s.ramasser());
		g.deposer(s.ramasser());
		g.deposer(s.ramasser());
		g.deposer(s.ramasser());
	
		System.out.println("Affichage des collections de trésors de la grotte et du sentier.");
		System.out.println("g = " + g.getTresors());
		System.out.println("s = " + s.getTresors());
		System.out.println("");

		System.out.println("Echange d'un trésor de la grotte vers la grotte.");
		g.deposer(g.ramasser());
		
		System.out.println("Affichage de la collection de trésors de la grotte.");
		System.out.println("g = " + g.getTresors());
		System.out.println("");
		
		System.out.println("Test de la présence du pirate sur le sentier.");
		System.out.println(s.isPirate());
		System.out.println("Ajout d'un pirate sur le sentier.");
		s.setPirate(true);
		System.out.println("Test de la présence du pirate sur le sentier.");
		System.out.println(s.isPirate());
		
	}

}
