function onLoad(){
	$.ajax({
		url: 'rest/kategorije/',
		type: 'get',
		success: function(kategorije) {
			if(kategorije==null){
			}else {
				ispisiKategorije(kategorije);		
			} 
		}
		
	});
}

$(document).ready(function(){
	$('button#sacuvaj').click(function(){
		let naziv = $('input[name=naziv]').val();
		let opis = $('input[name=opis]').val();
		let stariNaziv = $('select#aktKat').val();
		let flag = true;
		if(naziv==null || naziv===""){
			let td=$('<td>Popunite ovo polje!</td>');
			let tr = $($('table#izmjena tbody tr')[1]);
			tr.append(td);
			flag = false;
		}
		if(opis==null || opis===""){
			let td=$('<td>Popunite ovo polje!</td>');
			let tr = $($('table#izmjena tbody tr')[2]);
			tr.append(td);
			flag = false;
		}
		if(flag){
			$.ajax({
				url: 'rest/kategorije/izmjeni/'+stariNaziv+'/'+naziv+'/'+opis,
				type: 'PUT',
				success: function(){
					$.get({
						url : 'rest/kategorije',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiKAT(acc);
							}
						}
					});
				}
			});
		}
	});
});


function ispisiKategorije(kategorije){
	 var list = kategorije == null ? [] : (kategorije instanceof Array ? kategorije : [ kategorije ]);
		
	 $.each(kategorije, function(index, k) {
		 
		// if(k.aktivna===true){
		 ispisiKAT(k);
		// }
	 
	 });

	
}

function ispisiKAT(k){
	
	let naziv = $('<td>'+k.naziv+'</td>');
	let opis = $('<td>'+k.opis+'</td>');
	let lista = $('<ul></ul>');
	ispisiOg(lista,k.naziv);
	let aktivan = $('<td>'+k.ak+'</td>');
	let tdObrisi = $('<td></td>');
	if(k.ak==="Da"){
		let aObrisi = $('<a href="">Obrisi</a>');
		aObrisi.click(function(event){
			event.preventDefault();
			let id = k.naziv;
			$.ajax({
				url:'rest/kategorije/delkategoriju/'+id,
				type:'PUT',
				success: function(){
					$.ajax({
						url : 'rest/kategorije',
						type:'get',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiKAT(acc);
							}
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
			let id = k.naziv;
			$.ajax({
				url:'rest/kategorije/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/kategorije',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiKAT(acc);
							}
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}
	let tr = $('<tr></tr>');
	tr.append(naziv).append(opis).append(lista).append(aktivan).append(tdObrisi);
	$('table#tbKategorije tbody').append(tr);
	if(k.aktivna === true){
		let opt = $('<option>'+k.naziv+'</option>');
		$('select#aktKat').append(opt);
	}
	
	 
}

function removeAll() {
	$('table#tbKategorije tbody tr').remove();
	$('select#aktKat option').remove();
}
function ispisiOg(lista,id){
	$.ajax({
		url:'rest/kategorije/getOgluKat/'+id,
		type:'get',
		success: function(ogl) {
			if(ogl==null){
			}else {
				 var list = ogl == null ? [] : (ogl instanceof Array ? ogl : [ ogl ]);
					
				 $.each(ogl, function(index, k) {
					 lista.append('<li>'+k.naziv+'</li>');
				 });
			} 
		}
		
	});
}

