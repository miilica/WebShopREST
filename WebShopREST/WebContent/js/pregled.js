function loadPregled() {
	var korisnik = sessionStorage.getItem("korisnik");
	console.log("dobro dosli");

	$.ajax({
		url: 'rest/kategorije',
		type: 'get',
		success: function(kat) {
				for(k of kat){
					let it = $('<option>'+k.naziv+'</option>');
					$("select#kat").append(it);
					
				}
					
			}
		
	});
	
	//ulogovao se
	if(korisnik != null && korisnik !="null" && korisnik !="undefined") {
		console.log(korisnik);
		var user = JSON.parse(korisnik);
		console.log(user.uloga);
		$("#logovanje").hide();
		$("#adminDosao").hide();
		$("#pregledKategorija").hide();
		$("#dodajKat").hide();
		$("#dodajOglas").hide();
		$("#pregledAktivnihOglasa").hide();
		$("#dodajRecenziju").hide();
		$("#prikazRecenzija").hide();
		$("#registracija").hide();
		$("#pregledOglasa").hide();
		$("#omiljeniOglasi").hide();
		$("#poruceni").hide();
		$("#dostavljeni").hide();
		$("#prazno").hide();
		$("#dodajPoruku3").hide();
		$("#mojiOglasi").hide();
		$("#mojePoruke").hide();
		$("#recen").hide();
		$("#prazno2").hide();
		
		$("#mojeRecenzije").hide();
		$("#dodajPoruku").hide();
		$("#prikazPoruka").hide();
		$("#dodajPoruku2").hide();
		$("#adminSaljePOruku1").hide();
		$("#adminSaljePOruku2").hide();
		$("#adminDosao").hide();
		
			if(user.uloga === "Admininstrator"){
	 			$("#adminDosao").show();
	 			$("#pregledKategorija").show();
	 			$("#dodajKat").show();
	 			$("#prikazPoruka").show();
	 			$("#pregledOglasa").show();
	 			$("#mojePoruke").show();
	 			$("#pregledAktivnihOglasa").show();
	 			$("#adminSaljePOruku1").show();
	 			$("#adminSaljePOruku2").show();
	 			
			}else if(user.uloga === "Prodavac"){
				document.getElementById("lajk_dislajk").innerHTML = "Broj lajkova: "+user.brLajk+" Broj dislajkova: "+user.brDisLajk;
				$("#dodajOglas").show();
	 			$("#mojiOglasi").show();
	 			$("#mojePoruke").show();
	 			$("#recen").show();
	 			$("#prazno2").show();
	 			$("#pregledAktivnihOglasa").show();
	 			$("#dodajPoruku3").show();
	 			//$("#dodajPoruku2").show();
	 			$("#dodajPoruku2").show();
	 			
	 			$.ajax({
					url: 'rest/oglasi/',
					type: 'get',
					success: function(ogl) {
						if(ogl==null){
						}else {
							for(ad of ogl){
								if(ad.status==="aktivan"){
									prikaziOglas2(ad);	
								}
							}
								
						} 
					}
					
				});
				
					
			}else if(user.uloga === "Kupac"){
				document.getElementById("lajk_dislajk").innerHTML = " ";
				$("#dodajRecenziju").show();
				$("#prikazRecenzija").show();
				$("#omiljeniOglasi").show();
				$("#poruceni").show();
				$("#mojePoruke").show();
				$("#dostavljeni").show();
				$("#pregledOglasa").show();
				$("#prazno").show();
				$("#dodajPoruku").show();
				$("#mojeRecenzije").show();
				//$("#pregledAktivnihOglasa").show();
				$("#prikazPoruka").hide();
			
				$.ajax({
					url: 'rest/oglasi/',
					type: 'get',
					success: function(ogl) {
						if(ogl==null){
						}else {
							for(ad of ogl){
								if(ad.status==="aktivan"){
									prikaziOglas(ad);	
								}
							}
								
						} 
					}
					
				});
			}
		} else {
		//ne postoji korisnik
		console.log("usao u else");
		$("#adminDosao").hide();
			$("#pregledKategorija").hide();
			$("#dodajKat").hide();
			$("#dodajOglas").hide();
 			$("#pregledAktivnihOglasa").hide();
 			$("#dodajRecenziju").hide();
			$("#prikazRecenzija").hide();
			$("#dodajPoruku3").hide();
			
			$("#omiljeniOglasi").hide();
			$("#poruceni").hide();
			$("#dostavljeni").hide();
			$("#pregledOglasa").hide();
			$("#prazno").hide();
			
			$("#mojiOglasi").hide();
			$("#mojePoruke").hide();
			$("#recen").hide();
			$("#prazno2").hide();
			$("#mojeRecenzije").hide();
			document.getElementById("lajk_dislajk").innerHTML = " ";
			$("#dodajPoruku").hide();
			$("#dodajPoruku2").hide();
			$("#dodajPoruku3").hide();
			$("#prikazPoruka").hide();
			$("#adminSaljePOruku1").hide();
			$("#adminSaljePOruku2").hide();
			
		$("#odjava").hide();
		
	}

}

