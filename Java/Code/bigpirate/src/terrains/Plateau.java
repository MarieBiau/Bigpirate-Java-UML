package terrains;

import java.util.HashSet;
import java.util.Set;
import obs.*;
import ramassables.*;

/**
 * La classe Plateau modélise le plateau du jeu
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Plateau implements Observable {

	//Attributs
	
	/**
	 * Un tableau à 2 dimensions de Terrain représentant le tableau
	 */
	private Terrain[][] plateau;

	/**
	 * La case du pirate
	 */
	private Sentier caseP;

	/**
	 * La dernière case sur laquelle s'est déplacé le pirate
	 */
	private Sentier casePrecedente;

	/**
	 * La case du fantôme
	 */
	private Terrain caseF;

	/**
	 * La case du moussaillon 1
	 */
	private TerrainMoussaillon caseMoussaillon1;

	/**
	 * La case du moussaillon 2
	 */
	private TerrainMoussaillon caseMoussaillon2;

	/** 
	 * La case du moussaillon 3
	 */
	private TerrainMoussaillon caseMoussaillon3;

	/**
	 * La case où se trouve la grotte
	 */
	private Grotte caseGrotte;

	/**
	 * Un tableau contenant les cases Barque
	 */
	private Barque[] casesBarque;
	
	/**
	 * Collection d'observeurs à mettre à jour lors de la modification du plateau
	 */
	private Set<Observer> observers = new HashSet<Observer>();

	
	//Méthodes

	/**
	 * Constructeur
	 */
	public Plateau(){
	}

	/**
	 * Méthode d'initialisation du plateau de taille 12x12
	 * Crée chaque case contenue dans le tableau
	 * La case en haut à gauche est la case (0,0). La case au dessous est la case (1,0).
	 * Place les personnages à leurs cases de départ
	 * @param nbJoueurs nombre de moussaillons dans la partie
	 */
	public void initialisation1 (int nbJoueurs){

		plateau = new Terrain [12][12];
		int n = nbJoueurs;
		int i;
		int j;

		/*
		 * Eau = 0
		 * Sentier = 1
		 * SCocotier = 2
		 * Cachette = 3
		 * Herbe = 4
		 * Grotte = 5
		 * Barque = 6
		 */

		int[] matrice0 = {0,0,0,0,0,0,0,0,0,0,0,0};
		int[] matrice1 = {0,1,1,1,1,2,1,1,1,2,1,0};
		int[] matrice2 = {0,2,3,4,1,3,1,4,4,3,1,0};
		int[] matrice3 = {0,1,4,1,5,4,1,4,3,4,1,0};
		int[] matrice4 = {0,1,4,1,4,1,1,1,2,4,1,0};
		int[] matrice5 = {0,1,1,1,1,1,3,4,1,1,1,0};
		int[] matrice6 = {0,1,4,1,4,4,2,1,1,4,1,0};
		int[] matrice7 = {0,2,3,1,4,4,1,4,1,4,1,0};
		int[] matrice8 = {0,1,4,2,3,4,1,4,1,4,1,0};
		int[] matrice9 = {0,1,4,1,4,4,1,4,1,4,1,0};
		int[] matrice10 = {0,1,1,1,1,1,1,1,1,1,1,6};
		int[] matrice11 = {0,0,0,0,0,0,0,0,0,0,6,6};

		int[][] matrice = {matrice0,matrice1,matrice2,matrice3,matrice4,matrice5,matrice6,matrice7,matrice8,matrice9,matrice10,matrice11};	

		for (i=0;i<12;i++){
			for(j=0;j<12;j++){
				switch (matrice[i][j]){
				case 0:
					plateau[i][j] = new Eau(i,j);
					break;
				case 1:
					plateau[i][j] = new Sentier(n,i,j);
					break;
				case 2:
					plateau[i][j] = new SCocotier(n,i,j);
					break;
				case 3:
					plateau[i][j] = new Cachette(n,i,j);
					break;
				case 4:
					plateau[i][j] = new Herbe(i,j);
					break;
				case 5:
					plateau[i][j] = new Grotte(n,i,j);
					break;
				case 6:
					plateau[i][j] = new Barque(n,i,j);
					break;
				}
			}
			notifyObservers();
		}


		((Grotte)plateau[3][4]).setPirate(true);
		((Sentier)plateau[2][10]).setFantome(true);
		caseP = (Sentier)plateau[3][4];
		caseGrotte = (Grotte)caseP;
		casePrecedente = (Sentier) plateau[3][4];
		caseF = plateau[1][10];
		casesBarque = new Barque[3];
		casesBarque[0] = (Barque)plateau[11][11];
		casesBarque[1] = (Barque)plateau[11][10];
		casesBarque[2] = (Barque)plateau[10][11];
		caseMoussaillon1 = (TerrainMoussaillon) plateau[10][11];

		HashSet<Ramassable> tresor = new HashSet<Ramassable>();
		for (int k=0; k<n; k++){
			tresor.add(new Tresor());
		}
		caseGrotte.deposer(tresor);

		if (n == 1) {
			((Barque)plateau[10][11]).setMoussaillon(1, true);	
		}
		if (n == 2) {
			((Barque)plateau[10][11]).setMoussaillon(1, true);
			((Barque)plateau[11][10]).setMoussaillon(2, true);
			caseMoussaillon2 = (TerrainMoussaillon) plateau[11][10];	
		}
		if (n == 3) {
			((Barque)plateau[10][11]).setMoussaillon(1, true);
			((Barque)plateau[11][10]).setMoussaillon(2, true);
			((Barque)plateau[11][11]).setMoussaillon(3, true);
			caseMoussaillon2 = (TerrainMoussaillon) plateau[11][10];	
			caseMoussaillon3 = (TerrainMoussaillon) plateau[11][11];
		}
		
	}

	/**
	 * Permet de modifier la case du pirate. Met à jour le plateau.
	 * @param caseP la nouvelle case du pirate
	 */
	public void setCaseP(Sentier caseP) {
		this.caseP.setPirate(false);
		this.caseP = caseP;
		this.caseP.setPirate(true);
		notifyObservers();
	}
	
	/**
	 * Permet de modifier la case précédente du pirate
	 * @param casePrecedente la dernière case sur laquelle s'est déplacé le pirate
	 */
	public void setCasePrecedente(Sentier casePrecedente) {
		this.casePrecedente = casePrecedente;
	}

	/**
	 * Permet de modifier la case du fantôme. Met à jour le plateau.
	 * @param caseF la nouvelle case du fantôme
	 */
	public void setCaseF(Terrain caseF) {
		this.caseF.setFantome(false);
		this.caseF = caseF;
		this.caseF.setFantome(true);
		notifyObservers();
	}

	/**
	 * Permet de modifier la case d'un moussaillon. Met à jour le plateau.
	 * @param n numéro du moussaillon à déplacer (1 à 3)
	 * @param terrain la nouvelle case du moussaillon
	 */
	public void setCaseMoussaillon(int n, TerrainMoussaillon terrain) {
		switch (n){
		case 1 :
			this.caseMoussaillon1.setMoussaillon(n, false);
			this.caseMoussaillon1 = terrain;
			this.caseMoussaillon1.setMoussaillon(n, true);
			break;
		case 2 :
			this.caseMoussaillon2.setMoussaillon(n, false);
			this.caseMoussaillon2 = terrain;
			this.caseMoussaillon2.setMoussaillon(n, true);
			break;
		case 3 :
			this.caseMoussaillon3.setMoussaillon(n, false);
			this.caseMoussaillon3 = terrain;
			this.caseMoussaillon3.setMoussaillon(n, true);
			break;
		}
		notifyObservers();
	}

	/**
	 * Permet d'obtenir la case du pirate
	 * @return la case du pirate
	 */
	public Sentier getCaseP() {
		return caseP;
	}
	
	/**
	 * Permet d'obtenir la case du fantome
	 * @return la case du fantome
	 */
	public Terrain getCaseF() {
		return caseF;
	}
	
	/**
	 * Permet d'obtenir la case d'un moussaillon
	 * @param n le numéro du moussaillon
	 * @return la case du moussaillon
	 */
	public TerrainMoussaillon getCaseMoussaillon(int n) {
		switch (n) {
		case 1 :
			return caseMoussaillon1;
		case 2 :
			return caseMoussaillon2;
		case 3 :
			return caseMoussaillon3;
		}
		return null;
	}
	
	/**
	 * Permet d'obtenir la case où se trouve la grotte
	 * @return la case Grotte
	 */
	public Grotte getCaseGrotte() {
		return caseGrotte;
	}

	/**
	 * Permet d'obtenir les cases Barques
	 * @return le tableau de cases Barque
	 */
	public Barque[] getCasesBarque() {
		return casesBarque;
	}
	
	/**
	 * Permet d'obtenir le plateau du jeu
	 * @return le plateau
	 */
	public Terrain[][] getPlateau() {
		return plateau;
	}

	/**
	 * Permet d'obtenir la case de coordonnées (x,y)
	 * @param x le numéro de ligne de la case voulue
	 * @param y le numéro de colonne de la case voulue
	 * @return la case voulue
	 */
	public Terrain getCase(int x, int y) {
		if( x>=0 && y >=0 && x<12 && y<12){
			return ((plateau[x])[y]);
		}
		return null;
	}

	/**
	 * Permet d'obtenir la case à gauche de la case passée en paramètre
	 * @param t la case dont on veut connaître la voisine de gauche
	 * @return la case de gauche
	 */
	public Terrain getGauche(Terrain t) {
		return getCase(t.getX(),t.getY()-1);
	}

	/**
	 * Permet d'obtenir la case à droite de la case passée en paramètre
	 * @param t la case dont on veut connaître la voisine de droite
	 * @return la case de droite
	 */
	public Terrain getDroite(Terrain t) {
		return getCase(t.getX(),t.getY()+1);
	}

	/**
	 * Permet d'obtenir la case en haut de la case passée en paramètre
	 * @param t la case dont on veut connaître la voisine du haut
	 * @return la case du haut
	 */
	public Terrain getHaut(Terrain t) {
		return getCase(t.getX()-1,t.getY());
	}

	/**
	 * Permet d'obtenir la case en bas de la case passée en paramètre
	 * @param t la case dont on veut connaître la voisine du bas
	 * @return la case du bas
	 */
	public Terrain getBas(Terrain t) {
		return getCase(t.getX()+1,t.getY());
	}

	/**
	 * Permet de savoir si un moussaillon est dans une cachette
	 * @param n le numéro du moussaillon (entre 1 et 3)
	 * @return True si le moussaillon n est sur une case Cachette
	 */
	public boolean estCache (int n) {
		return (getCaseMoussaillon(n) instanceof Cachette);
	}
		
	/**
	 * Méthode de calcul des déplacements possibles du fantôme
	 * Retourne une collection de tableaux de Terrain. Chaque tableau correspond à un déplacement possible du fantôme
	 * @param scoreDe le nombre de cases que le fantôme peut parcourir
	 * @param res une collection vide de tableaux de Terrain
	 * @return une collection de tableaux de Terrain
	 */
	public HashSet<Terrain[]> cheminsFantome (int scoreDe, HashSet<Terrain[]> res) {
		if (scoreDe==0) { //Condition d'arrêt de la récursivité
			return res; //On retourne le résultat
		}
		else {
			if (res.isEmpty()){ //Si la collection est vide : c'est la première itération

				//On crée 4 tableaux de Terrain pour les 4 directions possibles
				Terrain[] t1 = new Terrain[scoreDe+1];
				Terrain[] t2 = new Terrain[scoreDe+1];
				Terrain[] t3 = new Terrain[scoreDe+1];
				Terrain[] t4 = new Terrain[scoreDe+1];
				//On stocke la case sur laquelle le fantôme se trouvait
				t1[scoreDe] = caseF;
				t2[scoreDe] = caseF;
				t3[scoreDe] = caseF;
				t4[scoreDe] = caseF;

				//On teste chaque case voisine pour savoir si celle-ci existe
				//Si oui on ajoute cette case dans un tableau que l'on ajoute dans la collection
				if (getGauche(caseF) != null){
					t1[scoreDe-1] = getGauche(caseF);
					res.add(t1);
				}
				if (getDroite(caseF) != null){
					t2[scoreDe-1] = getDroite(caseF);
					res.add(t2);
				}
				if (getHaut(caseF) != null){
					t3[scoreDe-1] = getHaut(caseF);
					res.add(t3);
				}
				if (getBas(caseF) != null){
					t4[scoreDe-1] = getBas(caseF);
					res.add(t4);
				}
				//Récursivement, on diminue le score du dé et on donne les déplacements calculés jusqu'à présent
				return cheminsFantome(scoreDe-1,res);
			}
			//Si la collection passée en paramètre n'est pas vide : itération > 1
			else{
				//On crée une nouvelle collection résultat pour ne pas garder les anciens déplacements
				HashSet<Terrain[]> resBis = new HashSet<Terrain[]>();

				//On parcourt la collection de déplacements déjà calculés
				for(Terrain[] t:res){

					//On teste pour chaque déplacement, toutes les cases voisines de la dernière case présente dans le tableau
					//On exclut la case voisine qui créerait un déplacement vers l'arrière
					//Et on vérifie que la case voisine existe
					if (t[scoreDe+1] != getGauche(t[scoreDe]) && getGauche(t[scoreDe])!= null ){
						//On crée un nouveau tableau de Terrain
						Terrain[] tab1 = new Terrain[t.length];
						//On remplit ce nouveau tableau des anciennes cases parcourus
						for (int i=t.length-1;i>=scoreDe;i--){
							tab1[i] = t[i];
						}
						//On ajoute la nouvelle case dans le tableau nouvellement créé
						tab1[scoreDe-1] = getGauche(t[scoreDe]); 
						//On ajoute le tableau dans la collection
						resBis.add(tab1);
					}
					if (t[scoreDe+1] != getDroite(t[scoreDe]) && getDroite(t[scoreDe])!= null){
						Terrain[] tab2 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab2[i] = t[i];
						}
						tab2[scoreDe-1] = getDroite(t[scoreDe]);
						resBis.add(tab2);
					}
					if (t[scoreDe+1] != getHaut(t[scoreDe]) && getHaut(t[scoreDe])!= null){
						Terrain[] tab3 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab3[i] = t[i];
						}
						tab3[scoreDe-1] = getHaut(t[scoreDe]);
						resBis.add(tab3);
					}
					if (t[scoreDe+1] != getBas(t[scoreDe]) && getBas(t[scoreDe])!= null){
						Terrain[] tab4 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab4[i] = t[i];
						}
						tab4[scoreDe-1] = getBas(t[scoreDe]);
						resBis.add(tab4);
					}
				}
				//Récursivement, on décrémente le score du dé et on passe la ouvelle collection des déplacements nouvellement calculés en paramètre
				return cheminsFantome(scoreDe-1,resBis);
			}
		}
	}

	/**
	 * Méthode de calcul des déplacements possibles du pirate
	 * Retourne une collection de tableaux de Terrain. Chaque tableau correspond à un déplacement possible du pirate
	 * @param scoreDe le nombre de cases que le pirate peut parcourir
	 * @param res une collection vide de tableaux de Terrain
	 * @return une collection de tableaux de Terrain contenant tous les déplacements possibles du pirate
	 */
	public HashSet<Terrain[]> cheminsPirate (int scoreDe, HashSet<Terrain[]> res){
		//Condition d'arrêt de la récursivité
		if (scoreDe==0) {
			return res; 
		}
		else {
			//Si la collection res est vide : première itération
			if (res.isEmpty()){
				//On crée un nouveau tableau de taille scoreDe+2
				Terrain[] t = new Terrain[scoreDe+2];
				//On stocke la case où se trouve le pirate et la case sur laquelle il se trouvait au tour précédent
				//Cela permettra d'obliger le pirate à se déplacer en avant
				t[scoreDe+1] = casePrecedente;
				t[scoreDe] = caseP;
				//On ajoute ce tableau dans la collection résultat
				res.add(t);
			}

			//On crée une nouvelle collection de tableaux. Cela nous permettra d'exclure les anciens tableaux non pleins
			HashSet<Terrain[]> resBis = new HashSet<Terrain[]>();

			//On parcourt la collection de tableaux passée en paramètre
			for(Terrain[] t:res){
				//Si lors d'un déplacement le pirate rencontre un trésor ou un moussaillon
				//Le déplacement ne doit pas aller plus loin
				//Dans ce cas on ne calcule pas de déplacement supplémentaire
				if (((TerrainMoussaillon)t[scoreDe]).isMoussaillon() || (((Sentier)t[scoreDe]).isTresor() && t[scoreDe].getX()!=caseGrotte.getX() && t[scoreDe].getY()!=caseGrotte.getY())){
					//On rajoute la case où se trouve le moussaillon ou le trésor
					t[scoreDe-1] = t[scoreDe];
					//On ajoute le tableau à la collection
					resBis.add(t);
				}
				else{
					//Sinon on teste pour chaque case voisine de la dernière case calculée :
					//si elle est différente de la case précédente : cela permet d'éviter de marcher en arrière
					//si elle est de la classe Sentier : cela permet de ne marcher uniquement sur les sentiers
					if ((Terrain)t[scoreDe+1] != getGauche(t[scoreDe]) && getGauche(t[scoreDe]) instanceof Sentier ){
						//Alors on crée un nouveau tableau 
						Terrain[] tab1 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							//On le remplit des cases précédemment calculées
							tab1[i] = t[i];
						}
						//On ajoute la nouvelle case sur laquelle le pirate passera, dans le tableau
						tab1[scoreDe-1] = getGauche(t[scoreDe]);
						//On ajoute le tableau dans la collection résultat
						resBis.add(tab1);
					}
					if ((Terrain)t[scoreDe+1] != getDroite(t[scoreDe]) && getDroite(t[scoreDe]) instanceof Sentier){
						Terrain[] tab2 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab2[i] = t[i];
						}
						tab2[scoreDe-1] = getDroite(t[scoreDe]);
						resBis.add(tab2);
					}
					if ((Terrain)t[scoreDe+1] != getHaut(t[scoreDe]) && getHaut(t[scoreDe]) instanceof Sentier){
						Terrain[] tab3 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab3[i] = t[i];
						}
						tab3[scoreDe-1] = getHaut(t[scoreDe]);
						resBis.add(tab3);
					}
					if ((Terrain)t[scoreDe+1] != getBas(t[scoreDe]) && getBas(t[scoreDe]) instanceof Sentier){
						Terrain[] tab4 = new Terrain[t.length];
						for (int i=t.length-1;i>=scoreDe;i--){
							tab4[i] = t[i];
						}
						tab4[scoreDe-1] = getBas(t[scoreDe]);
						resBis.add(tab4);
					}
				}
			}
			//Récursivité : on décrémente le score du dé et on passe la collection resBis contenant les nouveaux déplacements calculés
			return cheminsPirate(scoreDe-1,resBis);
		}
	}

	/**
	 * Méthode de calcul des déplacements possibles du moussaillon
	 * Retourne une collection de tableaux de Terrain. Chaque tableau correspond à un déplacement possible du moussaillon
	 * @param scoreDe le nombre de cases que le moussaillon peut parcourir
	 * @param res une collection vide de tableaux de Terrain
	 * @param caseM la case à partir de laquelle on calcule les déplacements
	 * @return une collection de tableaux de Terrain contenant tous les déplacements possibles du moussaillon
	 */
	public HashSet<Terrain[]> cheminsMoussaillon (int scoreDe, HashSet<Terrain[]> res, TerrainMoussaillon caseM) {
		//Condition d'arrêt de la récursivité
		if (scoreDe==0) {
			return res;
		}
		else {
			//Si la collection passée en paramètre est vide : première itération
			if (res.isEmpty()){
				//Le moussaillon peut se déplacer dans toutes les directions
				//On crée donc un tableau par direction
				Terrain[] t1 = new Terrain[scoreDe+1];
				Terrain[] t2 = new Terrain[scoreDe+1];
				Terrain[] t3 = new Terrain[scoreDe+1];
				Terrain[] t4 = new Terrain[scoreDe+1];
				//On stocke la case sur laquelle se trouve le moussaillon
				t1[scoreDe] = caseM;
				t2[scoreDe] = caseM;
				t3[scoreDe] = caseM;
				t4[scoreDe] = caseM;
				//On vérifie, pour chaque voisine de la case où se trouve le moussaillon, si elle est de la classe TerrainMoussaillon
				if (getGauche(caseM) instanceof TerrainMoussaillon && !(getGauche(caseM) instanceof Cachette)){
					//Alors, on la stocke dans un tableau
					t1[scoreDe-1] = getGauche(caseM);
					//Que l'on ajoute dans la collection résultat
					res.add(t1);
				}
				if (getDroite(caseM) instanceof TerrainMoussaillon && !(getDroite(caseM) instanceof Cachette)){
					t2[scoreDe-1] = getDroite(caseM);
					res.add(t2);
				}
				if (getHaut(caseM) instanceof TerrainMoussaillon && !(getHaut(caseM) instanceof Cachette)){
					t3[scoreDe-1] = getHaut(caseM);
					res.add(t3);
				}
				if (getBas(caseM) instanceof TerrainMoussaillon && !(getBas(caseM) instanceof Cachette)){
					t4[scoreDe-1] = getBas(caseM);
					res.add(t4);
				}
				//Recursivité : on décrémente le score du dé et on passe la collection résultat en paramètre
				return cheminsMoussaillon(scoreDe-1,res,caseM);
			}
			else{
				//Sinon on crée une nouvelle collection : cela nous permettra d'exclure les tableaux non pleins
				HashSet<Terrain[]> resBis = new HashSet<Terrain[]>();

				//On parcourt la collection de déplacements anciennement calculés
				for(Terrain[] t:res){
					//Si lors d'un déplacement le moussaillon rencontre le pirate
					//Le déplacement ne doit pas aller plus loin, le pirate est un obstacle
					//Dans ce cas on ne calcule pas de déplacement supplémentaire
					if (t[scoreDe] instanceof Sentier && ((Sentier)t[scoreDe]).isPirate()){

						//On rajoute la case précédent celle du pirate
						t[scoreDe-1] = t[scoreDe+1];
						t[scoreDe] = t[scoreDe+1];
						//On ajoute le tableau à la collection
						resBis.add(t);

					}
					else {
						//On vérifie pour chaque case voisine de la dernière case calculée :
						//si elle est différente de la précédente : cela évite les demi-tours
						//si elle est de la classe TerrainMoussaillon : cela permet au Moussailon de ne marcher que sur les TerrainMoussaillon
						if (t[scoreDe+1] != getGauche(t[scoreDe]) && getGauche(t[scoreDe]) instanceof TerrainMoussaillon && !(getGauche(t[scoreDe]) instanceof Cachette)){
							//Alors on crée un nouveau tableau de Terrain
							Terrain[] tab1 = new Terrain[t.length];
							for (int i=t.length-1;i>=scoreDe;i--){
								//on le remplit des anciennes cases pour récupérer le parcours
								tab1[i] = t[i];
							}
							//On ajoute la nouvelle case accessible dans le tableau
							tab1[scoreDe-1] = getGauche(t[scoreDe]);
							//On ajoute le tableau créé dans la nouvelle collection
							resBis.add(tab1);
						}
						if (t[scoreDe+1] != getDroite(t[scoreDe]) && getDroite(t[scoreDe]) instanceof TerrainMoussaillon && !(getDroite(t[scoreDe]) instanceof Cachette)){
							Terrain[] tab2 = new Terrain[t.length];
							for (int i=t.length-1;i>=scoreDe;i--){
								tab2[i] = t[i];
							}
							tab2[scoreDe-1] = getDroite(t[scoreDe]);
							resBis.add(tab2);
						}
						if (t[scoreDe+1] != getHaut(t[scoreDe]) && getHaut(t[scoreDe]) instanceof TerrainMoussaillon && !(getHaut(t[scoreDe]) instanceof Cachette)){
							Terrain[] tab3 = new Terrain[t.length];
							for (int i=t.length-1;i>=scoreDe;i--){
								tab3[i] = t[i];
							}
							tab3[scoreDe-1] = getHaut(t[scoreDe]);
							resBis.add(tab3);
						}
						if (t[scoreDe+1] != getBas(t[scoreDe]) && getBas(t[scoreDe]) instanceof TerrainMoussaillon && !(getBas(t[scoreDe]) instanceof Cachette)){
							Terrain[] tab4 = new Terrain[t.length];
							for (int i=t.length-1;i>=scoreDe;i--){
								tab4[i] = t[i];
							}
							tab4[scoreDe-1] = getBas(t[scoreDe]);
							resBis.add(tab4);
						}
					}
				}
				//Récursivité : on décrémente le score du dé et on passe la nouvelle collection des déplacements calculés en paramètre 
				return cheminsMoussaillon(scoreDe-1,resBis,caseM);
			}
		}
	}

	/**
	 * Permet de placer un trésor aléatoirement sur une case Sentier qui n'est pas la grotte
	 * @param t une collection de Ramassable contenant une instance de Tresor
	 */
	public void aleaTresor(HashSet<Ramassable> t) {
		int x = caseGrotte.getX();
		int y = caseGrotte.getY();
		while (!(plateau[x][y] instanceof Sentier) || plateau[x][y] instanceof Grotte) {
			x = (int)(Math.random()*12);
			y = (int)(Math.random()*12);
		}
		((Sentier)plateau[x][y]).deposer(t);
		notifyObservers();
	}

	/**
	 * Permet de replacer un coffre dans la grotte
	 * @param t une collection de Ramassable contenant une instance de Tresor
	 */

	public void retourGrotte(HashSet<Ramassable> t){
		((Grotte)plateau[caseGrotte.getX()][caseGrotte.getY()]).deposer(t);
		notifyObservers();
	}

	/**
	 * Ajoute un observeur dans la collection d'observeurs
	 * @param l'observeur à ajouter
	 */
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Supprime observeur dans la collection d'observeurs
	 * @param l'observeur à supprimer
	 */
	public void removeObserver(Observer o) {
		observers.remove(o);	
	}

	/**
	 * Notifie les observeurs d'une mise à jour
	 */
	public void notifyObservers() {
		for (Observer o : observers){
			o.update(this);
		}
	}
	
	/**
	 * Notifie les observeurs d'une mise à jour
	 * Ici, le plateau n'a pas besoin de cette méthode update, elle ne fais donc rien
	 * @param un object si on en a besoin
	 */
	public void notifyObservers(Object arg){};

	
	//Main
	
	public static void main(String[] args) {

		/* Tests de la création et de l'utilisation du plateau */
		
		System.out.println("Création et initialisation du plateau avec 2 moussaillons.");
		Plateau p = new Plateau();
		p.initialisation1(2);
		System.out.println("");
		
		System.out.println("Tests concernant le pirate.");
		System.out.println("Case du pirate : " + p.getCaseP());
		System.out.println("Présence du pirate sur sa case : " + ((Sentier)p.getCaseP()).isPirate());
		p.setCaseP((Sentier)p.getCase(5,5));
		System.out.println("Nouvelle case du pirate : " + p.getCaseP());
		System.out.println("Présence du pirate sur une autre case " + (p.getCase(1,4)));
		System.out.println("");

		System.out.println("Tests concernant le fantôme.");
		System.out.println("Case du fantôme " + p.getCaseF());
		System.out.println("Présence du fantôme sur sa case " + (p.getCaseF()).isFantome());
		p.setCaseF(p.getCase(5,7));
		System.out.println("Nouvelle case du fantôme " + p.getCaseF());
		System.out.println("Présence du fantôme sur une autre case " + p.getCase(2,9).isFantome());
		System.out.println("");

		System.out.println("Tests concernant le moussaillon.");
		System.out.println("Case du moussaillon 1 " + p.getCaseMoussaillon(1));
		System.out.println("Présence du moussaillon sur sa case " + ((TerrainMoussaillon)p.getCaseMoussaillon(1)).isMoussaillon());
		p.setCaseMoussaillon(1,(TerrainMoussaillon)p.getCase(5,6));
		System.out.println("Nouvelle case du moussaillon " + p.getCaseMoussaillon(1));
		System.out.println("Test si le moussaillon est caché " + p.estCache(1));
		System.out.println("");
		
		System.out.println("Tests concernant les cases voisines.");
		Terrain c = p.getCase(2,3);
		System.out.println("Case : " + c + " de coordonnées x = " + c.getX() + " et y = " + c.getY());
		System.out.println("Case du haut : " + p.getHaut(c) + " de coordonnées x = " + p.getHaut(c).getX() + " et y = " + p.getHaut(c).getY());
		System.out.println("Case du bas : " + p.getBas(c) + " de coordonnées x = " + p.getBas(c).getX() + " et y = " + p.getBas(c).getY());
		System.out.println("Case de gauche : " + p.getGauche(c) + " de coordonnées x = " + p.getGauche(c).getX() + " et y = " + p.getGauche(c).getY());
		System.out.println("Case de droite : " + p.getDroite(c) + " de coordonnées x = " + p.getDroite(c).getX() + " et y = " + p.getDroite(c).getY());
		System.out.println("");
		
		System.out.println("Tests concernant les méthodes de calcul des déplacements possibles.");
		System.out.println("");
		
		System.out.println("Déplacements possibles du fantôme depuis la case 1,1 :");
		HashSet<Terrain[]> resF = new HashSet<Terrain[]>();
		p.setCaseF(p.getCase(1,1));
		resF = p.cheminsFantome(3, resF);
		for (Terrain[] t : resF){
			for (int i=3; i>-1;i--){
				System.out.println("x = " + t[i].getX() + " y = " + t[i].getY());
			}
			System.out.println("");
		}
		System.out.println("");
		
		System.out.println("Déplacements possibles du pirate depuis la case 1,1 :");
		HashSet<Terrain[]> resP = new HashSet<Terrain[]>();
		p.setCaseP((Sentier)p.getCase(1,1));
		resP = p.cheminsPirate(6,resP);
		for (Terrain[] t : resP){
			for (int i=6; i>-1;i--){
				System.out.println("x = " + t[i].getX() + " y = " + t[i].getY());
			}
			System.out.println("");
		}
		
		System.out.println("Déplacements possibles du moussaillon depuis la case 1,1 :");
		HashSet<Terrain[]> resM = new HashSet<Terrain[]>();
		p.setCaseMoussaillon(1,(TerrainMoussaillon)p.getCase(1,1));
		resM = p.cheminsMoussaillon(3,resM,(TerrainMoussaillon)p.getCaseMoussaillon(1));
		for (Terrain[] t : resM){
			for (int i=3; i>-1;i--){
				System.out.println("x = " + t[i].getX() + " y = " + t[i].getY());
			}
			System.out.println("");
		}
		
		System.out.println("Tests concernant le déplacement des trésors.");
		HashSet<Ramassable> r = new HashSet<Ramassable>();
		r.add(new Tresor());
		System.out.println("La grotte possède initialement 4 trésors.");
		System.out.println(p.caseGrotte.getTresors());
		System.out.println("Un trésor retourne dans la grotte, il y a donc 5 trésors dans la grotte.");		
		p.retourGrotte(r);
		System.out.println(p.caseGrotte.getTresors());
		System.out.println("On vide le plateau de ses trésors.");
		for (int i=0;i<5;i++) {
			p.getCaseGrotte().ramasser();
		}
		System.out.println("On place aléatoirement un trésor sur le plateau.");
		p.aleaTresor(r);
		System.out.println("On parcourt le plateau et on affiche les coordonnées des trésors rencontrés.");		
		for (int i=0;i<12;i++) {
			for (int j=0;j<12;j++) {
				if (p.getCase(i,j) instanceof Sentier && !(((Sentier)(p.getCase(i,j))).getTresors().isEmpty())) {
					System.out.println("Il y a un trésor sur la case de coordonnées x = " + i + " et y = " + j);
				}
			}
		}

	}
}
