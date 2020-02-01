package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.KlasaLogovanje;
import beans.Poruka;
import beans.User;
import dao.UserDAO;
import beans.Admin;
import beans.Prodavac;
import beans.Kupac;

@Path("/korisnici")
public class UserService {

	@Context
	ServletContext ctx;
	private UserDAO korDao = new UserDAO();



	public UserService() {
		
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
	    	korDao = new UserDAO(contextPath);
	    	System.out.println(contextPath+"ovo je putanja");
			ctx.setAttribute("userDAO", korDao);
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getKorisnike() {
		korDao = (UserDAO)ctx.getAttribute("userDAO");

		ArrayList<User> sviKor = new ArrayList<User>();
		
		for (User k : korDao.getUsers().values()) {
				sviKor.add(k);
				System.out.println("get kor "+k.getKorisnicko());
			}
		
		
		return sviKor;
	
	}
	
	@GET
	@Path("/pretraziKorisnike")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> korisniciPretraga(@QueryParam("ime") String ime, @QueryParam("grad") String grad) {
		korDao = (UserDAO)ctx.getAttribute("userDAO");
		
		ArrayList<User> povratna = new ArrayList<User>();
		for(User u :korDao.getUsers().values()) {
			povratna.add(u);
		}
		System.out.println("USAO U PRETRAGU");
		boolean vrsiSeIme = false;
			if(ime != null  && ime.equals("")==false && ime.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po nazivu "+ ime);
			
			for(User u : korDao.getUsers().values()) {
				if(u.getKorisnicko().equalsIgnoreCase(ime) == false) {
					if(povratna.contains(u)) {
						povratna.remove(u);
						
					}
				}
			}
			vrsiSeIme = true;
			
		}
			boolean vrsiSeGrad = false;
			
		if(grad != null && grad.equals("")==false && grad.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po gradu "+ grad);
			
			for(User u : korDao.getUsers().values()) {
				
				if(u.getGrad().equalsIgnoreCase(grad) == false) {
					if(povratna.contains(u)) {
						povratna.remove(u);
						
					}
				}
			}
			 vrsiSeGrad = true;
			
		}
	
		if(vrsiSeIme == false && vrsiSeGrad == false){
			return null;
			
		}
		
		for(User u : povratna) {
			System.out.println("povr "+u.getKorisnicko());
		}
		return povratna;
	}
	
	@GET
	@Path("/getGradove")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getGradove() {
	korDao = (UserDAO)ctx.getAttribute("userDAO");
	ArrayList<String> x = new ArrayList<String>();
	
	for (User o : korDao.getUsers().values()) {
		x.add(o.getGrad());
	}	
	return x;

}
	
	@GET
	@Path("/getadmine")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAdmine() {
		korDao = (UserDAO)ctx.getAttribute("userDAO");
		ArrayList<User> kor = new ArrayList<User>();
		for(User u : korDao.getUsers().values()) {
			if(u.getUloga().equals("Admininstrator")) {
				kor.add(u);
				System.out.println("get "+u.getKorisnicko());
			}
		}
		return kor;
	
	}



	@POST
	@Path("/reg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registrujKorisnika(User user) {
		System.out.println("regiiiiiiiiii");
	 	korDao = (UserDAO) ctx.getAttribute("userDAO");
		String contextPath = ctx.getRealPath("");
	 	user.setUloga("Kupac");
		int postojeci = -1;
		User povratna = new User();
			
		for(User korisnik :korDao.getUsers().values()) {
			if(korisnik.getKorisnicko().equals(user.getKorisnicko())) {	
				postojeci = 1;
				povratna.setPrezime("ime");
				
				break;
			}
		}		
		
		for(User korisnik :korDao.getUsers().values()) {
			if(korisnik.getMail().equals(user.getMail())) {	
				postojeci = 2;
				povratna.setPrezime("adresa");
				
				break;
			}
		}	
		int broj=0;
		
		String telefon = user.getTelefon();
		if(telefon.equals("") || telefon == null || telefon.equals("undefined")) {
			povratna.setPrezime("telefon");
			postojeci = 3;
		}else {
			
			 try
			    {
			       broj = Integer.parseInt(telefon);
			    }
			    catch (NumberFormatException nfe)
			    {
			    	povratna.setPrezime("telefon");
					postojeci = 3;
				      System.out.println("NumberFormatException: " + nfe.getMessage());
						
					return povratna;
			    }
			
			
		}
		
		if(broj <1) {
	    	povratna.setPrezime("telefon");
			postojeci = 3;
	
			
		}
		if(postojeci == -1) { //nije pronasao korisnika sa istiim nazivom
		   System.out.println("UBACEN KORISNIK.");
		   Kupac k = new Kupac(user);
		    korDao.save(contextPath, k);
		    System.out.println(k.getKorisnicko()+k.getPassword());
		
			ctx.setAttribute("userDAO", korDao);
			return k;
		}else {
			return povratna;
		}	
	}
	
	@POST
	@Path("/uloguj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(KlasaLogovanje korisnik,@Context HttpServletRequest request) {
		korDao = (UserDAO) ctx.getAttribute("userDAO");
	
	//System.out.println("DOSAO DA SE ULOGUJE");
	//System.out.println("Ime korisnika je " + korisnik.getUsername() + " sifra je "+korisnik.getPassword());
	if(korDao.find(korisnik.getKorisnicko(), korisnik.getLozinka()) != null) {
		
		System.out.println("Korisnik je " + korDao.find(korisnik.getKorisnicko(), korisnik.getLozinka()).getKorisnicko());
	
		request.getSession().setAttribute("user",korDao.find(korisnik.getKorisnicko(), korisnik.getLozinka()));
		return korDao.find(korisnik.getKorisnicko(), korisnik.getLozinka());
	}else {
		return null;
	}
	
	}
	
	@GET
	@Path("/izloguj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User logout(@Context HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");		
		//Poništava sesiju i oslobaða objekte koji su bili vezani za nju
		request.getSession().invalidate();
	return user;
	}
	
	