function izabranaKategorija(){
	var i = $('#kat').val();
	$.ajax({
		url: 'rest/oglasi/getOglizKAt/'+i,
		type: 'get',
		success: function(oglasi) {
			if(oglasi==null){
				alert('Nema oglasa u kategoriji!');
			}else {
				$('table#oglasi tbody td').remove();
				for(r of oglasi){
						odstampajOglas(r);
				}
				$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
			} 
		}
		
	});
}
$(document).ready(function(){
	
	
		$( "#pretragaOglasa" ).click(function() {
			window.location.href="pretragaOglasa.html";
			
		});
		
		$( "#pretragaKorisnika" ).click(function() {
			window.location.href="pretragaKorisnika.html";
			
		});
		$( "#adminDosao" ).click(function() {
			window.location.href="pregledkorisnika.html";
			
		});
		
		$( "#pregledKategorija" ).click(function() {
			window.location.href="pregledkategorija.html";
			
		});
		
		
		
		$( "#dodajKat" ).click(function() {
			window.location.href="kategorija.html";
			
		});
		$( "#dodajOglas" ).click(function() {
			window.location.href="dodajOglas.html";
			
		});
		
		$( "#pregledAktivnihOglasa" ).click(function() {
			window.location.href="pregledOglasa.html";
			
		});
		
		$( "#dodajRecenziju" ).click(function() {
			window.location.href="dodajRecenziju.html";
			
		});
		
		$( "#prikazRecenzija" ).click(function() {
			window.location.href="prikazrecenzija.html";
			
		});
		$( "#dodajPoruku" ).click(function() {
			window.location.href="dodajPoruku.html";
			
		});
		$( "#dodajPoruku2" ).click(function() {
			window.location.href="prodavacPoruka.html";
			
		});
		$( "#dodajPoruku3" ).click(function() {
			window.location.href="porukaAdminu.html";
			
		});
		$( "#prikazPoruka" ).click(function() {
			window.location.href="pregledPoruka.html";
			
		});
		
		$("#adminSaljePOruku1").click(function(){
			window.location.href="adminSaljePoruku1.html";
		});
		$("#adminSaljePOruku2").click(function(){
			window.location.href="adminSaljePoruku2.html";
		});
		
		$("#mojeRecenzije").click(function() {
			$.ajax({
				url: 'rest/oglasi/getMojeRecenzije2',
				type: 'get',
				success: function(rec) {
					if(rec==null){
						alert('Nema recenzija!');
					}else {
						$('table#oglasi tbody td').remove();
						for(r of rec){
								prikaziRecenzije(r);	
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
		});
		
		$("#recen").click(function() {
			$.ajax({
				url: 'rest/oglasi/getMojeRecenzije',
				type: 'get',
				success: function(rec) {
					if(rec==null){
						alert('Nema recenzija!');
					}else {
						$('table#oglasi tbody td').remove();
						for(r of rec){
								prikaziRecenzije2(r);	
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
		});
		
		$("#mojePoruke").click(function() {
			var korisnik = sessionStorage.getItem("korisnik");
			var user = JSON.parse(korisnik);
			console.log(user.korisnicko);
			
			$.ajax({
				url: 'rest/korisnici/getMojePoruke/'+user.korisnicko,
				type: 'get',
				success: function(poruke) {
					if(poruke==null){
						alert('Nema poruka!');
					}else {
						$('table#oglasi tbody td').remove();
						for(p of poruke){
								prikaziPoruke(p);	
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
		});
		
		$("#mojiOglasi").click(function() {
			$.ajax({
				url: 'rest/oglasi/getMojeOglase',
				type: 'get',
				success: function(ogl) {
					if(ogl==null){
					}else {
						$('table#oglasi tbody td').remove();
						for(ad of ogl){
								prikaziOglas3(ad);	
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
		});
		
		$( "#pregledOglasa" ).click(function() {
			$.ajax({
				url: 'rest/oglasi/',
				type: 'get',
				success: function(ogl) {
					if(ogl==null){
					}else {
						$('table#oglasi tbody td').remove();
						for(ad of ogl){
							if(ad.status==="aktivan"){
								prikaziOglas(ad);	
							}
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
			
		});
		
		$( "#deset" ).click(function() {
			$.ajax({
				url: 'rest/oglasi/getDesetOglasa',
				type: 'get',
				success: function(ogl) {
					if(ogl==null){
					}else {
						$('table#oglasi tbody td').remove();
						for(ad of ogl){
							odstampajOglas(ad);
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");	
					} 
				}
				
			});
			
		});
		
			$(document).on("click","#omiljeniOglasi",function(){
			$.ajax({
				url : 'rest/oglasi/getomiljene',
				type: 'get',
				contentType: 'application/json',
				success: function(ogl){
					if(ogl==null){
						alert('null oglasi');
					}
					
					$('table#oglasi tbody td').remove();
					for(ad of ogl){
						prikaziOglas(ad);	
					}
					$("#oglasi").load(pregled.html.href+" #oglasi>*","");
					
					
				}
			});
			
		});
			

			$(document).on("click","#poruceni",function(){
			$.ajax({
				url : 'rest/oglasi/getporucene',
				type: 'get',
				contentType: 'application/json',
				success: function(ogl){
					if(ogl==null){
						alert('null oglasi');
					}
					$('table#oglasi tbody td').remove();
					for(ad of ogl){
						prikaziPoruceniOglas(ad);	
					}
					$("#oglasi").load(pregled.html.href+" #oglasi>*","");
				}			
			});
			});
			
			$(document).on("click","#dostavljeni",function(){
				$.ajax({
					url : 'rest/oglasi/getdostavljene',
					type: 'get',
					contentType: 'application/json',
					success: function(ogl){
						if(ogl==null){
							alert('null oglasi');
						}
						$('table#oglasi tbody td').remove();
						for(ad of ogl){
							 prikaziDostavljeniOglas(ad);
						}
						$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						
					}
				});
			
		});
			

		
		$(document).on("click","#odjava",function(){
			console.log("usao u funkciju");
			sessionStorage.setItem("korisnik",null);
			$.ajax({
				method:'GET',
				url: "rest/korisnici/izloguj",
				success: function(user){
					window.location.href ='pregled.html';
				}
			});
		});
		

		
	});

function ispisiKategorije(kategorije){
	 var list = kategorije == null ? [] : (kategorije instanceof Array ? kategorije : [ kategorije ]);
		
	 $.each(kategorije, function(index, kategorija) {
		 if(kategorija.aktivna===true){
			 ispisiKategoriju(kategorija);
		 }
		 	
		
		 
	 });

	
}


//////////////pregled oglasa
function prikaziOglas(ad){
	let tr = $('<tr></tr>');
	let tr2 = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>'+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>'+ad.brLajkova+'</label>');
	let neg = $('<label>'+ad.brDislajkova+'</label> ');
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	td.append(a).append(b).append(c).append(d);
	
	let td4 = $('<td></td>');	
			var addToFavorite = $('<a href="#">Omiljeni</a>');
			addToFavorite.click(function(){
				$.post({
					url : 'rest/oglasi/addToFavorite/'+ad.naziv,
				success : function(){
					alert('Oglas dodat u omiljene!');
				},
				error : function(r){
					alert(r.responseText);
				}
				});
			});
		
		
			let addToCart = $('<a href="#">Dodaj u korpu</a>"');
			addToCart.click(function(){
				
				$.post({
					url : 'rest/oglasi/ukorpu/'+ad.naziv,
				success : function(){
					location.reload(true);
					alert('Oglas dodat u korpu!');
				},
				error : function(r){
					alert(r.responseText);
				}
			});
				
			});
			td4.append(addToCart);
		
		
		td4.append(addToFavorite);
	
	let plus = $('<a href="#">Like</a>');
	let minus =$('<a href="#">Dislike</a>');
	let prijavi = $('<a href="#">Prijavi</a>');
	
	td3.append(poz).append(plus);
	td3.append(neg).append(minus).append(prijavi);
	
	
	prijavi.click(function(){
		
		$.ajax({
			type : 'put',
			url : 'rest/oglasi/prijavi/'+ad.naziv,
			success : function(){
				alert('Oglas prijavljen!');
			},
			error : function(r){
				alert(r.responseText);
			}
		});
	});
	
		
		plus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/likedAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brLajkova = ad.brLajkova +1;
					window.location.reload(true);
				},
				error : function(r){
					alert(r.responseText);
				}
			});
		});
		
		
		minus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/dislikeAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brDislajkova = ad.brDislajkova + 1;
					window.location.reload(true); 
				},
				error: function(response){
					alert(response.responseText);
				}
			});
			
			
		});
		
		let td5=$('<td></td>');
		let slika= $('<img  height="42" width="42" />');
		slika.attr("src", ad.imgSlika);
		td5.append(slika);
		
		tr.append(td2).append(td4).append(td);
		tr2.append(td3).append(td5);
		$('table#oglasi tbody').append(tr);
		$('table#oglasi tbody').append(tr2);
						
}

function prikaziPoruceniOglas(ad){
	let tr = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>Naziv: '+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	let e =$('<p>Status: '+ad.status+'</p>');
	let prijavi = $('<a href="#">Prijavi</a>');
	td.append(a).append(b).append(c).append(d).append(e).append(prijavi);
	
prijavi.click(function(){
		
		$.ajax({
			type : 'put',
			url : 'rest/oglasi/prijavi/'+ad.naziv,
			success : function(){
				alert('Oglas prijavljen!');
			},
			error : function(r){
				alert(r.responseText);
			}
		});
	});
	
			let addToCart = $('<a href="#">Dostavljen</a>"');
			addToCart.click(function(){
				
				$.post({
					url : 'rest/oglasi/dostavljen/'+ad.naziv,
				success : function(){
					alert('Oglas dostavljen!');
				},
				error : function(r){
					alert(r.responseText);
				}
			});
				
			});
			td.append(addToCart);
			
			let td5=$('<td></td>');
			let slika= $('<img  height="42" width="42"/>');
			slika.attr("src", ad.imgSlika);
			td5.append(slika);
		
		tr.append(td2).append(td).append(td5);

		$('table#oglasi tbody').append(tr);
		
}

function prikaziDostavljeniOglas(ad){
	let tr = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>Naziv:'+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	let e =$('<p>Status: '+ad.status+'</p>');
	td.append(a).append(b).append(c).append(d).append(e);
	
	let td3 = $('<td></td>');
	let plus = $('<a href="#">Like prodavca</a>');
	let minus =$('<a href="#">Dislike prodavca</a>');
	let prijavi =$('<a href="#">Prijavi</a>');
	
	td3.append(plus).append(minus).append(prijavi);
	
prijavi.click(function(){
		
		$.ajax({
			type : 'put',
			url : 'rest/oglasi/prijavi/'+ad.naziv,
			success : function(){
				alert('Oglas prijavljen!');
			},
			error : function(r){
				alert(r.responseText);
			}
		});
	});
		
		plus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/likedProdavca/'+ad.vlasnik,
				success : function(){
					alert("Prodavac lajkovan!");
				},
				error : function(r){
					alert(r.responseText);
				}
			});
		});
		
		
		minus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/dislikeProdavca/'+ad.vlasnik,
				success : function(){
					alert("Prodavac dislajkovan!");
				},
				error: function(response){
					alert(response.responseText);
				}
			});
			
			
		});
		
		let td5=$('<td></td>');
		let slika= $('<img  height="42" width="42"/>');
		slika.attr("src", ad.imgSlika);
		td5.append(slika);
		
		tr.append(td2).append(td).append(td3).append(td5);

		$('table#oglasi tbody').append(tr);
		
}

function prikaziOglas2(ad){
	let tr = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>Naziv:'+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	let status = $('<p>Opis: '+ad.status+'</p>');
	td2.append(name).append(price).append(desc).append(status);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>Broj lajkova: '+ad.brLajkova+'</label>');
	let neg = $('<label>Broj dislajkova: '+ad.brDislajkova+'</label> ');
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	td.append(a).append(b).append(c).append(d);

	let td5=$('<td></td>');
	let slika= $('<img  height="42" width="42"/>');
	slika.attr("src", ad.imgSlika);
	td5.append(slika);
	
	td3.append(poz).append(neg);
		
		
		tr.append(td2).append(td3).append(td).append(td5);
		$('table#oglasi tbody').append(tr);
						
}

function prikaziOglas3(ad){
	let tr = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>Naziv: '+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>Broj lajkova: '+ad.brLajkova+'</label>');
	let neg = $('<label>Broj dislajkova: '+ad.brDislajkova+'</label> ');
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	td.append(a).append(b).append(c).append(d);

	td3.append(poz).append(neg);
	
	let tdObrisi = $('<td></td>');
	if(ad.daLiJeAktivam===true){
		let aObrisi = $('<a href="">Obrisi</a>');
		aObrisi.click(function(event){
			event.preventDefault();
			let id = ad.naziv;
			$.ajax({
				url:'rest/oglasi/deloglas/'+id,
				type:'PUT',
				success: function(){
					$.ajax({
						url : 'rest/oglasi/getMojeOglase',
						type:'get',
						success : function(accounts) {
							//removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								ispisiOglas(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aObrisi);
	}else{
		let aAktiviraj = $('<a href="">Aktiviraj</a>');
		aAktiviraj.click(function(event){
			event.preventDefault();
			let id = ad.naziv;
			$.ajax({
				url:'rest/oglasi/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/oglasi/getMojeOglase',
						success : function(accounts) {
							//removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								ispisiOglas(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}
	let td5=$('<td></td>');
	let slika= $('<img  height="42" width="42" />');
	slika.attr("src", ad.imgSlika);
	td5.append(slika);
		
		tr.append(td2).append(td3).append(td).append(tdObrisi).append(td5);
		$('table#oglasi tbody').append(tr);
						
}
function prikaziPoruke(o){
	let oglas = $('<td>Naziv oglasa:'+o.naziv_oglasa+'</td>');
	let posiljalac = $('<td>Posiljalac:'+o.posiljalac+'</td>');
	let naslov = $('<td>Naslov: '+o.naslov+'</td>');
	let sadrzaj = $('<td>Sadrzaj: '+o.sadrzaj+'</td>');
	let dat = $('<td>Datum: '+o.dat+'</td>');
	
	let tdObrisi = $('<td></td>');
	if(o.aktivna===true){
		let aObrisi = $('<a href="">Obrisi</a>');
		aObrisi.click(function(event){
			event.preventDefault();
			let id = o.naslov;
			$.ajax({
				url:'rest/poruke/del/'+id,
				type:'PUT',
				success: function(){
					$.ajax({
						url : 'rest/poruke',
						type:'get',
						success : function(accounts) {
						///////	removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								ispisiPoruku(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aObrisi);
	}else{
		let aAktiviraj = $('<a href="">Aktiviraj</a>');
		aAktiviraj.click(function(event){
			event.preventDefault();
			let id = o.naslov;
			$.ajax({
				url:'rest/poruke/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/poruke',
						success : function(accounts) {
						/////	removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								ispisiPoruku(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}

		
	let tr = $('<tr></tr>');
	tr.append(oglas).append(posiljalac).append(naslov).append(sadrzaj).append(dat).append(tdObrisi);
	$('table#oglasi tbody').append(tr);
}
function prikaziRecenzije(o){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	
	let naslov = $('<td>Naslov: '+o.naslov+'</td>');
	let recenzent = $('<td>Recenzent: '+o.recenzent+'</td>');
	let oglas = $('<td>Oglas: '+o.oglas+'</td>');
	let sadrzaj = $('<td>Sadrzaj: '+o.sadrzaj+'</td>');

	let tacan = $('<td>Opis tacan: '+o.daLiJeOpisTacan+'</td>');
	let ispostovan = $('<td>Dogovor ispostovan:'+o.daLiJeDogIspostovan+'</td>');

	let tdObrisi = $('<td></td>');
	if(o.aktivan===true){
		let aObrisi = $('<a href="">Obrisi</a>');
		aObrisi.click(function(event){
			event.preventDefault();
			let id = o.naslov;
			$.ajax({
				url:'rest/recenzije/del/'+id,
				type:'PUT',
				success: function(){
					$.ajax({
						url : 'rest/oglasi/getMojeRecenzije2',
						type:'get',
						success : function(accounts) {
						///////	removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								prikaziRecenzije(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aObrisi);
	}else{
		let aAktiviraj = $('<a href="">Aktiviraj</a>');
		aAktiviraj.click(function(event){
			event.preventDefault();
			let id = o.naslov;
			$.ajax({
				url:'rest/recenzije/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/oglasi/getMojeRecenzije2',
						success : function(accounts) {
						/////	removeAll();
							$('table#oglasi tbody td').remove();
							for(acc of accounts){
								prikaziRecenzije(acc);
							}
							$("#oglasi").load(pregled.html.href+" #oglasi>*","");
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}


	let tr = $('<tr></tr>');
	tr.append(naslov).append(oglas).append(sadrzaj).append(recenzent).append(tacan).append(ispostovan);
	if(user.uloga==="Kupac"){
		re.append(tdObrisi);
	}

	$('table#oglasi tbody').append(tr);
}


function odstampajOglas(ad){
	var korisnik = sessionStorage.getItem("korisnik");
	var user = JSON.parse(korisnik);
	let tr = $('<tr></tr>');
	let tr2 = $('<tr></tr>');
	
	let td2 = $('<td></td>');	
	let name = $('<p>'+ad.naziv+'</p>');
	let price = $('<p>Cijena: '+ad.cijena+' </p>');
	let desc = $('<p>Opis: '+ad.opis+'</p>');
	td2.append(name).append(price).append(desc);
	
	let td3 = $('<td></td>');	
	let poz = $('<label>Like:'+ad.brLajkova+'</label>');
	let neg = $('<label>Dislike: '+ad.brDislajkova+'</label> ');
	
	let poz1 = $('<label>'+ad.brLajkova+'</label>');
	let neg1= $('<label> '+ad.brDislajkova+'</label> ');
	
	let td = $('<td></td>');	
	let a = $('<p>Postavljen: '+ad.prviDat+'</p>');
	let b = $('<p>Istice: '+ad.drugiDat+' </p>');
	let c = $('<p>Grad: '+ad.grad+'</p>');
	let d = $('<p>Postavio: '+ad.vlasnik+'</p>');
	td.append(a).append(b).append(c).append(d);
	let prijavi = $('<a href="#">Prijavi</a>');
	
	let td5=$('<td></td>');
	let slika= $('<img  height="60" width="50"/>');
	slika.attr("src", ad.imgSlika);
	td5.append(slika);
	
prijavi.click(function(){
		
		$.ajax({
			type : 'put',
			url : 'rest/oglasi/prijavi/'+ad.naziv,
			success : function(){
				alert('Oglas prijavljen!');
			},
			error : function(r){
				alert(r.responseText);
			}
		});
	});
		
	
	let td4 = $('<td></td>');	
			var addToFavorite = $('<a href="#">Omiljeni</a>');
			addToFavorite.click(function(){
				$.post({
					url : 'rest/oglasi/addToFavorite/'+ad.naziv,
				success : function(){
					alert('Oglas dodat u omiljene!');
				},
				error : function(r){
					alert(r.responseText);
				}
				});
			});
		
		
			let addToCart = $('<a href="#">Dodaj u korpu</a>"');
			addToCart.click(function(){
				
				$.post({
					url : 'rest/oglasi/ukorpu/'+ad.naziv,
				success : function(){
					location.reload(true);
					alert('Oglas dodat u korpu!');
				},
				error : function(r){
					alert(r.responseText);
				}
			});
				
			});
	let plus = $('<a href="#">Like</a>');
	let minus =$('<a href="#">Dislike</a>');
	if(user != null){
		if(user.uloga==="Kupac"){
			td4.append(addToCart);
			td4.append(addToFavorite);
			td3.append(poz1).append(plus);
			td3.append(neg1).append(minus).append(prijavi);
		}else{
			td3.append(poz).append(neg);
		}
	}
	
		plus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/likedAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brLajkova = ad.brLajkova +1;
					window.location.reload(true);
				},
				error : function(r){
					alert(r.responseText);
				}
			});
		});
		
		
		minus.click(function(){
			
			$.ajax({
				type : 'put',
				url : 'rest/oglasi/dislikeAd/'+ad.naziv,
				success : function(){
					previousAdName = ad.naziv;
					ad.brDislajkova = ad.brDislajkova + 1;
					window.location.reload(true); 
				},
				error: function(response){
					alert(response.responseText);
				}
			});
			
			
		});
		
		tr.append(td2).append(td4).append(td).append(td5);
		tr2.append(td3);
		$('table#oglasi tbody').append(tr);
		$('table#oglasi tbody').append(tr2);
						
}

