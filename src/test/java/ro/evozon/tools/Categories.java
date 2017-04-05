package ro.evozon.tools;

public enum Categories {

	DENTA(0), FRUMUSETE(1), MEDICINA(2), ANIMALE_DE_COMPANIE(3), SPORT_SI_AGREMENT(
			4), TERAPII_COMPLEMENTARE(5), AUTO(6), INSTALATII(7), JURIDIC(8), RESURSE_UMANE(
			9), ASIGURARI(10), MODA_SI_IMBRACAMINTE(11), ORGANIZARE_EVENIMENTE(
			12), EDUCATIE(13), IMOBILIARE(14), REPARATII(15), PSIHOLOGIE(16);
	private int value;

	Categories(final int value) {
		this.value = value;

	}

	public int getOption() {
		return value;
	}
}
