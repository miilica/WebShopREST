package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
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


import beans.Kategorija;
import dao.KategorijaDAO;
import dao.OglasDAO;
import beans.Oglas;




@Path("/kategorije")
public class KategorijaService {
	@Context
	ServletContext ctx;
	private KategorijaDAO katDao = new KategorijaDAO();

	public KategorijaService() {
		
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("katDAO") == null) {
			String contextPath = ctx.getRealPath("");
	    	katDao = new KategorijaDAO(contextPath);
	    	//System.out.println(contextPath+"ovo je putanja");
			ctx.setAttribute("katDAO", katDao);
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Kategorija> getKtegorije(@QueryParam("username") String username) {
		katDao = (KategorijaDAO)ctx.getAttribute("katDAO");
	if(username!=null) {
		ArrayList<Kategorija> pretrazeni = new ArrayList<Kategorija>();
		for (Kategorija k : katDao.getKategorije().values()) {
			if(k.getNaziv().contains(username)) {
				pretrazeni.add(k);
			}
		}
		return pretrazeni;
		
	}else {
	
		ArrayList<Kategorija> sviKor = new ArrayList<Kategorija>();
		
		for (Kategorija k : katDao.getKategorije().values()) {
				sviKor.add(k);
			}
		
		return sviKor;
	}
}
	
	@POST
	@Path("/addkategoriju")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Kategorija dodajKategoriju(Kategorija res) {
			
		   katDao = (KategorijaDAO) ctx.getAttribute("katDAO");
		   String contextPath = ctx.getRealPath(""); 
		 
		   res.setAktivna(true);
		   res.setAk("Da");
		   
		   katDao.save(contextPath, res);
		   ctx.setAttribute("katDAO", katDao);
		 //  System.out.println(res.getNaziv()+res.getOpis());
		   return res;
			
		
	}
	
	@GET
	@Path("/getOgluKat/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getOglase(@PathParam("name") String name){
		KategorijaDAO catDao = (KategorijaDAO) ctx.getAttribute("katDAO");
		OglasDAO oglDao = (OglasDAO) ctx.getAttribute("oglDAO");

		
		Kategorija category = catDao.find(name);
		Collection<Oglas> ads = new ArrayList<Oglas>();
		
		for (String pom: category.getListaOglasa()) {
			Oglas ad = oglDao.find(pom);
			if ((ad!= null) && (ad.isDaLiJeAktivam()) && (ad.getStatus().equals("aktivan")))
				ads.add(ad);
			System.out.println(ad.getNaziv()+" dodat");
		}

		return ads;
	}
	
	@PUT
	@Path("/delkategoriju/{name}")
	public Response delCat(@PathParam("name") String name, @Context HttpServletRequest req){
	/*	if(!isAdmin(req)){
			return Response.status(400).entity("Invalid user").build();
		}*/
		
		KategorijaDAO dao = (KategorijaDAO) this.ctx.getAttribute("katDAO");
		  
		Kategorija k = dao.find(name);
		k.setAk("Ne");
		k.setAktivna(false);
	
		System.out.println("kat je "+k.getNaziv()+k.isAktivna());
		
		//katDao.save(contextPath, k);
		//  ctx.setAttribute("katDAO", katDao);
		dao.saveKategorije();
		
		return Response.status(200).build();
		
	}
	
	@PUT
	@Path("/activate/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAcc(@PathParam("id") String id, @Context HttpServletRequest request) {
		KategorijaDAO dao = (KategorijaDAO) this.ctx.getAttribute("katDAO");	 
		Kategorija k = dao.find(id);
		k.setAk("Da");
		k.setAktivna(true);
		
	//	katDao.save(contextPath, k);
	//	   ctx.setAttribute("katDAO", katDao);
		dao.saveKategorije();
		return Response.status(200).build();
	}
	

	@PUT
	@Path("/izmjeni/{id}/{naziv}/{opis}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response promjeni(@PathParam("id") String id, @PathParam("naziv") String naziv, @PathParam("opis") String opis,
			@Context HttpServletRequest request) {
		
		KategorijaDAO dao = (KategorijaDAO) this.ctx.getAttribute("katDAO");
		 String contextPath = ctx.getRealPath(""); 
		 
		Kategorija k = dao.find(id);
		
		if(k!=null) {
			k.setNaziv(naziv);
			k.setOpis(opis);
			System.out.println("Promjenjena kategorijaa");
			k.setAk("Da");
			k.setAktivna(true);
		}
		dao.saveKategorije();
		katDao.save(contextPath, k);
	   // ctx.setAttribute("katDAO", katDao);
		
	
		return Response.status(200).build();
	}

}
