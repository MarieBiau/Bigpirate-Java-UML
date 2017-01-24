package personnages;

import java.util.*;

import obs.Observable;
import obs.Observer;
import ramassables.*;

/**
 * La classe Moussaillon représente les personnages des moussaillons
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class Moussaillon extends Personnage implements Observable {

	//Attributs

	/**
	 * Nombre de moussaillons de la partie
	 */
	private static int nombreM;

	/**
	 * Booléen modélisant le fait que le moussaillon soit en vie ou non
	 * True signifie qu'il est en vie et False qu'il a été tué
	 */
	private boolean enJeu;

	/**
	 * Collection des trésors appartenant au moussaillon
	 */
	private HashSet<Ramassable> tresor;

	/**
	 * Collection des cartes perroquet appartenant au moussaillon
	 */
	private HashSet<Ramassable> perroquets;

	/**
	 * Collection des cartes cocotier appartenant au moussaillon
	 */
	private HashSet<Ramassable> cocotiers;

	/**
	 * Collection d'observeurs à mettre à jour lors de la modification du moussaillon
	 */
	private Set<Observer> observers = new HashSet<Observer>();


	//Constructeur

	/**
	 * Constructeur
	 * Le moussaillon est en jeu
	 * Il possède une collection de trésors, une collection de cartes perroquet ainsi qu'une collection de cartes cocotier
	 * @param nombre est le nombre de moussaillons dans la partie
	 * @param barque est la case de départ du moussaillon
	 */
	public Moussaillon(int nombre) {
		nombreM=nombre;
		this.enJeu=true;
		this.tresor=new HashSet<Ramassable>();
		this.perroquets = new HashSet<Ramassable>();
		this.cocotiers = new HashSet<Ramassable>();
		int i;
		for (i=0;i<6-nombreM;i++) {
			this.perroquets.add(new Perroquet());
		}
		for (i=0;i<4-nombreM;i++) {
			this.cocotiers.add(new Cocotier());
		}
	}


	//Méthodes
	
	/**
	 * Méthode toString
	 */
	public String toString() {
		return "Moussaillon";
	}

	/**
	 * Permet d'obtenir le nombre de moussaillons de la partie
	 * @return le nombre de moussaillons de la partie
	 */
	public static int getNombreM() {
		return nombreM;
	}
	
	/**
	 * Permet d'obtenir la collection de trésors du moussaillon
	 * @return un HashSet de ramassables contenant la collection de trésors
	 */
	public HashSet<Ramassable> getTresor() {
		return tresor;
	}

	/**
	 * Permet d'obtenir la collection de perroquets du moussaillon
	 * @return un HashSet de ramassables contenant la collection de perroquets
	 */
	public HashSet<Ramassable> getPerroquets() {
		return perroquets;
	}

	/**
	 * Permet d'obtenir la collection de cocotiers du moussaillon
	 * @return un HashSet de ramassables contenant la collection de cocotiers
	 */
	public HashSet<Ramassable> getCocotiers() {
		return cocotiers;
	}

	/**
	 * Permet de savoir si un moussaillon est en jeu
	 * @return un booléen valant True si le moussaillon est en jeu et False sinon
	 */
	public boolean isEnJeu() {
		return enJeu;
	}

	/**
	 * Permet de savoir si le moussaillon possède un ou des atouts perroquet
	 * @return un boolean valant True s'il possède au minimum un perroquet
	 */
	public boolean possedePerroquet() {
		return !(perroquets.isEmpty());
	}
	
	/**
	 * Pernet de connaître le nombre de cartes perroquet possédées par le moussaillon
	 * @return le nombre de cartes perroquet du moussaillon
	 */
	public int getNbPerroquets(){
			return perroquets.size();
	}

	/**
	 * Permet de savoir si le moussaillon possède un ou des atouts cocotier
	 * @return un boolean valant True s'il possède au minimum un cocotier
	 */
	public boolean possedeCocotier() {
		return !(cocotiers.isEmpty());
	}

	/**
	 * Pernet de connaître le nombre de cartes cocotier possédées par le moussaillon
	 * @return le nombre de cartes cocotier du moussaillon
	 */
	public int getNbCocotiers(){
			return cocotiers.size();
	}
	
	/**
	 * Permet de savoir si le moussaillon possède un trésor
	 * @return un boolean valant True s'il possède  un trésor
	 */
	public boolean possedeTresor() {
		return !(tresor.isEmpty());
	}
	
	/**
	 * Permet d'utiliser un atout perroquet
	 */
	public void utilisePerroquet() {
		if (!this.perroquets.isEmpty()) {
			Iterator<Ramassable> i = perroquets.iterator();
			i.next();
			i.remove();
		}
	}

	/**
	 * Permet d'utiliser un atout cocotier
	 */
	public void utiliseCocotier() {
		if (!this.cocotiers.isEmpty()) {
			Iterator<Ramassable> i = cocotiers.iterator();
			i.next();
			i.remove();
		}
	}

	/**
	 * Permet d'abandonner ou de perdre un trésor
	 * @return s'il possède un trésor, l'instance du trésor et sinon null
	 */
	public HashSet<Ramassable> abandonnerTresor() {
		if (!this.tresor.isEmpty()) {
			HashSet<Ramassable> tresorAbandon = new HashSet<Ramassable>();
			for (Ramassable t: tresor){
				tresorAbandon.add(t);
			}
			this.tresor.clear();
			return tresorAbandon;
		}
		return null;
	}

	/**
	 * Permet de récupérer un trésor	
	 * @param tresorRecupere est le trésor qu'il récupère sur un Terrain
	 */
	public void recupererTresor(HashSet<Ramassable> ramassableRecupere) {
		for (Ramassable r:ramassableRecupere){
			if (r instanceof Tresor) {
				this.tresor.add((Tresor)r);
			}
		}
	}
	
	/**
	 * Permet d'éliminer un joueur du jeu
	 */
	public void eliminer() {
		this.enJeu=false;
		nombreM--;
	}

	/**
	 * Ajoute un observeur dans la collection d'observeurs
	 * @param o l'observeur à ajouter
	 */
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Supprime un observeur dans la collection d'observeurs
	 * @param o l'observeur à supprimer
	 */
	public void removeObserver(Observer o) {
		observers.remove(o);	
	}

	/**
	 * Notifie les observeurs d'une mise à jour
	 */
	public void notifyObservers(){
	}

	

	
	//Main
	
	public static void main(String[] args) {
		
		/* Tests de la création et de l'utilisation des méthodes d'un moussaillon */
		
		Moussaillon m = new Moussaillon(3);
		System.out.println(Moussaillon.getNombreM());
		System.out.println(m.isEnJeu());
		System.out.println(m.getTresor());
		System.out.println(m.possedeTresor());
		System.out.println(m.getPerroquets());
		System.out.println(m.possedePerroquet());
		System.out.println(m.getNbPerroquets());
		System.out.println(m.getCocotiers());
		System.out.println(m.possedeCocotier());
		System.out.println(m.getNbCocotiers());
		System.out.println("");
		
		m.utilisePerroquet();
		m.utiliseCocotier();
		System.out.println(m.getPerroquets());
		System.out.println(m.possedePerroquet());	
		System.out.println(m.getCocotiers());
		System.out.println(m.possedeCocotier());
		System.out.println("");
		
		HashSet<Ramassable> t1 = new HashSet<Ramassable>();
		HashSet<Ramassable> t2 = new HashSet<Ramassable>();;
		Tresor tres = new Tresor();
		t1.add(tres);
		m.recupererTresor(t1);
		System.out.println(m.possedeTresor());
		System.out.println(m.getTresor());
		t2=m.abandonnerTresor();
		System.out.println(m.possedeTresor());
		System.out.println(m.getTresor());
		System.out.println(t2);
		m.eliminer();
		System.out.println(m.isEnJeu());
		
	}

}
