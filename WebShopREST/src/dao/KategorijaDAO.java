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

import beans.Kategorija;

public class KategorijaDAO {
	private HashMap<String, Kategorija> kategorije = new HashMap<>();
	private String ctxpath;

	public KategorijaDAO() {

	}

	public KategorijaDAO(String contextPath) {
		this.ctxpath = contextPath;
		try {
			loadKategorie();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Kategorija find(String username) {
		if(kategorije.containsKey(username)) {
			Kategorija pronadjeni = kategorije.get(username);
				return pronadjeni;		
		}
		return null;	
	}
	

	public void save(String contextPath,Kategorija k) {
		if(kategorije.containsKey(k.getNaziv())==false) {
		
		kategorije.put(k.getNaziv(), k);
		System.out.println(contextPath+"1 put stavlja");
		saveKategorije(); 
		}
	}
	
	

	public HashMap<String, Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(HashMap<String, Kategorija> kategorije) {
		this.kategorije = kategorije;
	}
	
	public void saveKategorije(){
		ObjectMapper mapper = new ObjectMapper();
		

		
		ArrayList<Kategorija> kat = new ArrayList<Kategorija>();
		for(Kategorija temp : this.kategorije.values()){
				kat.add(temp);	
		}
		
		
		File cutFile = new File(this.ctxpath +"/kategorije.json");
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(cutFile, kat);
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
	

	private void loadKategorie() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		

		StringBuilder json = new StringBuilder();
		String temp;

		
		//load customer
		File customerFile = new File(this.ctxpath +"/kategorije.json");
		json.setLength(0);
		json = new StringBuilder();
		try{
			BufferedReader br  = new BufferedReader(new FileReader(customerFile));
			while((temp = br.readLine()) != null){
				json.append(temp);
			}
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		List<Kategorija> kat = mapper.readValue(json.toString(), new TypeReference<ArrayList<Kategorija>>() {});
		
		for(Kategorija c: kat){
			
			this.kategorije.put(c.getNaziv(), c);
		}
		
		
		
	}

	

	
}
