package beans;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User{

	private String korisnicko;
	private String password;
	private String ime;
	private String prezime;
	private String uloga;
	 private String telefon;
	 private String mail;
	 private String datum;
	private String grad;
	 private ArrayList<Poruka> poruke = new ArrayList<Poruka>();
	

	public User() {
		
	}

	public User(String korisnicko, String password, String ime, String prezime,String telefon, String grad,
			 String mail, ArrayList<Poruka> poruke) {
		
		this.korisnicko = korisnicko;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.uloga = "Kupac";
		this.telefon = telefon;
		this.grad = grad;
		this.mail=mail;
		this.datum = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		this.poruke = poruke;
	
	}
	
	

	public User(String korisnicko, String password, String ime, String prezime, String uloga, String telefon,
			String mail, String datum, String grad, ArrayList<Poruka> poruke) {
		super();
		this.korisnicko = korisnicko;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.uloga = uloga;
		this.telefon = telefon;
		this.mail = mail;
		this.datum = datum;
		this.grad = grad;
		this.poruke = poruke;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}


	public String getGrad() {
		return grad;
	}

	

	public void setGrad(String grad) {
		this.grad = grad;
	}



	

	public String getKorisnicko() {
		return korisnicko;
	}

	public void setKorisnicko(String korisnicko) {
		this.korisnicko = korisnicko;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


		public ArrayList<Poruka> getPoruke() {
			return poruke;
		}

		public void setPoruke(ArrayList<Poruka> poruke) {
			this.poruke = poruke;
		}

	


}
