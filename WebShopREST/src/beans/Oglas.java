package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Oglas {
	private String naziv;
	private double cijena; 
	private String opis;
	private int brLajkova;
	private int brDislajkova;
	
	private long datumPostavljanja;
	private Date datumIsticanja;
	private String grad;
	private ArrayList<Recenzija> listaRecenzija = new ArrayList<Recenzija>(); 
	private boolean daLiJeAktivam;
	private String status;
	
	
	private long datIsticanja;
	private String imgSlika;
	private String vlasnik;
	
	private Date prvi;
	private String prviDat;
	private String drugiDat;
	
	private ArrayList<String> uKategorijama = new ArrayList<String>();
	
   private int popularnost;
	
	

	


	public Oglas() {
		this.brLajkova=0;
		this.brDislajkova=0;
		this.setDatumPostavljanja(java.lang.System.currentTimeMillis());
		this.daLiJeAktivam= true;
		this.status= "aktivan";
		this.listaRecenzija = new ArrayList<Recenzija>();
		
		this.setPopularnost(0);
		this.prvi = new Date(datumPostavljanja);
		 String pattern = "MM/dd/yyyy";

		DateFormat df = new SimpleDateFormat(pattern);
		String prviDat1 = df.format(prvi);
		setPrviDat(prviDat1);

		System.out.println(this.getDatumIsticanja());

		 
	}
	
	public String getNaziv() {
		return naziv;
	}

	public long getDatIsticanja() {
		return datIsticanja;
	}

	public void setDatIsticanja(long datIsticanja) {
		this.datIsticanja = datIsticanja;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getBrLajkova() {
		return brLajkova;
	}

	public void setBrLajkova(int brLajkova) {
		this.brLajkova = brLajkova;
	}

	public int getBrDislajkova() {
		return brDislajkova;
	}

	public void setBrDislajkova(int brDislajkova) {
		this.brDislajkova = brDislajkova;
	}

	

	public long getDatumPostavljanja() {
		return datumPostavljanja;
	}

	public void setDatumPostavljanja(long datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}

	public Date getDatumIsticanja() {
		return datumIsticanja;
	}

	public void setDatumIsticanja(Date datumIsticanja) {
		this.datumIsticanja = datumIsticanja;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public ArrayList<Recenzija> getListaRecenzija() {
		return listaRecenzija;
	}

	public void setListaRecenzija(ArrayList<Recenzija> listaRecenzija) {
		this.listaRecenzija = listaRecenzija;
	}

	public boolean isDaLiJeAktivam() {
		return daLiJeAktivam;
	}

	public void setDaLiJeAktivam(boolean daLiJeAktivam) {
		this.daLiJeAktivam = daLiJeAktivam;
	}

	public String getImgSlika() {
		return imgSlika;
	}

	public void setImgSlika(String imgSlika) {
		this.imgSlika = imgSlika;
	}

	public String getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(String vlasnik) {
		this.vlasnik = vlasnik;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getPrviDat() {
		return prviDat;
	}


	public void setPrviDat(String prviDat) {
		this.prviDat = prviDat;
	}
	
	public String getDrugiDat() {
		return drugiDat;
	}


	public void setDrugiDat(String drugiDat) {
		this.drugiDat = drugiDat;
	}


	public ArrayList<String> getuKategorijama() {
		return uKategorijama;
	}


	public void setuKategorijama(ArrayList<String> uKategorijama) {
		this.uKategorijama = uKategorijama;
	}

	public int getPopularnost() {
		return popularnost;
	}

	public void setPopularnost(int popularnost) {
		this.popularnost = popularnost;
	}

	
	
	
	
	
}
