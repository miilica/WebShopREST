package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Poruka {

	private String naziv_oglasa;
	private String posiljalac;
	private String naslov;
	private String sadrzaj;
	private long datum;
	private boolean aktivna;
	
	private String dat;


	public Poruka() {
		super();
		this.aktivna=true;
		this.setDatum(java.lang.System.currentTimeMillis());
		System.out.println("poruka prazam konstruktor");
		 String pattern = "MM/dd/yyyy hh:mm";

			DateFormat df = new SimpleDateFormat(pattern);
			String prviDat1 = df.format(datum);
			setDat(prviDat1);

	}

	public String getNaziv_oglasa() {
		return naziv_oglasa;
	}


	public void setNaziv_oglasa(String naziv_oglasa) {
		this.naziv_oglasa = naziv_oglasa;
	}


	public String getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
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


	public long getDatum() {
		return datum;
	}


	public void setDatum(long datum) {
		this.datum = datum;
	}


	public boolean isAktivna() {
		return aktivna;
	}


	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}
	
	
	public String getDat() {
		return dat;
	}


	public void setDat(String dat) {
		this.dat = dat;
	}

	
	
	
	

}
