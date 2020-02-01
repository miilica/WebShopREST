package services;

import java.util.Collection;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Oglas;
import beans.Poruka;
import beans.User;
import dao.OglasDAO;
import dao.UserDAO;

@Path("/poruke")
public class PorukaService {
		
		@Context
		ServletContext ctx;

		
		
		public PorukaService() {
			
		}
		
	
		

		@POST
		@Path("/addporuku/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Poruka sendMessage(Poruka message,@PathParam("id") String id, @Context HttpServletRequest req){
			
			User userSender = (User)req.getSession(false).getAttribute("user");
			UserDAO dao = (UserDAO) this.ctx.getAttribute("userDAO");
			OglasDAO oglDao = (OglasDAO) this.ctx.getAttribute("oglDAO");
			Oglas pom = oglDao.find(id);
			System.out.println("vlasnik oglasa "+pom.getVlasnik());
			String x = pom.getVlasnik();
			User userReciver = dao.findU(x);
			
			if (userSender == null)
				return null;
			if (userReciver == null)
				return null;

			if (userSender.getUloga().equals("Kupac") && userReciver.getUloga().equals("Admininstrator")){
				return null;
			}
			
			message.setPosiljalac(userSender.getKorisnicko());
			System.out.println("posiljalac "+userSender.getKorisnicko()+userReciver.getKorisnicko());
			
			userReciver.getPoruke().add(message);
			
			dao.saveUsers();
			//sacuvati
			
			return message;
			
		}
		
		@POST
		@Path("/addporukuadminu/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Poruka porukaAdminu(Poruka message,@PathParam("id") String id, @Context HttpServletRequest req){
			
			User userSender = (User)req.getSession(false).getAttribute("user");
			UserDAO dao = (UserDAO) this.ctx.getAttribute("userDAO");
					
			User userReciver = dao.findU(id);
			
			if (userSender == null)
				return null;
			if (userReciver == null)
				return null;

			
			message.setPosiljalac(userSender.getKorisnicko());
			System.out.println("posiljalac "+userSender.getKorisnicko()+"adminu "+userReciver.getKorisnicko());
			userReciver.getPoruke().add(message);
			
			dao.saveUsers();
			//sacuvati
			
			return message;
			
		}
	
		
		@PUT
		@Path("/del/{name}")
		public Response delOglas(@PathParam("name") String name, @Context HttpServletRequest req){

			User user = (User) req.getSession(false).getAttribute("user");
			Collection<Poruka> pom = user.getPoruke();
			UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
			if(pom!=null) {
				for(Poruka p : pom) {
					if(p.getNaslov().equals(name)) {
						p.setAktivna(false);
						System.out.println("Izbrisana poruka");
						dao.saveUsers();
						return Response.status(200).build();
					}
				}
			}
			
			return Response.status(400).build();
			
		}
		
		@PUT
		@Path("/activate/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response activateAcc(@PathParam("id") String id, @Context HttpServletRequest request) {
			User user = (User) request.getSession(false).getAttribute("user");
			Collection<Poruka> pom = user.getPoruke();
			UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
			if(pom!=null) {
				for(Poruka p : pom) {
					if(p.getNaslov().equals(id)) {
						p.setAktivna(true);
						System.out.println("Vracena poruka");
						dao.saveUsers();
						return Response.status(200).build();
					}
				}
			}
			
			return Response.status(400).build();
		}
		

		
	
}
