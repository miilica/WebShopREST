function onLoad(){
	$.ajax({
		url: 'rest/poruke/',
		type: 'get',
		success: function(poruke) {
			if(poruke==null){
				alert('Nema oglasa, null');
			}else {
				ispisiPoruke(poruke);		
			} 
		}
		
	});
}

function ispisiPoruke(poruke){
	 var list = poruke == null ? [] : (poruke instanceof Array ? poruke : [ poruke ]);
		
	 $.each(poruke, function(index, o) {
		 ispisiPoruku(o);	 
	 });

	
}

function ispisiPoruku(o){
	
	let oglas = $('<td>'+o.naziv_oglasa+'</td>');
	let posiljalac = $('<td>'+o.posiljalac+'</td>');
	let naslov = $('<td>'+o.naslov+'</td>');
	let sadrzaj = $('<td>'+o.sadrzaj+'</td>');
	let dat = $('<td>'+o.dat+'</td>');
	
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
							removeAll();
							for(acc of accounts){
								ispisiPoruku(acc);
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
			let id = o.naslov;
			$.ajax({
				url:'rest/poruke/activate/'+id,
				type:'PUT',
				success: function(){
					$.get({
						url : 'rest/poruke',
						success : function(accounts) {
							removeAll();
							for(acc of accounts){
								ispisiPoruku(acc);
							}
						}
					});
				}
			});
		});
		tdObrisi.append(aAktiviraj);
	}
	let tr = $('<tr></tr>');
	tr.append(oglas).append(posiljalac).append(naslov).append(sadrzaj).append(dat).append(tdObrisi);
	$('table#tbPoruke tbody').append(tr);
	if(o.aktivna === true){
		let opt = $('<option>'+o.naslov+'</option>');
		$('select#aktPoruke').append(opt);
	}
	
	function removeAll() {
		$('table#tbPoruke tbody tr').remove();
		$('select#aktPoruke option').remove();
	}
	

	$(document).ready(function(){
		$('button#sacuvaj').click(function(){
			let naslov = $('input[name=naslov]').val();
			let sad = $('input[name=sad]').val();	
			let stariNaziv = $('select#aktPoruke').val();
			
			
			
				$.ajax({
					url: 'rest/poruke/izmjeni/'+stariNaziv+'/'+naslov+'/'+sad,
					type: 'PUT',
					success: function(){
						$.get({
							url : 'rest/poruke',
							success : function(accounts) {
								removeAll();
								for(acc of accounts){
									ispisiPoruku(acc);
								}
							}
						});
					}
				});
			
		});
	});
	
	 
}

