package gui;

/**
 * Interface graphique du jeu
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import java.awt.* ;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;

import terrains.*;
import jeu.*;
import obs.*;
import personnages.*;





public class GameGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;



	/*
	 *	------------------------------------------------
	 *  |infoP.|  plateau                     |actionP.|
	 *  |      |                              |        |
	 *  |      |                              |        |
	 *  |      |                              |        |
	 *  |      |                              |        |
	 *  |      |                              |        |
	 *  |      |                              |        |
	 *  ------------------------------------------------ 
	 */

	/*
	 * Plateau :
	 * 
	 * ---------------------------------------------------------------------------
	 * | 0,0 | 0,1 | 0,2 | 0,3 | 0,4 | 0,5 | 0,6 | 0,7 | 0,8 | 0,9 | 0,10 | 0,11 |
	 * ---------------------------------------------------------------------------
	 * | 1,0 | 1,1 | 1,2 | 1,3 | 1,4 | 1,5 | 1,6 | 1,7 | 1,8 | 1,9 | 1,10 | 1,11 |
	 * ---------------------------------------------------------------------------
	 * | 2,0 | 2,1 | 2,2 | 2,3 | 2,4 | 2,5 | 2,6 | 2,7 | 2,8 | 2,9 | 2,10 | 2,11 |
	 * ---------------------------------------------------------------------------
	 * | 3,0 | 3,1 | 3,2 | 3,3 | 3,4 | 3,5 | 3,6 | 3,7 | 3,8 | 3,9 | 3,10 | 3,11 |
	 * ---------------------------------------------------------------------------
	 * | 4,0 | 4,1 | 4,2 | 4,3 | 4,4 | 4,5 | 4,6 | 4,7 | 4,8 | 4,9 | 4,10 | 4,11 |
	 * ---------------------------------------------------------------------------
	 * | 5,0 | 5,1 | 5,2 | 5,3 | 5,4 | 5,5 | 5,6 | 5,7 | 5,8 | 5,9 | 5,10 | 5,11 |
	 * ---------------------------------------------------------------------------
	 * | 6,0 | 6,1 | 6,2 | 6,3 | 6,4 | 6,5 | 6,6 | 6,7 | 6,8 | 6,9 | 6,10 | 6,11 |
	 * ---------------------------------------------------------------------------
	 * | 7,0 | 7,1 | 7,2 | 7,3 | 7,4 | 7,5 | 7,6 | 7,7 | 7,8 | 7,9 | 7,10 | 7,11 |
	 * ---------------------------------------------------------------------------
	 * | 8,0 | 8,1 | 8,2 | 8,3 | 8,4 | 8,5 | 8,6 | 8,7 | 8,8 | 8,9 | 8,10 | 8,11 |
	 * ---------------------------------------------------------------------------
	 * | 9,0 | 9,1 | 9,2 | 9,3 | 9,4 | 9,5 | 9,6 | 9,7 | 9,8 | 9,9 | 9,10 | 9,11 |
	 * ---------------------------------------------------------------------------
	 * |10,0 |10,1 |10,2 |10,3 |10,4 |10,5 |10,6 |10,7 |10,8 |10,9 |10,10 |10,11 |
	 * ---------------------------------------------------------------------------
	 * |11,0 |11,1 |11,2 |11,3 |11,4 |11,5 |11,6 |11,7 |11,8 |11,9 |11,10 |11,11 |
	 * ---------------------------------------------------------------------------
	 * 
	 */


	/*Attributs*/

	/**
	 * Jeu
	 */
	private Jeu jeu;

	/**
	 * Nombre de joueurs dans la partie
	 */
	private int nbJoueurs;

	/**
	 * Plateau
	 */
	private Plateau platTerrains;

	/**
	 * Liste des chemins possibles
	 */
	private HashSet<Terrain[]> chemins;
	
	/**
	 * Booléen indiquant si un moussaillon utilise une carte Perroquet
	 */
	private boolean perroquetUsed;
	
	/**
	 * Booléen indiquant si un moussaillon utilise une carte Cocotier
	 */
	private boolean cocotierUsed;
	
	/**
	 * Booléen indiquant si un moussaillon abandonne son tresor
	 */
	private boolean tresorAbandonned;
	
	/**
	 * Booléen indiquant si un personnage a gagné
	 */
	private boolean victoire = false;

	/**
	 * Booléen indiquant si le dé a été lancé pendant ce tour
	 */
	private boolean deLance = false;		

	//Interface

	/**
	 * Panneau d'informations (à gauche)
	 */
	private JPanel infoPanel;
	/**
	 * JLabel indiquant le joueur courant
	 */
	private JLabel joueurCrnt;
	/**
	 * Chaîne indiquant le personnage courant
	 */
	private String strJoueurCrnt;
	/**
	 * Personnage courant
	 */
	private Personnage cPers;

	/*Atouts*/
	/**
	 * JPanel où sont affichés les atouts et trésors du moussaillon
	 */
	private JPanel atouts;
	/**
	 * Card layout du panel d'affichage des atouts et trésors
	 */
	private CardLayout clAtouts;
	/**
	 * String servant à séléctionner la carte pAtoutsMouss
	 */
	private String atoutsMouss = "carte atouts moussaillons";
	/**
	 * String servant à séléctionner la carte pAtoutsAutre
	 */
	private String atoutsAutre = "carte atouts autres";
	/**
	 * JLabel décrivant les atouts et trésors des moussaillons
	 */
	private JLabel pAtoutsMouss;
	/**
	 * JLabel décrivant les atouts des autres personnages
	 */
	private JLabel pAtoutsAutre;
	/**
	 * Chaîne de caractères décrivant les atouts des moussaillons
	 */
	private String strAtoutsMouss;

	/**
	 * JLabel indiquant le score au dé
	 */
	private JLabel pScoreDe;
	/**
	 * JString décrivant le score au dé
	 */
	private String strScoreDe;


	/**
	 * JPanel du plateau de jeu (au centre)
	 */
	private JPanel plateau;
	/**
	 * Tableau de boutons représentant le plateau de jeu
	 */
	private JButton[][] cases;


	/**
	 * Panneau de contrôle (à droite)
	 */
	private JPanel controlPanel;
	/**
	 * JPanel affichant les actions possibles
	 */
	private JPanel pActions;
	/**
	 * Checkbox indiquant l'utilisation d'une carte Perroquet
	 */
	private JCheckBox usePerroquet;
	/**
	 * Checkbox indiquant l'utilisation d'une carte Cocotier
	 */
	private JCheckBox useCocotier;
	/**
	 * Checkbox indiquant l'abandon du trésor
	 */
	private JCheckBox abandonTresor;
	/**
	 * Bouton de lancer du dé
	 */
	private JButton lancerDe;

	/*Images*/
	/**
	 * Icône des moussaillons
	 */
	Icon m1Icon = new ImageIcon("assets/imgM1.png");
	/**
	 * Icône du pirate
	 */
	Icon pirIcon = new ImageIcon("assets/imgPir.png");
	/**
	 * Icône du fantôme
	 */
	Icon fantIcon = new ImageIcon("assets/imgFant.png");
	/**
	 * Icône du trésor
	 */
	Icon tresIcon = new ImageIcon("assets/imgTres.png");



	/*Constructeur*/

	/**
	 * Construit une fenêtre de jeu
	 * @param platTerrains le plateau du jeu
	 * @param nbJ le nombre de joueurs de la partie
	 * @param jeu le jeu
	 */
	public GameGUI(Plateau platTerrains, int nbJ, Jeu jeu){
		super("Big Pirate");
		this.init(platTerrains, nbJ, jeu);
		this.maj();
		this.setVisible(true);
	}

	/*Méthodes d'initialisation*/

	/**
	 * Initialisation de l'interface graphique
	 * @param platTerrains plateau du jeu
	 * @param nbJ nombre de joueurs de la partie
	 * @param jeu le jeu
	 */
	private void init(Plateau platTerrains, int nbJ, Jeu jeu){
		this.platTerrains = platTerrains;
		this.nbJoueurs = nbJ;
		this.jeu = jeu;
		//Fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		this.addWindowListener(l);
		//Taille
		this.setSize(1000,500);
		//Position au centre
		this.setLocationRelativeTo(null);
		//Choix du gestionnaire de mise en forme
		this.setLayout(new BorderLayout(5,5));
		//Création
		this.creerInfoPanel();
		//Création
		this.creerPlateauPanel();
		//Création
		this.creerControlPanel();
	}


	/**
	 * Création du panel d'affichage des informations
	 */
	private void creerInfoPanel(){
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(3, 1));
		this.creerInfoJoueur();
		this.creerInfoAtouts();
		this.creerInfoDe();
	}

	/**
	 * Création du label d'affichage du joueur courant
	 */
	private void creerInfoJoueur(){
		joueurCrnt = new JLabel();
		TitledBorder titleIJ;
		titleIJ = BorderFactory.createTitledBorder("Joueur");
		joueurCrnt.setBorder(titleIJ);
		infoPanel.add(joueurCrnt);
		this.getContentPane().add(infoPanel,BorderLayout.LINE_START);
	}

	/**
	 * Création du panel d'informations sur les atouts du joueur
	 */
	private void creerInfoAtouts(){
		pAtoutsMouss = new JLabel();		
		TitledBorder titleIA;
		titleIA = BorderFactory.createTitledBorder("Atouts et trésor");
		pAtoutsMouss.setBorder(titleIA);
		pAtoutsAutre = new JLabel();		//pour le pirate et le fantôme, reste vide car ils n'ont pas d'atouts
		clAtouts = new CardLayout();
		atouts = new JPanel(clAtouts);
		atouts.add(pAtoutsMouss, atoutsMouss);
		atouts.add(pAtoutsAutre, atoutsAutre);
		infoPanel.add(atouts);
	}

	/**
	 * Création du panel d'informations sur le résultat du dé
	 */
	private void creerInfoDe(){
		pScoreDe = new JLabel();
		TitledBorder titleID;
		titleID = BorderFactory.createTitledBorder("Score au dé");
		pScoreDe.setBorder(titleID);
		infoPanel.add(pScoreDe);
	}

	/**
	 * Création du panel d'affichage du plateau
	 */
	private void creerPlateauPanel(){
		plateau = new JPanel();
		plateau.setLayout(new GridLayout(12,12));
		this.initPlateau();
		//ajout du plateau à l'interface
		this.getContentPane().add(plateau,BorderLayout.CENTER);
	}

	/**
	 * Initialisation du plateau
	 */
	private void initPlateau(){
		//double boucle pour initialisation des boutons
		int i;
		int j;
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		cases = new JButton[12][12];
		for(i=0;i<12;i++){
			for(j=0;j<12;j++){
				cases[i][j] = new JButton();
				cases[i][j].setBorder(emptyBorder);
				drawTerrain((platTerrains.getPlateau())[i][j],i,j);
				cases[i][j].setEnabled(false);
				cases[i][j].addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int x = 0;
								int y = 0;
								//coordonnes du bouton appuyé
								for (int i = 0; i < 12; i++) {
									for (int j = 0; j < 12; j++) {
										if( cases[i][j] == e.getSource() ) { 
											x = i;
											y = j;
										}
									}
								}
								deplacePers(x,y);
							}
						});
				plateau.add(cases[i][j]);
			}
		}
	}

	/**
	 * Dessine un terrain sur le plateau
	 * @param terrain Le terrain à dessiner
	 * @param i L'abscisse du terrain à dessiner
	 * @param j L'ordonnée du terrain à dessiner
	 */
	private void drawTerrain(Terrain terrain, int i, int j){
		if(terrain instanceof Sentier){
			cases[i][j].setBackground(new Color(221,171,102));
		}
		if(terrain instanceof Eau){
			cases[i][j].setBackground(new Color(43,74,111));
		}
		if(terrain instanceof SCocotier){
			cases[i][j].setBackground(new Color(170,117,57));
		}
		if(terrain instanceof Cachette){
			cases[i][j].setBackground(new Color(30,90,20));
		}
		if(terrain instanceof Herbe){
			cases[i][j].setBackground(new Color(60,141,47));
		}
		if(terrain instanceof Grotte){
			cases[i][j].setBackground(new Color(47,28,6));
		}
		if(terrain instanceof Barque){
			cases[i][j].setBackground(new Color(108,57,18));
		}
	}

	/**
	 * Création du panel de contrôle
	 */
	private void creerControlPanel(){
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 1));
		this.creerControlActions();
		this.creerControlButtLancerDe();
		// Ajout à l'interface
		this.getContentPane().add(controlPanel,BorderLayout.LINE_END);
	}

	/**
	 * Création du panel affichant les actions possibles du moussaillon
	 */
	private void creerControlActions(){
		pActions = new JPanel();
		pActions.setLayout(new GridLayout(3,1));
		//initialisation des checkboxes
		usePerroquet = new JCheckBox("Utiliser une carte Perroquet");
		useCocotier = new JCheckBox("Utiliser une carte Cocotier");
		abandonTresor = new JCheckBox("Abandonner le trésor");
		//listener
		usePerroquet.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				perroquetUsed = usePerroquet.isSelected() ;
			}
		});
		useCocotier.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				cocotierUsed = useCocotier.isSelected();				
			}
		});
		abandonTresor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tresorAbandonned = abandonTresor.isSelected();
			}
		});
		pActions.add(usePerroquet);
		pActions.add(useCocotier);
		pActions.add(abandonTresor);
		TitledBorder titleCA;
		titleCA = BorderFactory.createTitledBorder("Atouts du moussaillon");
		pActions.setBorder(titleCA);
		controlPanel.add(pActions);
	}

	/**
	 * Création du bouton de lancer du dé
	 */
	private void creerControlButtLancerDe(){
		lancerDe = new JButton("Lancer le dé");
		controlPanel.add(lancerDe);
		//Event listener pour lancerDe
		lancerDe.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {lanceDe();}
				});
	}

	


	/*Méthodes d'actualisation*/

	/**
	 * Met à jour l'interface 
	 */
	private void maj(){
		this.cPers = jeu.getPersoCourant();
		this.majInfoPanel();
		this.majPlateauPanel();
		this.majControlPanel();
	}


	/**
	 * Met à jour le panel d'informations
	 */
	private void majInfoPanel(){
		this.majInfoJoueur();
		this.majInfoAtouts();
		this.majInfoDe();
	}

	/**
	 * Mise à jour des informations sur le joueur
	 */
	private void majInfoJoueur(){
		strJoueurCrnt = getStrPersC();
		joueurCrnt.setText(strJoueurCrnt);
	}

	/**
	 * Récupération d'un string correspondant au perso courant
	 * @return une chaîne de caractères correspondant au perso courant
	 */
	private String getStrPersC(){
		String res = cPers.toString();
		if(cPers instanceof Moussaillon){
			res = res +" "+ jeu.getNumMoussaillon((Moussaillon)cPers);
		}
		return res;		
	}

	/**
	 * Mise à jour des information sur les atouts
	 */
	private void majInfoAtouts(){
		if(cPers instanceof Moussaillon){
			strAtoutsMouss = infosMouss();
			pAtoutsMouss.setText("<HTML><P>"+strAtoutsMouss+"</P></MTML>");
			//on montre les infos sur les atouts du moussaillon
			clAtouts.show(atouts,atoutsMouss);
		}else{
			//on montre les infos des autres persos (vide)
			clAtouts.show(atouts,atoutsAutre);
		}
	}

	/**
	 * Récupération des informations sur les atouts du moussaillon
	 * @return une chaîne de caractères décrivant les atouts du moussaillon
	 */
	private String infosMouss(){
		int nbPerr = ((Moussaillon)cPers).getNbPerroquets();
		int nbCoco = ((Moussaillon)cPers).getNbCocotiers();
		String res = "Il reste "+nbPerr+" cartes Perroquet<BR> et "+nbCoco+" cartes Cocotier.";
		if(((Moussaillon)cPers).possedeTresor()){
			res = res+ "<BR> Vous avez un trésor !";
		}else{
			res = res+ "<BR> Vous n'avez pas de trésor.";
		}
		return res;
	}

	/**
	 * Mise à jour des informations sur le dé
	 */
	private void majInfoDe(){
		if(deLance){
			strScoreDe = infoScoreDe();
		}else{
			strScoreDe = "Le dé n'a pas été lancé.  ";
		}
		pScoreDe.setText(strScoreDe);
	}

	/**
	 * Récupération des infos sur le score au dé
	 * @return une chîne décrivant le score au dé
	 */
	private String infoScoreDe(){
		return "<HTML><P>"+cPers.toString()+" a fait un score<BR> de "+jeu.getScoreDeCourant()+" .</P></HTML>";
	}

	/**
	 * Met à jour le plateau
	 */
	private void majPlateauPanel(){
		this.drawPersos();
		
	}

	/**
	 * Dessine les personnages sur le plateau
	 */
	private void drawPersos(){
		int i;
		int j;
		for(i=0;i<12;i++){
			for(j=0;j<12;j++){
				if(platTerrains.getPlateau()[i][j] instanceof Sentier && ((Sentier)platTerrains.getPlateau()[i][j]).isTresor()){
					cases[i][j].setIcon(tresIcon);
					cases[i][j].setDisabledIcon(tresIcon);
				}else{
					cases[i][j].setIcon(null);
				}
				if(platTerrains.getPlateau()[i][j] == platTerrains.getCaseP()){
					cases[i][j].setIcon(pirIcon);
					cases[i][j].setDisabledIcon(pirIcon);
				}
				if(platTerrains.getPlateau()[i][j] == platTerrains.getCaseF()){
					cases[i][j].setIcon(fantIcon);
					cases[i][j].setDisabledIcon(fantIcon);
				}
				int n;
				for(n=0;n<nbJoueurs;n++){
					if(platTerrains.getPlateau()[i][j] == platTerrains.getCaseMoussaillon(n)){
						cases[i][j].setDisabledIcon(m1Icon);
						cases[i][j].setIcon(m1Icon);
					}
				}
			}
		}
	}
	
	
	/**
	 * Mise à jour du panel de contrôle
	 */
	private void majControlPanel(){
		this.majControlActions();
	}

	/**
	 * Mise à jour du panel des actions : désactive les actions impossibles
	 */
	private void majControlActions(){
		if(cPers instanceof Moussaillon){
			abandonTresor.setEnabled(jeu.peutAbandonnerTresor(jeu.getNumMoussaillon((Moussaillon) cPers)));
			usePerroquet.setEnabled(jeu.peutUtiliserPerroquet(jeu.getNumMoussaillon((Moussaillon) cPers)));
			useCocotier.setEnabled(jeu.peutUtiliserCocotier(jeu.getNumMoussaillon((Moussaillon) cPers)));
		}else{
			abandonTresor.setEnabled(false);
			usePerroquet.setEnabled(false);
			useCocotier.setEnabled(false);
		}
	}

	/*Méthodes générales*/
	/**
	 * Affiche les chemins possibles et active les boutons correspondants pour permettre
	 * au joueur de les sélectionner
	 */
	private void proposerChemins(){
		Iterator<Terrain[]> it = chemins.iterator();
		while (it.hasNext()) {
			//coordonnées dernière case du chemin
			Terrain t = (Terrain)(it.next())[0];
			int i = t.getX();
			int j = t.getY();
			//activation du bouton correspondant
			cases[i][j].setEnabled(true);
			//bordure
			cases[i][j].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(170, 57, 57)));
		}
	}

	/*Methodes appelées par les Action listeners*/

	/**
	 * Déplace le personnage, vérifie si le joueur à gagné, demande le cas échéant
	 * au moussaillon s'il veut se cacher
	 * @param x l'abscisse de la case où le joueur se déplace
	 * @param y l'ordonnée de la case où le joueur se déplace
	 */
	private void deplacePers(int x, int y){
		int n;
		//recherche du chemin associé au bouton appuyé
		boolean trouve=false;
		Terrain[] tabTerr = null; 
		Iterator<Terrain[]> it = chemins.iterator();
		while(it.hasNext() && !trouve){
			tabTerr = it.next();
			trouve = (tabTerr[0] == platTerrains.getCase(x, y));
		}
		//recuperer le bon n
		if(cPers instanceof Moussaillon){
			n = jeu.getNumMoussaillon((Moussaillon)cPers);
		}else{
			n = 0;
		}
		//appeler jeu.deplacement()
		victoire = jeu.deplacement(cPers, n, tabTerr);
		int i;
		int j;
		for(i=0;i<12;i++){
			for(j=0;j<12;j++){
				//desactiver boutons
				cases[i][j].setEnabled(false);
				//bordure
				cases[i][j].setBorder(BorderFactory.createEmptyBorder());
			}
		}
		if(victoire){
			JOptionPane.showMessageDialog(plateau,cPers+" a gagné !","Victoire !",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		}
		if(cPers instanceof Moussaillon){
			if(platTerrains.getCaseMoussaillon(n) instanceof SCocotier && jeu.peutUtiliserCocotier(n)){
				//proposer de se cacher
				int chx = JOptionPane.showConfirmDialog(
					    plateau,
					    "Voulez-vous utiliser une carte\n"
							    + "cocotier pour vous cacher ? \n",
					    "se cacher ?",
					    JOptionPane.YES_NO_OPTION);
				if(chx == JOptionPane.YES_OPTION){
					jeu.utiliserCocotier(n);
				}
			}
		}
		jeu.tourSuivant();
		deLance = false;
		this.cPers = jeu.getPersoCourant();
		this.maj();
	}

	/**
	 * Lance le dé, propose le déplacement aux moussaillon et au pirate,
	 * déplace le fantône
	 */
	private void lanceDe(){
		deLance = true;
		int n = 0;
		if(cPers instanceof Moussaillon){
			n = jeu.getNumMoussaillon((Moussaillon)cPers);
		}else{
			perroquetUsed = false;
		}	
		chemins = jeu.lancerDe(cPers, n, perroquetUsed);
		this.majInfoDe();
		if(cPers instanceof Fantome){
			//choix direct du chemin et deplacement
			Terrain[] chF = jeu.choisirParcoursFantome(chemins);
			System.out.println("appel deplacement fantome");
			jeu.deplacement(cPers, 0, chF);
			System.out.println("appel tour suivant");
			jeu.tourSuivant();
			deLance = false;
			this.cPers = jeu.getPersoCourant();
			this.maj();
		}else if(cPers instanceof Moussaillon){
			if(cocotierUsed){
				jeu.utiliserCocotier(n);
				jeu.tourSuivant();
			}else if(tresorAbandonned){
				jeu.abandonnerTresor(n);
				this.proposerChemins();
			}else{
				this.proposerChemins();
			}
		}else{
			this.proposerChemins();
		}
	}


	/**
	 * Mise à jour de l'interface quand le modèle est modifié
	 */
	public void update(Observable o) {
		this.maj();
	}

}