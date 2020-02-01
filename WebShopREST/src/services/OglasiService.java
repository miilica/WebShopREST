package services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import beans.Admin;
import beans.Kupac;
import beans.Oglas;
import beans.Poruka;
import beans.Prodavac;
import beans.Recenzija;
import beans.User;
import dao.KategorijaDAO;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/oglasi")
public class OglasiService {
	
	@Context
	ServletContext ctx;
	private OglasDAO oglDao = new OglasDAO();
	
	
	public OglasiService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("oglDAO")==null) {
			String contextPath = ctx.getRealPath("");
			oglDao = new OglasDAO(contextPath);
			ctx.setAttribute("oglDAO", oglDao);
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Oglas> getOglase() {
	oglDao = (OglasDAO)ctx.getAttribute("oglDAO");
		ArrayList<Oglas> sviKor = new ArrayList<Oglas>();
		
		for (Oglas k : oglDao.getOglasi().values()) {
			System.out.println("get oglas "+k.getNaziv());
				sviKor.add(k);
			}
		
		return sviKor;
}
	@GET
	@Path("/pretraziOglase")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Oglas> oglasiPretraga(@QueryParam("ime") String ime,@QueryParam("minc") String minc,
			     @QueryParam("maxc") String maxc,@QueryParam("mino") String mino,@QueryParam("maxo") String maxo,
			     @QueryParam("mind") String mind,@QueryParam("maxd") String maxd,
			     @QueryParam("grad") String grad,@QueryParam("status") String status) {
		oglDao = (OglasDAO)ctx.getAttribute("oglDAO");
		
		ArrayList<Oglas> povratna = new ArrayList<Oglas>();
		for(Oglas o :oglDao.getOglasi().values()) {
			povratna.add(o);
		}
		System.out.println("USAO U PRETRAGU");
		boolean vrsiSeIme = false;
			if(ime != null  && ime.equals("")==false && ime.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po nazivu "+ ime);
			
			for(Oglas o : oglDao.getOglasi().values()) {
				if(o.getNaziv().equalsIgnoreCase(ime) == false) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			vrsiSeIme = true;
			
		}
			boolean vrsiSeGrad = false;
			
		if(grad != null && grad.equals("")==false && grad.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po gradu "+ grad);
			
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getGrad().equalsIgnoreCase(grad) == false) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeGrad = true;
			
		}
		
		boolean vrsiSeMinCijena = false;
		
		if(minc != null && minc.equals("")==false && minc.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po minc "+ minc);
			double pom = Double.parseDouble(minc);
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getCijena()<pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMinCijena = true;
			
		}
		
		boolean vrsiSeMaxCijena = false;
		
		if(maxc != null && maxc.equals("")==false && maxc.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po maxc "+ maxc);
			double pom = Double.parseDouble(maxc);
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getCijena()>pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMaxCijena = true;
			
		}
		
		boolean vrsiSeMinOCijena = false;
		
		if(mino != null && mino.equals("")==false && mino.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po mino "+ mino);
			double pom = Double.parseDouble(mino);
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getBrLajkova()<pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMinOCijena = true;
			
		}
		
		boolean vrsiSeMaxOCijena = false;
		
		if(maxo != null && maxo.equals("")==false && maxo.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po maxo "+ maxo);
			double pom = Double.parseDouble(maxo);
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getBrLajkova()>pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMaxOCijena = true;
			
		}
	/*	 String pattern = "MM/dd/yyyy";
		   DateFormat df = new SimpleDateFormat(pattern);
		   Date date=null;
		try {
			date = df.parse(mind);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	boolean vrsiSeMinDAtum = false;
		
		if(mind != null && mind.equals("")==false && mind.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po mind "+ mind);
			long pom = date.getTime();
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getDatIsticanja()<pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMinDAtum = true;
			
		}
		
		boolean vrsiSeMaxDatum = false;
		  Date datee=null;
			try {
				datee = df.parse(maxd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(maxd != null && maxd.equals("")==false && maxd.equals("undefined")==false) {
			
			System.out.println("usao u pratagu po maxd "+ maxd);
			long pom = datee.getTime();
			for(Oglas o : oglDao.getOglasi().values()) {
				
				if(o.getDatIsticanja()>pom) {
					if(povratna.contains(o)) {
						povratna.remove(o);
						
					}
				}
			}
			 vrsiSeMaxDatum= true;
			
		}*/
			
		if(vrsiSeIme == false && vrsiSeGrad == false && vrsiSeMinCijena==false && vrsiSeMaxCijena==false && vrsiSeMinOCijena==false && vrsiSeMaxOCijena==false /*&& vrsiSeMinDAtum==false && vrsiSeMaxDatum==false*/){
			return null;
			
		}
		
		for(Oglas o : povratna) {
			System.out.println("povr "+o.getNaziv());
		}
		return povratna;
	}
	
	@GET
	@Path("/getDesetOglasa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Oglas> vratipopularne() {
					
				
		oglDao = (OglasDAO)ctx.getAttribute("oglDAO");
		ArrayList<Oglas> oglasi = new ArrayList<Oglas>();
		ArrayList<Oglas> oglPovr = new ArrayList<Oglas>();
					
		for(Oglas o : oglDao.getOglasi().values()) {
			oglasi.add(o);
		}
		Collections.sort(oglasi, Comparator.comparingInt(Oglas::getPopularnost));
		
		int brojac = 0;
		for(int i = oglasi.size()-1;i>=0;i--) {
			brojac++;
			oglPovr.add(oglasi.get(i));
			if(brojac == 10) {
				break;
			}
		}
		
		System.out.println("ima oglasa "+oglPovr.size());
		
		return oglPovr;			
	}
	
	@GET
	@Path("/odgovori/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getOglseOdgovori(@PathParam("id") String id) {
	oglDao = (OglasDAO)ctx.getAttribute("oglDAO");
	
		//ArrayList<Oglas> sviKor = new ArrayList<Oglas>();
		ArrayList<String> x = new ArrayList<String>();
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		User u = userDao.findU(id);
		System.out.println(u.getKorisnicko()+"user get ogl odg"+u.getUloga());
		for (Poruka p : u.getPoruke()) {
			if(p.getPosiljalac()!=null) {
				x.add(p.getNaziv_oglasa());
				System.out.println(p.getNaziv_oglasa()+u.getPoruke().size());
			}
			}
		
		return x;

}
	@GET
	@Path("/getGradove")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getGradove() {
	oglDao = (OglasDAO)ctx.getAttribute("oglDAO");
	ArrayList<String> x = new ArrayList<String>();
	
	for (Oglas o : oglDao.getOglasi().values()) {
		x.add(o.getGrad());
	}	
	return x;

}
	
	@POST
	@Path("/addoglas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Oglas dodajOglas(Oglas res ,@Context HttpServletRequest req) throws IOException {
		User user = (User) req.getSession(false).getAttribute("user");
		   oglDao = (OglasDAO) ctx.getAttribute("oglDAO");
		   String contextPath = ctx.getRealPath(""); 

		   String base64Image = (res.getImgSlika()).split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			File outputfile = new File(contextPath+"/slike/"+res.getNaziv()+".jpg");
			ImageIO.write(img, "jpg", outputfile);
			res.setImgSlika("slike/"+res.getNaziv()+".jpg");
		   
		   String pattern = "MM/dd/yyyy";
		   DateFormat df = new SimpleDateFormat(pattern);
			String drDat2 = df.format(res.getDatumIsticanja());
			res.setDrugiDat(drDat2);
			res.setDaLiJeAktivam(true);
			res.setVlasnik(user.getKorisnicko());
			long pom = res.getDatumIsticanja().getTime();
			res.setDatIsticanja(pom);
			
			KategorijaDAO catDao = (KategorijaDAO) this.ctx.getAttribute("katDAO");
			for(String str : res.getuKategorijama()){
				catDao.find(str).getListaOglasa().add(str);
			}
			
			if(oglDao.getOglasi().values().contains(res)) {
				System.out.println("Oglas vec postoji");
				return null;
			}
			//oglDao.getOglasi().values().add(res);
		   catDao.saveKategorije();
		   oglDao.saveOglase();
		   oglDao.save(contextPath, res);
		   System.out.println("okkkkk"); 
		   return  res;
					
	}
	

	
	@PUT
	@Path("/deloglas/{name}")
	public Response delOglas(@PathParam("name") String name, @Context HttpServletRequest req){

		UserDAO udao  = (UserDAO)this.ctx.getAttribute("userDAO");
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		
		 
		Oglas k = dao.find(name);
		k.setDaLiJeAktivam(false);
		

		System.out.println("ogl je "+k.getNaziv()+k.isDaLiJeAktivam());
		User user = (User)req.getSession(false).getAttribute("user");
		if(user instanceof Admin){
			Poruka m = new Poruka();
			m.setAktivna(true);
			m.setNaslov("Automatska poruka");
			m.setPosiljalac("Admin");
			m.setSadrzaj("Vas oglas je obrisan");
			Prodavac p = (Prodavac)udao.findU(k.getVlasnik());
			p.getPoruke().add(m);
			System.out.println("Amin poslao poruku"+p.getKorisnicko());
			if(!k.getStatus().equals("aktivan")) {
				Poruka s = new Poruka();
				s.setAktivna(true);
				s.setPosiljalac("Admin");
				s.setSadrzaj("Oglas je "+name+ " obrisan od strane aministratora.");
				Kupac l =null;
				for(User x : udao.getUsers().values()) {
					if(x instanceof Kupac && ((Kupac) x).getDostavljeni()!=null) {
						for(Oglas i :  ((Kupac) x).getDostavljeni()) {
							if(i.getNaziv().equals(k.getNaziv())) {
								l=(Kupac)udao.findU(i.getVlasnik());
								break;
							}
						else if(x instanceof Kupac && ((Kupac) x).getPoruceni()!=null) {
							for(String ii :  ((Kupac) x).getPoruceni()) {
								if(ii.equals(k.getNaziv())) {
									Oglas y = dao.find(ii);
									l=(Kupac)udao.findU(y.getVlasnik());
									break;
								}
						}
					}
				}
				l.getPoruke().add(s);
			}
			
		}
			}
		dao.saveOglase();
		
	}	

	return Response.status(200).build();
}
	
	
	@PUT
	@Path("/activate/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAcc(@PathParam("id") String id, @Context HttpServletRequest request) {
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		 
		Oglas k = dao.find(id);
		k.setDaLiJeAktivam(true);
		System.out.println("ogl je "+k.getNaziv()+k.isDaLiJeAktivam());
		dao.saveOglase();

		return Response.status(200).build();
	}
	
	//+stariNaziv+'/'+naziv+'/'+cijena+'/'+opis+'/'+datumIst+'/'+grad+'/'+imgSource,
	@PUT
	@Path("/izmjeni/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response promjeni(@PathParam("id") String id, Oglas res, @Context HttpServletRequest request) throws IOException {
		
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO udao  = (UserDAO)this.ctx.getAttribute("userDAO");
		 String contextPath = ctx.getRealPath(""); 
		 Oglas k = dao.find(id);
		
		
		if(k!=null) {
			if(res.getNaziv()!=null) {
				k.setNaziv(res.getNaziv());
			}
			if(res.getOpis()!=null) {
				k.setOpis(res.getOpis());
			}
			
			
			if(res.getGrad()!=null) {
				k.setGrad(res.getGrad());
			}
			if(res.getImgSlika()!=null) {
				k.setImgSlika(res.getImgSlika());
			}
			long pom = k.getDatumIsticanja().getTime();
			k.setDatIsticanja(pom);

			   String pattern = "MM/dd/yyyy";
			   DateFormat df = new SimpleDateFormat(pattern);
				String drDat2 = df.format(k.getDatumIsticanja());
				k.setDrugiDat(drDat2);
				
				   String base64Image = (k.getImgSlika()).split(",")[1];
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
					
					File outputfile = new File(contextPath+"/slike/"+k.getNaziv()+".jpg");
					ImageIO.write(img, "jpg", outputfile);
					k.setImgSlika("slike/"+k.getNaziv()+".jpg");
					dao.getOglasi().remove(id);
					dao.getOglasi().put(k.getNaziv(), k);
				   
			User user = (User)request.getSession(false).getAttribute("user");
			System.out.println("Promjenjena oglasa");
			if(user instanceof Admin){
				Poruka m = new Poruka();
				m.setAktivna(true);
				m.setNaslov("Automatska poruka");
				m.setPosiljalac("Admin");
				m.setSadrzaj("Vas oglas "+id+" je izmjenjen od strane administratora.");
				Prodavac p = (Prodavac)udao.findU(k.getVlasnik());
				p.getPoruke().add(m);
				System.out.println("Amin poslao poruku"+p.getKorisnicko());
			}
		}
		dao.saveOglase();
		return Response.status(200).build();
	}
	

	@PUT
	@Path("/likedAd/{name}")
	public Response likeAd(@PathParam("name") String name, @Context HttpServletRequest req ){
		
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null) ){
			return Response.status(400).entity("Invalid user").build();
		}
		
		Kupac temp = (Kupac) user;
		if(temp.getLajkovaniOglasi().contains(name)){
			return Response.status(400).entity("Already liked ad").build();
		}
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Oglas o = oglDao.find(name);
		
		if(o == null)
			return Response.status(400).entity("Invalid ad").build();
		
		o.setBrLajkova(o.getBrLajkova()+1);
		temp.getLajkovaniOglasi().add(name);
		

		oglDao.saveOglase();
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		userDao.saveUsers();
		
		return Response.status(200).build();
	}
	
	@PUT
	@Path("/prijavi/{name}")
	public Response prijaviOglas(@PathParam("name") String name, @Context HttpServletRequest req ){
		
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null) ){
			return Response.status(400).entity("Invalid user").build();
		}
		
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO uDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Oglas o = oglDao.find(name);
		
		if(o == null)
			return Response.status(400).entity("Invalid ad").build();
		
		Poruka p = new Poruka();
		p.setAktivna(true);
		p.setNaslov("Automatska poruka");
		p.setNaziv_oglasa(o.getNaziv());
		p.setPosiljalac("Admin");
		p.setSadrzaj("Vas oglas "+o.getNaziv()+ " je prijavljen kao prevara!");
		Prodavac pr = (Prodavac)uDao.findU(o.getVlasnik());
		if(pr!=null) {
			pr.getPoruke().add(p);
			System.out.println("Poruka upoyorenja poslata "+pr.getKorisnicko());
		}
		return Response.status(200).build();
	}
	
	@PUT
	@Path("/dislikeAd/{name}")
	public Response dislikeAd(@PathParam("name")String adName, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Invalid user").build();
		}
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Oglas o = oglDao.find(adName);
		
		if(o == null)
			return Response.status(400).entity("Invalid ad").build();
		
		Kupac temp = (Kupac) user;
		if(temp.getDislijovaniOglsi().contains(adName)){
			return Response.status(400).entity("Already disliked ad").build();
		}
		
		o.setBrDislajkova(o.getBrDislajkova()+1);
		temp.getDislijovaniOglsi().add(adName);
		//sacuvati ad i user
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		userDao.saveUsers();
		oglDao.saveOglase();
		return Response.status(200).build();
	}


	@POST
	@Path("/addToFavorite/{name}")
	public Response addToFavorite(@PathParam("name") String adName, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Invalid user").build();
		}
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Oglas o = oglDao.find(adName);
		if(o == null){
			return Response.status(400).entity("Invalid ad").build();
		}
		o.setPopularnost(o.getPopularnost()+1);
		Kupac temp = (Kupac) user;
		
		if(temp.getOmiljeni().contains(o.getNaziv())){
			return Response.status(400).entity("Ad already added to favirties").build();
		}
		
		temp.getOmiljeni().add(o.getNaziv());
		System.out.println("omiljeni "+o.getNaziv()+o.getStatus());
	//	o.setPopularity(ad.getPopularity() +1 );
		//sacuvatu user i ad
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		userDao.saveUsers();
		oglDao.saveOglase();;
			
		return Response.status(200).build();
		
	}
	@POST
	@Path("/ukorpu/{name}")
	public Response addToChart(@PathParam("name") String adName, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Nema user").build();
		}
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Oglas o = oglDao.find(adName);
		if(o == null){
			return Response.status(400).entity("Nema oglasa").build();
		}
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Kupac temp = (Kupac)userDao.findU(user.getKorisnicko());
		
		//Kupac temp = (Kupac) user;
		
		if(temp.getPoruceni().contains(o.getNaziv())){
			return Response.status(400).entity("Vec dodat u korpu").build();
		}
		
		temp.getPoruceni().add(o.getNaziv());
		o.setStatus("U realizaciji");
		System.out.println("u korpu "+o.getNaziv()+o.getStatus());
		//sacuvatu user i ad
		
		userDao.saveUsers();
		oglDao.saveOglase();
	return Response.status(200).build();
		
	}
	
	@POST
	@Path("/dostavljen/{name}")
	public Response dostavljen(@PathParam("name") String adName, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Invalid user").build();
		}
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Oglas o = oglDao.find(adName);
		if(o == null){
			return Response.status(400).entity("Invalid ad").build();
		}
		
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Kupac temp = (Kupac)userDao.findU(user.getKorisnicko());
		
		if(temp.getDostavljeni().contains(o)){
			return Response.status(400).entity("Vec dostavljen").build();
		}
		
		temp.getPoruceni().remove(o.getNaziv());
		
		temp.getDostavljeni().add(o);
		o.setStatus("dostavljen");
		System.out.println("oglas "+o.getNaziv()+o.getStatus());
		
		Poruka auto = new Poruka();
		auto.setNaslov("Automatska poruke");
	
		User prodavac = (User) userDao.findU(o.getVlasnik());
		
		if(prodavac == null){
			return Response.status(400).entity("Invalid user").build();
		}
		
		auto.setSadrzaj("Korisniku "+ user.getKorisnicko() + " je dostavljen proizvod "+ o.getNaziv());
		prodavac.getPoruke().add(auto);
		System.out.println("dostavljen "+o.getNaziv()+o.getStatus());
		//sacuvatu user i ad
		userDao.saveUsers();
		oglDao.saveOglase();
	return Response.status(200).build();
		
	}
	
	
	
	@GET
	@Path("/getomiljene")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getOmiljene(@Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return null;
		}
		ArrayList<Oglas> adList = new ArrayList<Oglas>();
		
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Kupac temp = (Kupac) user;
		for(String ad : temp.getOmiljeni()){
			if(dao.find(ad) !=  null){
				adList.add(dao.find(ad));
				//System.out.println("omiljeni oglas "+ad.getNaziv()+ad.getStatus());
			}
		}
	
		return adList;		
	}
	
	@GET
	@Path("/getporucene")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getPorucene(@Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return null;
		}
		ArrayList<Oglas> adList = new ArrayList<Oglas>();
		
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Kupac temp = (Kupac) user;
		for(String ad : temp.getPoruceni()){
			if(dao.find(ad) !=  null){
				adList.add(dao.find(ad));
				//System.out.println("poruceni oglas "+ad.getNaziv()+ad.getStatus());
			}
		}
		return adList;		
	}

	@GET
	@Path("/getdostavljene")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getDostavljene(@Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return null;
		}
		ArrayList<Oglas> adList = new ArrayList<Oglas>();
		
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		Kupac temp = (Kupac) user;
		for(Oglas ad : temp.getDostavljeni()){
			if(dao.find(ad.getNaziv()) !=  null){
				adList.add(ad);
				System.out.println("get dostavljeni oglas "+ad.getNaziv()+ad.getStatus());
			}
		}
		return adList;		
	}
	
	@GET
	@Path("/getOglizKAt/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getOglaseIzKategorije(@PathParam("id") String id,@Context HttpServletRequest req){
		
		ArrayList<Oglas> adList = new ArrayList<Oglas>();
		System.out.println("Usao u pretragu");
		OglasDAO dao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		for(Oglas o : dao.getOglasi().values()) {
			System.out.println(o.getNaziv());
			if(o.isDaLiJeAktivam() && o.getStatus().equals("aktivan")) {
				System.out.println(o.getNaziv()+" aktivan");
				for(String k : o.getuKategorijama()) {
					System.out.println("u kat"+id+k);
					if(k.equals(id)) {
						adList.add(o);
						System.out.println("Oglas "+o.getNaziv());
					}
				}
			}
		}
		return adList;		
	}
	
	@PUT
	@Path("/likedProdavca/{username}")
	public Response likeSal(@PathParam("username") String username, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Invalid user").build();
		}
		
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Prodavac prodavac =  (Prodavac)userDao.findU(username);
		
		if(prodavac == null){
			return Response.status(400).entity("Invalid salesman").build();		
		}
		
		prodavac.setBrLajk(prodavac.getBrLajk()+1);
		System.out.println("Prodavac "+prodavac.getKorisnicko()+" lajkova "+prodavac.getBrLajk());
		//sacuvati user
		userDao.saveUsers();
		
		
		
		return Response.status(200).build();
	}
	
	@PUT
	@Path("/dislikeProdavca/{username}")
	public Response dislikeSal(@PathParam("username") String username, @Context HttpServletRequest req){
		User user = (User)req.getSession(false).getAttribute("user");
		if((user == null)){
			return Response.status(400).entity("Invalid user").build();
		}
		
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Prodavac prodavac =  (Prodavac)userDao.findU(username);
		
		if(prodavac == null){
			return Response.status(400).entity("Invalid salesman").build();		
		}
		
		prodavac.setBrDisLajk(prodavac.getBrDisLajk()+1);
		System.out.println("Prodavac "+prodavac.getKorisnicko()+" dislajkova "+prodavac.getBrDisLajk());
		//sacuvati user
		userDao.saveUsers();	
		return Response.status(200).build();
	}
	@GET
	@Path("/getMojeOglase")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getMojeOglase(@Context HttpServletRequest req){
		User user = (User) req.getSession(false).getAttribute("user");

		ArrayList<Oglas> adList = new ArrayList<Oglas>();
		
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		
		if(oglDao.getOglasi() == null){
			return null;
		}
		
		for(Oglas ad : oglDao.getOglasi().values()){
			if(ad.getVlasnik()!=null) {
			if(ad.getVlasnik().equals(user.getKorisnicko())){
				adList.add(ad);
				System.out.println("moj oglas"+ad.getNaziv());
			}
		}}
		
		return adList;	
	}
	
	@GET
	@Path("/getMojeRecenzije")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Recenzija> getSalReview(@Context HttpServletRequest req){
		User user = (User) req.getSession(false).getAttribute("user");
		
		ArrayList<Recenzija> recList = new ArrayList<Recenzija>();
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
	
		for(Oglas o: oglDao.getOglasi().values()) {
			if(o.getVlasnik()!=null) {
				System.out.println("oglas nije null");
			if(o.getVlasnik().equals(user.getKorisnicko())) {
				System.out.println("oglas "+o.getNaziv());
				for(Recenzija r: o.getListaRecenzija()) {
					recList.add(r);
					System.out.println("recenzija "+r.getNaslov());
				}
			}
			}
		}	
		return recList;
	}
	
	@GET
	@Path("/getMojeRecenzije2")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Recenzija> getRecenzijeKupac(@Context HttpServletRequest req){
		User user = (User) req.getSession(false).getAttribute("user");
		
		ArrayList<Recenzija> recList = new ArrayList<Recenzija>();
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
	
		for(Oglas o: oglDao.getOglasi().values()) {
			if(o.getListaRecenzija()!=null) {
				for(Recenzija r: o.getListaRecenzija()) {
					if(r.getRecenzent().equals(user.getKorisnicko())) {
						recList.add(r);
						System.out.println("recenzija get2 "+r.getNaslov());
					}
					
				}
			
			}
		}	
		return recList;
	}
	
	
}