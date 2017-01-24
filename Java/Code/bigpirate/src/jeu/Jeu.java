package jeu;

import java.util.HashSet;

import des.*;
import personnages.*;
import ramassables.*;
import terrains.*;
import gui.*;

/**
 * 
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Jeu {

	//Attributs

	/*Personnages*/

	/**
	 * Le pirate 
	 */
	private Pirate pirate;

	/**
	 * Le moussaillon numéro 1
	 */
	private Moussaillon moussaillon1;

	/**
	 * Le moussaillon numéro 2
	 */
	private Moussaillon moussaillon2;

	/**
	 * Le moussaillon numéro 3
	 */
	private Moussaillon moussaillon3;

	/**
	 * Le fantôme
	 */
	private Fantome fantome;

	/**
	 * Le personnage actuellement en train d'effectuer son tour
	 */
	private Personnage persoCourant;

	/*Dés*/

	/**
	 * Le petit dé, lancé par les moussaillons et le fantôme
	 */
	private De petitDe;

	/**
	 * Le grand dé, lancé par le pirate
	 */
	private De grandDe;
	
	/**
	 * Résultat du dernier lancer de dé
	 */
	private int scoreDeCourant;

	/*Plateau*/

	/**
	 * Tableau de terrains à deux dimensions représentant le plateau de jeu
	 * Celui-ci est prédéfini
	 */
	private Plateau plateau;


	//Constructeur

	/**
	 * Constructeur
	 */
	public Jeu() {

	}


	//Méthodes
	
	/*Getters et setters*/
	
	/**
	 * Permet d'obtenir le personnage actuellement en train d'effectuer son tour
	 * @return l'instance du personnage effectuant son tour
	 */
	public Personnage getPersoCourant() {
		return persoCourant;
	}

	/**
	 * Permet d'obtenir le score du dernier lancer de dé
	 * @return le score du dernier lancer de dé
	 */
	public int getScoreDeCourant() {
		return scoreDeCourant;
	}

	/**
	 * Permet de récupérer une instance de moussaillon en fonction de son numéro
	 * @param numMoussaillon est le numéro de l'instance du moussaillon que l'on souhaite récupérer
	 * @return l'instance du moussaillon
	 */
	public Moussaillon getMoussaillon(int numMoussaillon) {
		switch (numMoussaillon) {
		case 1: 
			return moussaillon1;
		case 2:
			return moussaillon2;
		case 3:
			return moussaillon3;
		}
		return null;
	}
	
	/**
	 * Permet de récupérer le numéro d'un moussaillon
	 * @param m l'instance du moussaillon dont on veut connaître le numéro
	 * @return le numéro du moussaillon
	 */
	public int getNumMoussaillon(Moussaillon m) {
		if (m == getMoussaillon(1)) {
			return 1;
		}
		if (m == getMoussaillon(2)) {
			return 2;
		}
		if (m == getMoussaillon(3)) {
			return 3;
		}
		return 0;
	}
	
	
	/*Début de partie*/
	
	/**
	 * Permet de débuter une partie
	 * @param nbJoueurs est le nombre de moussaillons de la partie
	 */
	public void lancerPartie(Integer nbJoueurs) {

		//Initialisation de la partie
		pirate = new Pirate();
		moussaillon1 = new Moussaillon(nbJoueurs);
		if (nbJoueurs>1) {
			moussaillon2 = new Moussaillon(nbJoueurs);
		}
		if (nbJoueurs>2) {
			moussaillon3 = new Moussaillon(nbJoueurs);
		}
		fantome = new Fantome();
		petitDe = new De(3);
		grandDe = new De(6);
		plateau = new Plateau();
		plateau.initialisation1(nbJoueurs);
		persoCourant = moussaillon1;		//Le premier moussaillon joue en premier

		GameGUI game = new GameGUI(plateau,nbJoueurs+1,this);
		plateau.registerObserver(game);

	}


	/*Méthodes générales*/

	/**
	 * Permet de lancer un dé et de calculer les différents parcours possibles
	 * @param perso est le personnage qui souhaite se déplacer
	 * @param n est le numero du moussaillon qui souhaite se déplacer, 0 si ce n'est pas un moussaillon
	 * @param perroquet est un booléen valant True si le moussaillon utilise un perroquet, False sinon
	 * @return un HashSet contenant les différents parcours que le personnage peut effectuer
	 */
	public HashSet<Terrain[]> lancerDe(Personnage perso, int n, boolean perroquet) {
		HashSet<Terrain[]> vide = new HashSet<Terrain[]>();
		if (perso instanceof Pirate) {
			scoreDeCourant = grandDe.seLancer();
			HashSet<Terrain[]> chemin = plateau.cheminsPirate(scoreDeCourant, vide);
			return chemin;
		}
		if (perso instanceof Fantome) {
			scoreDeCourant = petitDe.seLancer();
			HashSet<Terrain[]> chemin = plateau.cheminsFantome(scoreDeCourant, vide);
			return chemin;
		}
		if (perso instanceof Moussaillon) {
			scoreDeCourant = petitDe.seLancer();
			if (perroquet) {
				utiliserPerroquet(getNumMoussaillon((Moussaillon)perso));
				scoreDeCourant = scoreDeCourant*2;
			}
			HashSet<Terrain[]> chemin = plateau.cheminsMoussaillon(scoreDeCourant, vide, plateau.getCaseMoussaillon(n));
			return chemin;
		}
		return null;
	}

	/**
	 * Permet de déplacer un personnage sur le plateau
	 * @param perso est le personnage qui souhaite se déplacer
	 * @param n est le numero du moussaillon qui souhaite se déplacer, 0 si ce n'est pas un moussaillon
	 * @param cheminChoisi est un tableau de cases correspondant au déplacement du personnage
	 * @return un booléen valant True si le personnage courant a gagné. Renvoie toujours False lorsque le personnage courant est le fantome
	 */
	public boolean deplacement(Personnage perso, int n, Terrain[] cheminChoisi) {

		if (perso instanceof Pirate) {
			for (int i=cheminChoisi.length-2;i>-1;i--) {
				plateau.setCasePrecedente((Sentier) cheminChoisi[i+1]);
				plateau.setCaseP((Sentier)cheminChoisi[i]);
				Sentier caseP = plateau.getCaseP();
				if (caseP.isTresor()) {
					HashSet<Ramassable> t;
					t = caseP.ramasser();
					plateau.retourGrotte(t);
				}
				if (caseP.isMoussaillon()) {
					boolean[] mous = caseP.getMoussaillons();
					for (int j=0;j<mous.length;j++) {
						if (mous[j]==true) {
							if (getMoussaillon(j+1).possedeTresor()) {
								HashSet<Ramassable> t;
								t = getMoussaillon(j+1).abandonnerTresor();
								plateau.retourGrotte(t);
							}
							caseP.setMoussaillon(j+1,false);
							getMoussaillon(j+1).eliminer();
						}
					}
				}
			}
			return gagnePirate();
		}

		if (perso instanceof Fantome) {
			for (int i=cheminChoisi.length-1;i>-1;i--) {
				plateau.setCaseF(cheminChoisi[i]);
				Terrain caseF = plateau.getCaseF();
				if (caseF instanceof Sentier && ((Sentier)caseF).isTresor()) {
					HashSet<Ramassable> t;
					t = ((Sentier)caseF).ramasser();
					plateau.aleaTresor(t);
				}
				if ((caseF instanceof TerrainMoussaillon && ((TerrainMoussaillon)caseF).isMoussaillon())) {
					boolean[] mous = ((TerrainMoussaillon)caseF).getMoussaillons();
					for (int j=0;j<mous.length;j++) {
						if (mous[j]==true) {
							if (getMoussaillon(j+1).possedeTresor()) {
								HashSet<Ramassable> t;
								t = getMoussaillon(j+1).abandonnerTresor();
								plateau.aleaTresor(t);
							}
						}
					}
				}
			}
			return false;
		}

		if (perso instanceof Moussaillon) {
			for (int i=cheminChoisi.length-1;i>-1;i--) {
				plateau.setCaseMoussaillon(n,(TerrainMoussaillon)cheminChoisi[i]);
				TerrainMoussaillon caseM = plateau.getCaseMoussaillon(n);
				if (caseM instanceof Sentier && ((Sentier)caseM).isTresor()) {
					HashSet<Ramassable> t;
					t = ((Sentier)caseM).ramasser();
					getMoussaillon(n).recupererTresor(t);
				}
			}
			return gagneMoussaillon(n);
		}
		return false;
	}

	/**
	 * Permet de mettre à jour l'attribut persoCourant
	 */
	public void tourSuivant() {
		if (persoCourant == fantome) {
			persoCourant = moussaillonSuivant(0);
		}
		else if (persoCourant == moussaillon1) {
			if (moussaillonSuivant(1) != null) {
				persoCourant = moussaillonSuivant(1);
			} else {
				persoCourant = pirate;
			}
		}
		else if (Moussaillon.getNombreM()>1 && persoCourant == moussaillon2) {
			if (moussaillonSuivant(2) != null) {
				persoCourant = moussaillonSuivant(2);
			} else {
				persoCourant = pirate;
			}
		}
		else if (Moussaillon.getNombreM()>2 && persoCourant == moussaillon3) {
			persoCourant = pirate;
		}
		else if (persoCourant == pirate) {
			persoCourant = fantome;
		}

	}

	/**
	 * Permet de savoir quel est le prochain moussaillon à jouer
	 * @param n est le numero du moussaillon jouant actuellement et vaut 0 s'il ne s'agit pas d'un moussaillon
	 * @return l'instance du moussaillon qui va jouer
	 */
	public Moussaillon moussaillonSuivant(int n) {
		switch (n) {
		case 0:
			if (moussaillon1.isEnJeu()) {
				return moussaillon1;
			} else if (Moussaillon.getNombreM()>1 && moussaillon2.isEnJeu()) {
				return moussaillon2;
			} else if (Moussaillon.getNombreM()>2 && moussaillon3.isEnJeu()) {
				return moussaillon3;
			}
		case 1:
			if (Moussaillon.getNombreM()>1 && moussaillon2.isEnJeu()) {
				return moussaillon2;
			} else if (Moussaillon.getNombreM()>2 && moussaillon3.isEnJeu()) {
				return moussaillon3;
			}
		case 2:
			if (Moussaillon.getNombreM()>2 && moussaillon3.isEnJeu()) {
				return moussaillon3;
			}
		}
		return null;
	}


	/*Méthodes du tour des moussaillons*/

	/**
	 * Permet de savoir si le moussaillon peut utiliser un atout cocotier
	 * @param numM est le numéro du moussaillon voulant utiliser un atout cocotier
	 * @return un booléen valant True s'il peut utiliser un cocotier et False sinon
	 */
	public boolean peutUtiliserCocotier(int numM) {
		return (getMoussaillon(numM).possedeCocotier() && (plateau.getCaseMoussaillon(numM) instanceof SCocotier || plateau.getCaseMoussaillon(numM) instanceof Cachette));
	}

	/**
	 * Permet de savoir si le moussaillon peut utiliser un atout perroquet
	 * @param numM est le numéro du moussaillon voulant utiliser un atout perroquet
	 * @return un booléen valant True s'il peut utiliser un perroquet et False sinon
	 */
	public boolean peutUtiliserPerroquet(int numM) {
		return getMoussaillon(numM).possedePerroquet();
	}

	/**
	 * Permet de savoir si le moussaillon peut abandonner un trésor
	 * @param numM est le numéro du moussaillon voulant abandonner un trésor
	 * @return un booléen valant True s'il peut abandonner un trésor et False sinon
	 */
	public boolean peutAbandonnerTresor(int numM) {
		return getMoussaillon(numM).possedeTresor();
	}

	/**
	 * Permet à un moussaillon d'abandonner un trésor
	 * @param numM est le numéro du moussaillon voulant abandonner un trésor 
	 */
	public void abandonnerTresor(int numM) {
		TerrainMoussaillon caseM = plateau.getCaseMoussaillon(numM);
		if (caseM instanceof Cachette) {
			if (plateau.getGauche(caseM) instanceof SCocotier) {
				((Sentier)plateau.getGauche(caseM)).deposer(getMoussaillon(numM).abandonnerTresor());
			}
			else if (plateau.getDroite(caseM) instanceof SCocotier) {
				((Sentier)plateau.getGauche(caseM)).deposer(getMoussaillon(numM).abandonnerTresor());
			}
			else if (plateau.getHaut(caseM) instanceof SCocotier) {
				((Sentier)plateau.getGauche(caseM)).deposer(getMoussaillon(numM).abandonnerTresor());
			}
			else if (plateau.getBas(caseM) instanceof SCocotier) {
				((Sentier)plateau.getGauche(caseM)).deposer(getMoussaillon(numM).abandonnerTresor());
			}
		} else {
			((Sentier)caseM).deposer(getMoussaillon(numM).abandonnerTresor());
		}
	}

	/**
	 * Permet à un moussaillon de se cacher
	 * @param numM est le numéro du moussaillon voulant se cacher
	 */
	public void utiliserCocotier(int numM) {
		TerrainMoussaillon caseM = plateau.getCaseMoussaillon(numM);
		if (plateau.getGauche(caseM) instanceof Cachette) {
			getMoussaillon(numM).utiliseCocotier();
			plateau.setCaseMoussaillon(numM,(TerrainMoussaillon)plateau.getGauche(caseM));
		}
		else if (plateau.getDroite(caseM) instanceof Cachette) {
			getMoussaillon(numM).utiliseCocotier();
			plateau.setCaseMoussaillon(numM,(TerrainMoussaillon)plateau.getDroite(caseM));
		}
		else if (plateau.getHaut(caseM) instanceof Cachette) {
			getMoussaillon(numM).utiliseCocotier();
			plateau.setCaseMoussaillon(numM,(TerrainMoussaillon)plateau.getHaut(caseM));
		}
		else if (plateau.getBas(caseM) instanceof Cachette) {
			getMoussaillon(numM).utiliseCocotier();
			plateau.setCaseMoussaillon(numM,(TerrainMoussaillon)plateau.getBas(caseM));
		}	
	}

	/**
	 * Permet à un moussaillon d'utiliser un atout perroquet
	 * @param numM est le numéro du moussaillon voulant utiliser un perroquet
	 */
	public void utiliserPerroquet(int numM) {
		getMoussaillon(numM).utilisePerroquet();
	}

	/**
	 * Permet de savoir si un moussaillon a gagné
	 * @param n le numéro du moussaillon
	 * @return boolean True si le moussaillon n a gagné et False sinon
	 */
	public boolean gagneMoussaillon(int n) {
		return(getMoussaillon(n).possedeTresor() && plateau.getCaseMoussaillon(n) instanceof Barque);
	}


	/*Méthodes du tour du pirate*/

	/**
	 * Permet de savoir si le pirate a gagné
	 * @return boolean True si le pirate a gagné et False sinon
	 */
	public boolean gagnePirate() {
		return (Moussaillon.getNombreM()==0);
	}


	/*Méthodes du tour du fantome*/
	
	/**
	 * Permet de choisir sur quel parcours, parmi ceux possible, le fantôme va effectuer
	 * @param parcours est la collection des différents déplacements que peut effectuer le fantôme
	 * @return un tableau de cases Terrain constituant le déplacement du fantôme
	 */
	public Terrain[] choisirParcoursFantome(HashSet<Terrain[]> parcours) {
		return fantome.choisirParcours(parcours);
	}
	
	
	//Main

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		LauncherGUI launcher = new LauncherGUI();
	}
}