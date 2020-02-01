package beans;

import java.util.ArrayList;

public class Kupac extends User {
	 private ArrayList<String> omiljeni = new ArrayList<String>();
	 private ArrayList<String> poruceni = new ArrayList<String>();
	 private ArrayList<Oglas> dostavljeni = new ArrayList<Oglas>();
	 
	 private ArrayList<String> lajkovaniOglasi = new ArrayList<String>();
		private ArrayList<String> dislijovaniOglsi = new ArrayList<String>();
		private ArrayList<String> lajkProdavci = new ArrayList<String>();
		private ArrayList<String> dislikedProdavci = new ArrayList<String>();
		
		public Kupac() {
			this.setUloga("Kupac");
		}
		
		public Kupac(User u){
			super(u.getKorisnicko(),u.getPassword(),u.getIme(),u.getPrezime(),u.getUloga(),u.getTelefon(),
					u.getMail(),u.getDatum(),u.getGrad(),u.getPoruke());
			this.setUloga("Kupac");
		}

		public ArrayList<String> getOmiljeni() {
			return omiljeni;
		}

		public void setOmiljeni(ArrayList<String> omiljeni) {
			this.omiljeni = omiljeni;
		}

		public ArrayList<String> getPoruceni() {
			return poruceni;
		}

		public void setPoruceni(ArrayList<String> poruceni) {
			this.poruceni = poruceni;
		}

		public ArrayList<Oglas> getDostavljeni() {
			return dostavljeni;
		}

		public void setDostavljeni(ArrayList<Oglas> dostavljeni) {
			this.dostavljeni = dostavljeni;
		}

		public ArrayList<String> getLajkovaniOglasi() {
			return lajkovaniOglasi;
		}

		public void setLajkovaniOglasi(ArrayList<String> lajkovaniOglasi) {
			this.lajkovaniOglasi = lajkovaniOglasi;
		}

		public ArrayList<String> getDislijovaniOglsi() {
			return dislijovaniOglsi;
		}

		public void setDislijovaniOglsi(ArrayList<String> dislijovaniOglsi) {
			this.dislijovaniOglsi = dislijovaniOglsi;
		}

		public ArrayList<String> getLajkProdavci() {
			return lajkProdavci;
		}

		public void setLajkProdavci(ArrayList<String> lajkProdavci) {
			this.lajkProdavci = lajkProdavci;
		}

		public ArrayList<String> getDislikedProdavci() {
			return dislikedProdavci;
		}

		public void setDislikedProdavci(ArrayList<String> dislikedProdavci) {
			this.dislikedProdavci = dislikedProdavci;
		}
		
		
		
}
