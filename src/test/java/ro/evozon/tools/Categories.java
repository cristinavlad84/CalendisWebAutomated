package ro.evozon.tools;

import java.util.Random;

public enum Categories {

	MEDICINA_DENTARA(7), FRUMUSETE(1), MEDICINA(4), ANIMALE_DE_COMPANIE(2), SPORT_SI_AGREMENT(3), TERAPII_COMPLEMENTARE(
			127), AUTO(91), INSTALATII(93), JURIDIC(95), RESURSE_UMANE(97), ASIGURARI(99), MODA_SI_IMBRACAMINTE(
					101), ORGANIZARE_EVENIMENTE(103), EDUCATIE(105), IMOBILIARE(107), REPARATII(109), PSIHOLOGIE(125);
	private int value;

	Categories(final int value) {
		this.value = value;

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
