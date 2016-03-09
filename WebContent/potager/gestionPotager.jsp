<%@page import="potager.entity.Potager"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/potager/css/bootstrap-3.3.6-dist/css/bootstrap.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/potager/css/style.css">
	
	
<title>Administration des potagers</title>
</head>
<body>
	
	<%@ include file="/WEB-INF/vuesPartielles/header.jsp"%>
	
	<div id="contenu" class="container-fluid">	
	
		<div class="row">
		
			<section id="titre" class="page-header">
			  <h1>Administration des potagers</h1>
			</header>		
		
			<div class="row">
				<a href="<%= request.getContextPath() %>/aosp/potagers/annuler" class="btn btn-danger">
					<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
					Annuler (${nbAnnulations})
			 	</a>
			 	<a href="<%= request.getContextPath() %>/aosp/message" class="btn btn-default">Demande d'arrosage</a> ( sur tout les potagers listés (Message) )		
			</div>
		
			<!-- Ajouter un potager -->
			<section id="ajouter">
				<form class="form-inline well" id="formulaire-ajouter" method="POST" action="<%= request.getContextPath() %>/aosp/potagers">
				  
				  <div class="form-group ${ erreurNom != null ? 'has-error' : '' }">
				    <label for="nom">Nom</label>
				    <input type="text" class="form-control" id="nom" 
				    		value="${param.nom}"
				    		name="nom" 
				    		placeholder="Nom" 
				    		data-toggle="tooltip" 
				    		title="${ erreurNom != null ? erreurNom : 'Entrez le nom du potager' }">
				  </div>
				  
				  <div class="form-group ${ erreurDimension != null ? 'has-error' : '' }">
				  
				    <label for="longueur">Dimension</label>
				    <input type="number" class="form-control" id="longueur"
				    		value="${param.longueur}"
				    		name="longueur" 
				    		placeholder="Longueur" 
				    		data-toggle="tooltip" 
				    		title="${ erreurDimension != null ? erreurDimension : 'Entrez la longueur en cm' }">
				    X
				    <input type="number" class="form-control" id="largeur" 
				    		value="${param.largeur}"
				    		name="largeur" 
				    		placeholder="Largeur" 
				    		data-toggle="tooltip" 
				    		title="${ erreurDimension != null ? erreurDimension : 'Entrez la largeur en cm' }">
				    
				    
				  </div>
				  
				  <div class="form-group ${ erreurCodepostal != null ? 'has-error' : '' }">
				    <label for="codePostal">Code Postal</label>
				    <input type="text" class="form-control" id="codePostal" 
				    		value="${param.codePostal}"
				    		name="codePostal"
				    		placeholder="Code Postal"
				    		data-toggle="tooltip"
				    		title="${ erreurCodepostal != null ? erreurCodepostal : 'Entrez le code postal (5 chiffres)' }">
				  </div>
				  		  
				  <button type="submit" class="btn btn-default">+ Ajouter</button>
				  
				</form>
			</section>
			
			<section id="lister">
			
				<table class="table table-hover table-striped table-bordered">
				
					<thead>
						<th>Nom</th>
						<th>Dimension</th>
						<th>Code Postal</th>
						<th>Propriétaire</th>
						<th>Actions</th>
						
					</thead>
						
					<tbody>
						<c:forEach items="${potagers}" var="potager">
							<tr 
								data-potager-id="${potager.idPotager}"
								data-potager-nom="${potager.nom}"
								data-potager-longueur="${potager.longueur}"
								data-potager-largeur="${potager.largeur}"
								data-potager-codePostal="${potager.codePostal}"
							>				
								
								<td><a href="<c:url value="/aosp/potagers/${potager.idPotager}"/>"> 
									${potager.nom} </a>
								</td>
								
								<td>${potager.largeur} x ${potager.longueur}</td>
								
								<td>${potager.codePostal}</td>
								
								<td> <a href="#" > ${potager.proprietaireNom} </a> </td>
								
								<td>
									<a class="bouton-afficher-modifier"  href="#">
										<i class="glyphicon glyphicon-pencil"></i>
									</a>
									
									<a data-button-id="${potager.idPotager}" class="bouton-supprimer" href="#">
										<i  class="glyphicon glyphicon-remove"></i>
									</a>
									
								</td>
							</tr>
						</c:forEach>
						
					</tbody>
							
				</table>
			
			</section>
		</div>
	</div>

	<!-- Modifier le potager -->
	<div class="modal fade ${ isModifier ? 'visible' : '' }" id="modal-modifier" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modifier le potager</h4>
				</div>
				<div class="modal-body">
				
				<form class="form well" id="formulaire-modifier" method="POST" action="<%= request.getContextPath() %>/aosp/potagers">
		  
				  <div class="form-group ${ erreurNom != null ? 'has-error' : '' }">
				    <label for="nom">Nom</label>
				    <input type="text" class="form-control" id="modifier-nom" name="modifier-nom" placeholder="Nom"
				    		data-toggle="tooltip" 
				    		title="${ erreurNom != null ? erreurNom : 'Entrez le nom du potager' }">
				  </div>
				  
				  <div class="form-group ${ erreurDimension != null ? 'has-error' : '' }">
				  
				    <label for="longueur">Dimension</label>
				    <input type="number" class="form-control" id="modifier-longueur" name="modifier-longueur" placeholder="Longueur"
				    		data-toggle="tooltip" 
				    		title="${ erreurDimension != null ? erreurDimension : 'Entrez la largeur du potager' }">				    
				    <input type="number" class="form-control" id="modifier-largeur"  name="modifier-largeur"  placeholder="Largeur"
				    		data-toggle="tooltip" 
			    			title="${ erreurDimension != null ? erreurDimension : 'Entrez la longueur du potager' }">
				    
				  </div>
				  
				  <div class="form-group ${ erreurCodepostal != null ? 'has-error' : '' }">
				    <label for="codePostal">Code Postal</label>
				    <input type="text" class="form-control" id="modifier-codePostal" name="modifier-codePostal" placeholder="Code Postal"
				    		data-toggle="tooltip" 
			    			title="${ erreurCodepostal != null ? erreurCodepostal : 'Entrez le code postal du potager (5 chiffres)' }">
				  </div>				  		  				  
				</form>
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					<button data-button-id="" name="modifier" type="button" class="bouton-modifier btn btn-primary">Mettre à jour</button>
				</div>
			</div>
		</div>
	</div>
	

</body>

	<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/potager/css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/potager/js/gestionPotager.js"></script>

</html>