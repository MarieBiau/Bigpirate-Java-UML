package gui;

/**
 * Interface graphique du launcher
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

import javax.swing.* ;
import javax.swing.border.* ;
import java.awt.* ;
import java.awt.event.* ;
import jeu.* ;




public class LauncherGUI extends JFrame{
	
	/**
	 * Interface graphique du launcher
	 */
	private static final long serialVersionUID = 1L;
	
	/*  ---------------
	 *  |     c1      |
	 *  ---------------
	 *  |     c2      |
	 *  ---------------
	 *  |     c3      |
	 *  ---------------
	 *  |     c4      |
	 *  --------------- 
	 */

	/*TODO : bannière*/
	//banniere
	private JLabel c1;
	
	//Texte explication
	private JLabel c2;
	
	//Choix nombre joueurs
	private JComboBox<Integer> c3;
	
	//Bouton lancer partie
	private JButton c4;
	
	//Jeu
	private Jeu jeu;
	

	/*Constructeur de fenetre de type launcher*/
	public LauncherGUI(){
		
		super("Big Pirate");
		
		this.jeu = new Jeu();
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/*Taille*/
		this.setSize(400,600);
		
		/*Position au centre*/
		this.setLocationRelativeTo(null);
		
		
		/*Choix du gestionnaire de mise en forme*/
		this.setLayout(new GridLayout(4,1));
		
		/*Bannière*/
		c1 = new JLabel ("BIG PIRATE",JLabel.CENTER);
		this.getContentPane().add(c1);
		
		/*Texte explication*/
		c2= new JLabel ("<html><p> Le jeu BIG PIRATE se joue de 2 à 4 joueurs. Un des joueurs joue le pirate, les autres des moussaillons</p> </html>");
		this.getContentPane().add(c2);
		
		/*Liste nb joueurs*/
		Integer[] nbJoueurs = {2,3,4};
		c3 = new JComboBox<Integer>(nbJoueurs);
		c3.setSelectedIndex(0);
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Choisir le nombre de joueurs");
		c3.setBorder(title);
		this.getContentPane().add(c3);
		
		/*Bouton validation*/
		c4 = new JButton("Lancer la partie");
		this.getContentPane().add(c4);
		
		/*Event listener pour c4 (lancer la partie)*/
		c4.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {lancerJeu();}
				});
		
	    /*Affichage du cadre*/
		this.setVisible(true);
		
	}
	
	/*Methode appelée si clic sur c4 (lancer partie)*/
	private void lancerJeu(){
		Integer nbJoueurs = (Integer) this.c3.getSelectedItem();
		jeu.lancerPartie(nbJoueurs-1);		
		this.dispose();
	}
	

}
