package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Oglas;
import beans.Poruka;

public class OglasDAO {
	private HashMap<String, Oglas> oglasi = new HashMap<>();


	private String ctxpath;


	public OglasDAO() {

	}
	
	public OglasDAO(String contextPath) {
		this.ctxpath = contextPath;
		try{
			loadPoruke();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Oglas find(String username) {
		if(oglasi.containsKey(username)) {
			Oglas pronadjeni = oglasi.get(username);
			return pronadjeni;
		}
		return null;	
	}
	
	public void save(String contextPath,Oglas o) {
		if(oglasi.containsKey(o.getNaziv())==false) {
			oglasi.put(o.getNaziv(), o);
			saveOglase();
		}
	}
	
	public void saveOglase(){
		ObjectMapper mapper = new ObjectMapper();
		

		
		ArrayList<Oglas> ogl = new ArrayList<Oglas>();
		for(Oglas o : this.oglasi.values()){
				ogl.add(o);	
		}
		
		
		File oglFile = new File(this.ctxpath +"/oglasi.json");
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(oglFile, ogl);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
private void loadPoruke() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		

		StringBuilder json = new StringBuilder();
		String temp;

		
		File oglFile = new File(this.ctxpath +"/oglasi.json");
		json.setLength(0);
		json = new StringBuilder();
		try{
			BufferedReader br  = new BufferedReader(new FileReader(oglFile));
			while((temp = br.readLine()) != null){
				json.append(temp);
			}
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		List<Oglas> ogl = mapper.readValue(json.toString(), new TypeReference<ArrayList<Oglas>>() {});
		
		for(Oglas c: ogl){
			
			this.oglasi.put(c.getNaziv(), c);
		}
			
	}

public HashMap<String, Oglas> getOglasi() {
	return oglasi;
}

public void setOglasi(HashMap<String, Oglas> poruke) {
	this.oglasi = poruke;
}


	
}