	@PUT
	@Path("/izmjeni/{username}/{tip}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response promjeni(@PathParam("username") String username, @PathParam("tip") String tip,
			 @Context HttpServletRequest request){
		korDao = (UserDAO) ctx.getAttribute("userDAO");

		User k = korDao.findU(username);
		if(tip.equals("Admininstrator")){
			Admin admin = new Admin(k);
			admin.setUloga("Admininstrator");
			korDao.getUsers().remove(username);
			korDao.getUsers().put(username, admin);
		}
		else if(tip.equals("Prodavac")){
			Prodavac s = new Prodavac(k);
			s.setUloga("Prodavac");
			korDao.getUsers().remove(username);
			korDao.getUsers().put(username, s);
		}
		else{
			Kupac c = new Kupac(k);
			c.setUloga("Kupac");
			korDao.getUsers().remove(username);
			korDao.getUsers().put(username, c);
		}
		
		System.out.println("Promjenjena korisnika"+k.getUloga());
		
		korDao.saveUsers();
		
	 	return Response.status(200).build();
	}
	
	@GET
	@Path("/getMojePoruke/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Poruka> getMojePoruke(@PathParam("id") String id){
		korDao = (UserDAO)ctx.getAttribute("userDAO");
		User temp = korDao.findU(id);
		if(temp == null){
			System.out.println("Nema usera");
			return null;
			
		}
		System.out.println("get moje poruke "+temp.getKorisnicko()+" je user");
		
		
		ArrayList<Poruka> porukeList = new ArrayList<Poruka>();
		for(Poruka p : temp.getPoruke()){
				if(p!=null) {
				porukeList.add(p);
				System.out.println("Poruka vracena "+p.getNaslov());}
				else {
				System.out.println("get poruke null");
				}
			
		}
		return porukeList;
		
	}
	

}
