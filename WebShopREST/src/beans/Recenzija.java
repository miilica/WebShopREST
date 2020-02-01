package beans;


public class Recenzija {
	private String oglas;
	private String naslov;
	private String sadrzaj;
	private String slika;
	private String daLiJeOpisTacan;
	private String daLiJeDogIspostovan;
	
	private String recenzent; //kupac,prijavljeni
	private boolean aktivan;
	
	public Recenzija() {
		super();
		this.aktivan=true;
		this.daLiJeOpisTacan=" ";
	}

	public String getOglas() {
		return oglas;
	}

	public void setOglas(String oglas) {
		this.oglas = oglas;
	}

	public String getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(String recenzent) {
		this.recenzent = recenzent;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public String getDaLiJeOpisTacan() {
		return daLiJeOpisTacan;
	}

	public void setDaLiJeOpisTacan(String daLiJeOpisTacan) {
		this.daLiJeOpisTacan = daLiJeOpisTacan;
	}

	public String getDaLiJeDogIspostovan() {
		return daLiJeDogIspostovan;
	}

	public void setDaLiJeDogIspostovan(String daLiJeDogIspostovan) {
		this.daLiJeDogIspostovan = daLiJeDogIspostovan;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	
	
	
	
}
