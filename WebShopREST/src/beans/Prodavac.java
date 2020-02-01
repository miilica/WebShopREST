package beans;

import java.util.ArrayList;

public class Prodavac extends User {
	 private ArrayList<String> objavljeni = new ArrayList<String>();
	 private ArrayList<String> isporuceni = new ArrayList<String>();
	private int brLajk = 0;
	 private int brDisLajk = 0;
	 private int prijave = 0;
	 
	public Prodavac() {
		this.setUloga("Prodavac");
		this.brLajk = 0;
		this.brDisLajk = 0;
		this.prijave=0;
	}
	
	public Prodavac(User u){
		super(u.getKorisnicko(),u.getPassword(),u.getIme(),u.getPrezime(),u.getUloga(),u.getTelefon(),
				u.getMail(),u.getDatum(),u.getGrad(),u.getPoruke());
		this.setUloga("Prodavac");
	}


	


	public ArrayList<String> getObjavljeni() {
		return objavljeni;
	}





	public void setObjavljeni(ArrayList<String> objavljeni) {
		this.objavljeni = objavljeni;
	}





	public ArrayList<String> getIsporuceni() {
		return isporuceni;
	}





	public void setIsporuceni(ArrayList<String> isporuceni) {
		this.isporuceni = isporuceni;
	}





	public int getBrLajk() {
		return brLajk;
	}

	public void setBrLajk(int brLajk) {
		this.brLajk = brLajk;
	}

	public int getBrDisLajk() {
		return brDisLajk;
	}

	public void setBrDisLajk(int brDisLajk) {
		this.brDisLajk = brDisLajk;
	}

	public int getPrijave() {
		return prijave;
	}

	public void setPrijave(int prijave) {
		this.prijave = prijave;
	}
	
	
	
	
	 
}
