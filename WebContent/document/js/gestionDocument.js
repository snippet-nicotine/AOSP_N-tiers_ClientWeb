/*
 * author: Nicolas LAMBERT
 */


window.onload = function(){
	
	//Initialisation des tooltips
	$(function () {
		$('[data-toggle="tooltip"]').tooltip();
	});
	
	// Init du modal
	var isModalVisible = $("#modal-modifier").hasClass("visible");
	console.log(isModalVisible);
	if( isModalVisible ) {
		$('#modal-modifier').modal('show') ;    
	}
	
	gestionDocument.init();


}


var gestionDocument = {
		
		baseUrl: "/aosp/documents",
		form: null,
		self: this,
		
		init: function() {
			
			this.form         = $("#formulaire-ajouter");
			this.formModifier = $("#formulaire-modifier");
			this.initEvents();
			
		},
		
		initEvents : function(){
			
			var form = this.form;
			var obj  = this;
			
			$('.bouton-afficher-modifier').on('click', function(e){
				
				e.preventDefault();
				var ligne = $(this).parent().parent();
				
				$("#modifier-titre").val( ligne.data("documentTitre") );
				$("#modifier-descriptif").val( ligne.data("documentDescriptif") );
				$("#modifier-nbExemplaires").val( ligne.data("documentNbexemplaires") );
				$('#modal-modifier .bouton-modifier').data("buttonId", ligne.data("documentId") );
								
				$('#modal-modifier').modal('show');
				
			});
			
			$('.bouton-modifier').on('click', function(e){
								
				e.preventDefault();
				var id  = $(this).data("buttonId");
				obj.modifier(id);
				
				
			});
			
			$('.bouton-supprimer').on('click', function(e){
				
				e.preventDefault();
				var id  = $(this).data("buttonId");
				
				obj.supprimer(id);				
			});
			
		},
		
		modifier : function(id){
			
			var url =  "/" + id + "/modifier";
			this.submit(this.formModifier, url, "POST");
			
		},
		
		supprimer : function(id){
			
			var url =  "/" + id + "/supprimer";	
			this.submit(this.form, url, "POST");
			
		},
		
		submit: function(form, action, method){
			var url = form.attr("action") + action;
			form.attr("method", method);
			form.attr("action", url);
			
			form.submit();
		}
		
}



