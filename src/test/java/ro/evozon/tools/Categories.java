package ro.evozon.tools;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum Categories {

	MEDICINA_DENTARA(7), FRUMUSETE(1), MEDICINA(4), ANIMALE_DE_COMPANIE(2), SPORT_SI_AGREMENT(3), TERAPII_COMPLEMENTARE(
			127), AUTO(91), INSTALATII(93), JURIDIC(95), RESURSE_UMANE(97), ASIGURARI(99), MODA_SI_IMBRACAMINTE(
					101), ORGANIZARE_EVENIMENTE(103), EDUCATIE(105), IMOBILIARE(107), REPARATII(109), PSIHOLOGIE(125),ALTA_CATEGORIE(9);
	private int value;

	Categories(final int value) {
		this.value = value;

	}
	//****** Reverse Lookup Implementation************//

	//Lookup table
	private static final Map<String, Integer> lookup = new HashMap<>();

	//Populate the lookup table on loading time
	static
	{
		for(Categories category : Categories.values())
		{
			lookup.put(category.name(), category.getOption());
		}
	}

	//This method can be used for reverse lookup purpose
	public static Integer get(String categoryName)
	{
		return lookup.get(categoryName);
	}
	public int getOption() {
		return value;
	}

	private static final Categories[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	public static Categories getRandomCategory() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
