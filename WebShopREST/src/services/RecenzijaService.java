package services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import beans.Kupac;
import beans.Oglas;
import beans.Poruka;
import beans.Recenzija;
import beans.User;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/recenzije")
public class RecenzijaService {
	@Context 
	ServletContext ctx;

	
	public RecenzijaService() {
		
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Recenzija> getRecenzije(@QueryParam("username") String username) {
		
	
		ArrayList<Recenzija> recenzije = new ArrayList<Recenzija>();
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		
		for (Oglas o : oglDao.getOglasi().values()) {
			if(o!=null) {
				for(Recenzija r : o.getListaRecenzija()) {
					recenzije.add(r);
					System.out.println(" get Recenziju "+r.getNaslov()+r.getRecenzent());
				}
			}
		}
			
		
		return recenzije;
	
}

	@POST
	@Path("/add/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Recenzija dodajRecenziju(Recenzija r, @PathParam("name") String name, @Context HttpServletRequest req) throws IOException{
		Kupac user = (Kupac)req.getSession(false).getAttribute("user");
	
		r.setRecenzent(user.getKorisnicko());
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Oglas ogl = oglDao.find(name);
		 String contextPath = ctx.getRealPath(""); 
		 	if(r.getSlika()!=null || !r.getSlika().equals("")) {
		 	   String base64Image = (r.getSlika().split(","))[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

				BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
				
				File outputfile = new File(contextPath+"/slike/"+r.getNaslov()+".jpg");
				ImageIO.write(img, "jpg", outputfile);
				r.setSlika("slike/"+r.getNaslov()+".jpg");
		 	}
		
		if((ogl == null)){
			System.out.println("Oglas null kod add recenziju");
			return null;
		}
		
		List<Recenzija> recList = ogl.getListaRecenzija();
		if(recList.contains(r)) {
			System.out.println("Recenzija vec postoji");
			return null;
		}
		
		recList.add(r);
		
		System.out.println("dodata recenzija "+r.getNaslov()+r.getRecenzent()+r.getOglas());
		//poruke
		Poruka autoMessage = new Poruka();
		autoMessage.setNaslov("Automatska poruke");
		
		User prodavac = (User) userDao.findU(ogl.getVlasnik());
		
		if(prodavac == null){
			return null;
		}
		
		autoMessage.setSadrzaj("Korisnik "+ user.getKorisnicko() + " je napisao recenziju vase reklame "+ ogl.getNaziv());
		prodavac.getPoruke().add(autoMessage);
		System.out.println("auto Poruka posata "+prodavac.getKorisnicko());

		//sacuvati
		userDao.saveUsers();
		oglDao.saveOglase();	
		//System.out.println("User je " + user.getKorisnicko());
		System.out.println("Recenzija "+ogl.getNaziv()+"Prodavac je"+prodavac.getKorisnicko());
		return r;	
	}
	
	@PUT
	@Path("/del/{name}")
	public Response delRecenziju(@PathParam("name") String name, @Context HttpServletRequest req){
		Kupac user = (Kupac)req.getSession(false).getAttribute("user");
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		
		ArrayList<Oglas> oglasi = user.getDostavljeni();
		if(oglasi!=null) {
			for(Oglas o: oglasi) {
				ArrayList<Recenzija> recenzije = o.getListaRecenzija();
				if(recenzije!=null) {
					for(Recenzija r : recenzije) {
						if(r.getNaslov().equals(name)) {
							r.setAktivan(false);
						}
					}
				}
			}
		}
		oglDao.saveOglase();
		userDao.saveUsers();
		return Response.status(200).build();
		
	}
	
	@PUT
	@Path("/activate/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateRecenziju(@PathParam("id") String id, @Context HttpServletRequest req) {
		Kupac user = (Kupac)req.getSession(false).getAttribute("user");
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		
		ArrayList<Oglas> oglasi = user.getDostavljeni();
		if(oglasi!=null) {
			for(Oglas o: oglasi) {
				ArrayList<Recenzija> recenzije = o.getListaRecenzija();
				if(recenzije!=null) {
					for(Recenzija r : recenzije) {
						if(r.getNaslov().equals(id)) {
							r.setAktivan(true);
						}
					}
				}
			}
		}
		oglDao.saveOglase();
		userDao.saveUsers();
		return Response.status(200).build();
	}
	

	@PUT
	@Path("/izmjeni/{id}/{naslov}/{sadrzaj}/{tacan}/{dog}/{imgSource}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response promjeni(@PathParam("id") String id, @PathParam("naslov") String naslov, @PathParam("sadrzaj") String sadrzaj,
			@PathParam("tacan") String tacan, @PathParam("dog") String dog,
			 @PathParam("imgSource") String imgSource, @Context HttpServletRequest request) throws IOException {
		
		Kupac user = (Kupac)request.getSession(false).getAttribute("user");
		OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
		UserDAO userDao = (UserDAO) this.ctx.getAttribute("userDAO");
		Recenzija pom=null;
		
		ArrayList<Oglas> oglasi = user.getDostavljeni();
		if(oglasi!=null) {
			for(Oglas o: oglasi) {
				ArrayList<Recenzija> recenzije = o.getListaRecenzija();
				if(recenzije!=null) {
					for(Recenzija r : recenzije) {
						if(r.getNaslov().equals(id)) {
							pom=r;
							break;
						}
					}
				}
			}
		}
		if(pom!=null) {
		
			pom.setNaslov(naslov);
			pom.setSadrzaj(sadrzaj);

			pom.setDaLiJeOpisTacan(tacan);
			pom.setDaLiJeDogIspostovan(dog);
			
			System.out.println("Promjenjena rec"+pom.getNaslov()+pom.getSadrzaj());
		}
		pom.setSlika(imgSource);
		 String contextPath = ctx.getRealPath(""); 
		 	if(pom.getSlika()!=null || !pom.getSlika().equals("")) {
		 	   String base64Image = (pom.getSlika().split(","))[1];
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

				BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
				
				File outputfile = new File(contextPath+"/slike/"+pom.getNaslov()+".jpg");
				ImageIO.write(img, "jpg", outputfile);
				pom.setSlika("slike/"+pom.getNaslov()+".jpg");
		 	}
		oglDao.saveOglase();
		userDao.saveUsers();
		return Response.status(200).build();
	}
	
}
