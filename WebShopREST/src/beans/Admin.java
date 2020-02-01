package beans;

public class Admin extends User {

	public Admin() {
		super();
		this.setUloga("Admininstrator");
	}
	
	public Admin(User u){
		super(u.getKorisnicko(),u.getPassword(),u.getIme(),u.getPrezime(),u.getUloga(),u.getTelefon(),
				u.getMail(),u.getDatum(),u.getGrad(),u.getPoruke());
		this.setUloga("Admininstrator");
	}

	
	
}
