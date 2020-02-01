package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import beans.Admin;
import beans.Kupac;
import beans.Prodavac;
import beans.User;

public class UserDAO{
	private HashMap<String, User> users = new HashMap<>();
	private String ctxpath;


	public UserDAO() {

	}

	public UserDAO(String contextPath) {
		this.ctxpath = contextPath;
		try{
			loadUsers();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public User find(String username, String password) {
		//System.out.println(" DOSAO U KLASU ZA FIND broj korisnika je "+users.size());
		if(users.containsKey(username)) {
			User pronadjeni = users.get(username);
			
			if(pronadjeni.getPassword().equals(password)) {	
				return pronadjeni;
			}
			
		}
		return null;	
	}
	
	public User findU(String username) {
		//System.out.println(" DOSAO U KLASU ZA FIND broj korisnika je "+users.size());
		if(users.containsKey(username)) {
			User pronadjeni = users.get(username);
			
			return pronadjeni;
			
		}
		return null;	
	}
	

	public void save(String contextPath,User k) {
		users.put(k.getKorisnicko(), k);
		System.out.println(contextPath);
		saveUsers();
	}
	
	

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}

	public Collection<User> findAll() {
		return users.values();
	}
	
	public void saveUsers(){
		ObjectMapper mapper = new ObjectMapper();
		

		ArrayList<Admin> adminList = new ArrayList<Admin>();
		for(User temp : this.users.values()){
			if(temp instanceof Admin){
				adminList.add((Admin)temp);
			}
		}
		
		ArrayList<Prodavac> salList = new ArrayList<Prodavac>();
		for(User temp : this.users.values()){
			if(temp instanceof Prodavac){
				salList.add((Prodavac)temp);
			}
		}
		
		ArrayList<Kupac> cutList = new ArrayList<Kupac>();
		for(User temp : this.users.values()){
			if(temp instanceof Kupac){
				cutList.add((Kupac)temp);
			}
		}
		
		File adminFile = new File(this.ctxpath +"/admin.json");
		try{
			mapper.writerWithDefaultPrettyPrinter().writeValue(adminFile, adminList);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		File salFile = new File(this.ctxpath +"/prodavac.json");
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(salFile, salList);
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
		
		File cutFile = new File(this.ctxpath +"/kupac.json");
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(cutFile, cutList);
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

	private void loadUsers() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();

			File adminFile = new File(this.ctxpath +"/admin.json");
			
			StringBuilder json = new StringBuilder();
			String temp;
			try{
				BufferedReader br  = new BufferedReader(new FileReader(adminFile));
				while((temp = br.readLine()) != null){
					json.append(temp);
				}
				br.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			List<Admin> adminList = mapper.readValue(json.toString(), new TypeReference<ArrayList<Admin>>() {});
			this.users.clear();
			for(Admin a  : adminList){
				this.users.put(a.getKorisnicko(), a);
			}

			File prodavacFile = new File(this.ctxpath +"/prodavac.json");
			json.setLength(0);
			json = new StringBuilder();
			try{
				BufferedReader br  = new BufferedReader(new FileReader(prodavacFile));
				while((temp = br.readLine()) != null){
					json.append(temp);
				}
				br.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			List<Prodavac> prodavci = mapper.readValue(json.toString(), new TypeReference<ArrayList<Prodavac>>() {});
			
			for(Prodavac s : prodavci){
				this.users.put(s.getKorisnicko(), s);
			}
			
			//load customer
			File kupacFile = new File(this.ctxpath +"/kupac.json");
			json.setLength(0);
			json = new StringBuilder();
			try{
				BufferedReader br  = new BufferedReader(new FileReader(kupacFile));
				while((temp = br.readLine()) != null){
					json.append(temp);
				}
				br.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			List<Kupac> kupci = mapper.readValue(json.toString(), new TypeReference<ArrayList<Kupac>>() {});
			
			for(Kupac c: kupci){
				
				this.users.put(c.getKorisnicko(), c);
			}
		
	}
	



	

}
