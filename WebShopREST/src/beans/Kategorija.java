package beans;

import java.util.ArrayList;

public class Kategorija {
	private String naziv;
	private String  opis;
	ArrayList<String> listaOglasa;
	private boolean aktivna;
	private String ak;
	
	
	public Kategorija() {
		super();
		listaOglasa= new ArrayList<String>();
		this.aktivna = true;
		ak="Da";
	}


	public String getAk() {
		return ak;
	}


	public void setAk(String ak) {
		this.ak = ak;
	}


	public Kategorija(String naziv, String opis) {
		super();
		this.naziv = naziv;
		this.opis = opis;
		this.listaOglasa =new ArrayList<String>();
		aktivna = true;
		this.ak="Da";
	}


	public boolean isAktivna() {
		return aktivna;
	}


	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public ArrayList<String> getListaOglasa() {
		return listaOglasa;
	}


	public void setListaOglasa(ArrayList<String> listaOglasa) {
		this.listaOglasa = listaOglasa;
	}



}
