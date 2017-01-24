package des;

/**
 * 
 * @author Anne-Charline Baclet, Marie Biau, Christophe Thao Ky
 *
 */

public class De {
	
	/**
	 * Valeur maximale atteignable lors d'un lancer de dé (nombre de faces du dé)
	 */
	protected int rangeDe;
	
	//Constructeur
	
	/**
	 * Constructeur
	 * @param n le nombre de faces du dé
	 */
	public De (int n){
		this.rangeDe = n;
	}
	
	
	//Méthodes
	
	/**
	 * Lance un dé à rangeDe faces
	 * @return Le résultat du dé (1 à rangeDe)
	 */
	public int seLancer() {
		return (int)(Math.random()*rangeDe+1);
	}
	
	
	//Main
	
	public static void main(String[] args) {
		/* Tests de la création et du lancement d'un grand De */
		System.out.println("Création d'un grand dé");
		De d = new De(6);
		System.out.println("10 lancers de dés");
		int i = 0;
		for (i=1;i<11;i++){
			System.out.println("Résultat du " + i + "ème lancer = " + d.seLancer());
		}
	}
	
}
