package terrains;

import ramassables.*;

/**
 * La classe Grotte modélise l'endroit où commence le pirate et la case où se trouve le trésor
 * Elle hérite de Sentier, car le Pirate et les Moussaillons peuvent s'y trouver
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Grotte extends Sentier {

	
	//Constructeur
	
	/**
	 * Constructeur
	 * Remplit la collection de trésors en fonction du nombre de joueurs
	 * @param nbJoueur nombre de joueurs de la partie
	 * @param x numéro de la ligne dans le plateau
	 * @param y numéro de la colonne dans le plateau
	 */
	public Grotte (int nbJoueur,int x, int y) {
		super(nbJoueur,x,y);	//Création d'un terrain
		for (int i=0;i<nbJoueur;i++) {
			this.getTresors().add(new Tresor());	//Remplissage de la collection de trésors en fonction du nombre de joueurs
		}
	}
	
}
