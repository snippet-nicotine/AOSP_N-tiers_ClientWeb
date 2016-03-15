<%@page import="document.entity.Document"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">

<html>
<head>

	<%@ include file="/WEB-INF/vuesPartielles/css.jsp"%>
	
	<title>Administration des documents</title>
	
</head>

<body>
	
	<%@ include file="/WEB-INF/vuesPartielles/header.jsp"%>
	
	<div id="contenu" class="container-fluid">	
	
		<div class="row">
		
			<section id="titre" class="page-header">
			  <h1>Administration des documents</h1>
			</header>		
				
			<!-- Ajouter un document -->
			<section id="ajouter">
				<form class="form-inline well" id="formulaire-ajouter" method="POST" action="<%= request.getContextPath() %>/aosp/documents">
				  
				  <div class="form-group ${ erreurTitre != null ? 'has-error' : '' }">
				    <label for="titre">Titre</label>
				    <input type="text" class="form-control" id="titre" 
				    		value="${param.titre}"
				    		name="titre" 
				    		placeholder="Titre" 
				    		data-toggle="tooltip" 
				    		title="${ erreurTitre != null ? erreurTitre : 'Entrez le titre du document' }">
				  </div>
				  
				  <div class="form-group ${ erreurDescriptif != null ? 'has-error' : '' }">
				    <label for="titre">Descriptif</label>
				    <input type="text" class="form-control" id="descriptif" 
				    		value="${param.descriptif}"
				    		name="descriptif" 
				    		placeholder="Descriptif" 
				    		data-toggle="tooltip" 
				    		title="${ erreurDescriptif != null ? erreurDescriptif : 'Entrez la description du document' }">
				  </div>
				  				  
				<div class="form-group ${ erreurExemplaires != null ? 'has-error' : '' }">
				    <label for="titre">Nombre d'exemplaires</label>
				    <input type="text" class="form-control" id="nbExemplaires" 
				    		value="${param.nbExemplaires}"
				    		name="nbExemplaires" 
				    		placeholder="Nombre d'exemplaires" 
				    		data-toggle="tooltip" 
				    		title="${ erreurExemplaires != null ? erreurExemplaires : 'Entrez la description du document' }">
				  </div>
				  
				  <div class="form-group ${ erreurLocalisation != null ? 'has-error' : '' }">
				    <label for="localisation">Localisation</label>
				    <select name="localisation">
				    	<c:forEach items="${localisations}" var="localisation">			    
					 		 <option>${localisation.lieu}</option>
					  	</c:forEach>
					</select>
				  </div>
				  		  
				  <button type="submit" class="btn btn-default">+ Ajouter</button>
				  
				</form>
			</section>
			
			<section id="lister">
			
				<table class="table table-hover table-striped table-bordered">
				
					<thead>
						<th>Titre</th>
						<th>Descriptif</th>
						<th>Exemplaires disponibles</th>
						<th>Actions</th>
						
					</thead>
						
					<tbody>
						<c:forEach items="${documents}" var="document">
							<tr 
								data-document-id="${document.id}"
								data-document-titre="${document.titre}"
								data-document-descriptif="${document.descriptif}"
								data-document-nbexemplaires="${document.nbExemplairesDispo}"
							>				
								
								<td><a href="<c:url value="/aosp/documents/${document.id}"/>"> 
									${document.titre} </a>
								</td>
								
								<td>${document.descriptif}</td>
								
								<td>${document.nbExemplairesDispo}</td>
																
								<td>
									<a class="bouton-afficher-modifier"  href="#">
										<i class="glyphicon glyphicon-pencil"></i>
									</a>
									
									<a data-button-id="${document.id}" class="bouton-supprimer" href="#">
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

	<!-- Modifier le document -->
	<div class="modal fade ${ isModifier ? 'visible' : '' }" id="modal-modifier" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modifier le document</h4>
				</div>
				<div class="modal-body">
				
				<form class="form well" id="formulaire-modifier" method="POST" action="<%= request.getContextPath() %>/aosp/documents">
		  
				 <div class="form-group ${ erreurTitre != null ? 'has-error' : '' }">
				    <label for="modifier-titre">Titre</label>
				    <input type="text" class="form-control" id="modifier-titre" 
				    		value="${param.titre}"
				    		name="modifier-titre" 
				    		placeholder="Titre" 
				    		data-toggle="tooltip" 
				    		title="${ erreurTitre != null ? erreurTitre : 'Entrez le titre du document' }">
				  </div>
				  
				  <div class="form-group ${ erreurDescriptif != null ? 'has-error' : '' }">
				    <label for="modifier-descriptif">Descriptif</label>
				    <input type="text" class="form-control" id="modifier-descriptif" 
				    		value="${param.descriptif}"
				    		name="modifier-descriptif" 
				    		placeholder="Descriptif" 
				    		data-toggle="tooltip" 
				    		title="${ erreurDescriptif != null ? erreurDescriptif : 'Entrez la description du document' }">
				  </div>
				  				  
				<div class="form-group ${ erreurExemplaires != null ? 'has-error' : '' }">
				    <label for="modifier-nbExemplaires">Nombre d'exemplaires</label>
				    <input type="text" class="form-control" id="modifier-nbExemplaires" 
				    		value="${param.nbExemplaires}"
				    		name="modifier-nbExemplaires" 
				    		placeholder="Nombre d'exemplaires" 
				    		data-toggle="tooltip" 
				    		title="${ erreurExemplaires != null ? erreurExemplaires : 'Entrez la description du document' }">
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
	<script src="<%=request.getContextPath()%>/document/css/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/document/js/gestionDocument.js"></script>

</html>