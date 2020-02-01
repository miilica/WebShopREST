package beans;

public class KlasaLogovanje {
	private String korisnicko;
	private String lozinka;
	
	public KlasaLogovanje() {
		super();
	}

	public KlasaLogovanje(String korisnicko, String lozinka) {
		super();
		this.korisnicko = korisnicko;
		this.lozinka = lozinka;
	}

	public String getKorisnicko() {
		return korisnicko;
	}

	public void setKorisnicko(String korisnicko) {
		this.korisnicko = korisnicko;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	
	
}
