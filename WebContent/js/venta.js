///////////////////////////////////////Metodos especial Venta Compra////////////////////////////////////////////////////
function actualizarCantidad(codigo, cantidad, form) {
	    	$('#codigo').val(codigo);
	    	$('#cantidad').val($(cantidad).val());
       		$('#accion').val(2);

       		if($('#datosprod').is(':visible')){
        		$('#datosprod').hide(500, function(){
        			form.submit();
        		}); 
        		
        	}else{
        		form.submit();
        	}
        }
        
        function borrarProducto(codigo, form){
        	$('#codigo').val(codigo);
        	$('#accion').val(3);

        	if($('#datosprod').is(':visible')){
        		$('#datosprod').hide(500, function(){
        			form.submit();
        		}); 
        		
        	}else{
        		form.submit();
        	}
        }
        
        function ejecutarAccion(action, form){
        	$('#accion').val(action);
        	
        	
        	if($('#datosprod').is(':visible')){
        		$('#datosprod').hide(500, function(){
        			form.submit();
        		}); 
        		
        	}else{
        		form.submit();
        	}
        	
        	
        	
        	
        }
	    
//////////////////////////////////////////////////////////////////////////////////////////////////////
	    
function autompletar(elemento, url){
	
	elemento
	.autocomplete(
			{
				source : url,
				select : function(a, b) {
					$(this).val(b.item.value);
				}
			});
	
}

function submitEnter(elemnto, url, sumar){
	elemnto.keypress(
			function(e) {

				if (e.which == 13) {
					if(sumar == true){
						url = url+$(this).val();
					}
					$(location).attr('href',url);
				}
			});
}

function editar(id, url) {
	url = url + id;
	$(location).attr('href', url);
}

function mostrar(elemento) {
	if (elemento.is(':visible'))
		elemento.hide('slow');
	else
		elemento.show('slow');
}

function solonumeros(elemento){
	
	$(elemento).click(function() {
		this.select();
		});
	
	elemento.keydown(function(event){
		if(event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 38 || event.keyCode == 40)
			return true;
		if(event.keyCode < 95){
		    if(event.keyCode < 48 || event.keyCode > 57)
		        return false;
		}else{
			if (event.keyCode < 96 || event.keyCode > 105) {
				return false;
	        }
		}
	    
	});	
}

function solodecimales(elemento){
	
	$(elemento).click(function() {
		this.select();
		});
	
	elemento.keydown(function(event){
		if(event.keyCode == 8 || event.keyCode == 110 || event.keyCode == 190 || event.keyCode == 9 || event.keyCode == 38 || event.keyCode == 40)
			return true;
		if(event.keyCode < 95){
		    if(event.keyCode < 48 || event.keyCode > 57)
		        return false;
		}else{
			if (event.keyCode < 96 || event.keyCode > 105) {
				return false;
	        }
		}
	    
	});	
}

function noescribir(elemento){
	elemento.keydown(function(event){
	        return false;
	    
	});	
}

function dosdecimales(numero){
	return parseFloat(Math.round(numero * 100) / 100).toFixed(2);
}

/////////////////////////////////////CARGAR
function cargarSubmit(elemnt, imgurl){
	 $.blockUI({ 
	 message: '<h2><img width="30px" height="30px" src='+ imgurl+'> &nbsp;&nbsp;Cargando... </h2>' ,
	 css: { 
           border: 'none', 
           backgroundColor: '#000', 
           '-webkit-border-radius': '10px', 
           '-moz-border-radius': '10px', 
           opacity: .5, 
           color: '#fff' 
       },
    onBlock: function() { 
       elemnt.submit(); 
       } 
    }); 
	
};
