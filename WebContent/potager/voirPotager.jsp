<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ include file="/WEB-INF/vuesPartielles/css.jsp"%>	
	<title>Potager: ${potager.idPotager}</title>
</head>

	<body>
	
		<%@ include file="/WEB-INF/vuesPartielles/header.jsp"%>
	
		<div id="contenu" class="container-fluid">	
	
			<div class="row">		
				<section id="titre" class="page-header">
				  <h1>${potager.nom}</h1>
				</section>
			</div>
				
				<section id="contenu">
					
				</section>				
				
		</div>
		
	</body>

</html>