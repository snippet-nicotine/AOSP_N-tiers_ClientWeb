package document.controlleur;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controlleur.ControlleurConnexion;
import document.clientServeur.ServiceGestionDocument;
import document.config.Url;
import document.entity.Document;
import document.service.exception.DaoDocumentAjoutException;
import document.service.exception.DaoDocumentGetException;
import document.service.exception.DaoDocumentModificationException;
import document.service.exception.DaoDocumentQueryException;
import document.service.exception.DaoDocumentSuppressionException;
import document.service.exception.DescriptionDocumentException;
import document.service.exception.ExemplairesDocumentException;
import document.service.exception.TitreDocumentException;

/**
 * Servlet implementation class ControlleurDocuments
 * Voici les url patterns:
 * 			[GET]  /documents/            => liste les documents</br>
 * 			[GET]  /documents/1           => affiche le document 1</br>
 * 			[GET]  /documents/1/ajouter   => affiche la page d'ajout</br>
 * 			[GET]  /documents/1/modifier  => affiche la page de modification de document 1</br>
 * 			[POST] /documents/            => ajoute un document</br>
 * 		    [POST] /documents/1           => update le document 1</br>
 * 			[POST] /documents/1/supprimer => supprime le document 1</br>
 */
@WebServlet("/ControlleurDocuments")
public class ControlleurDocuments extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LogManager.getLogger();
	
	private ServiceGestionDocument serviceGestionDocument;
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		InitialContext initialContext;

		try {
			initialContext = new InitialContext();
			serviceGestionDocument = (ServiceGestionDocument) initialContext.lookup( Url.EJB_SERVICE_GESTION_DOCUMENT );
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		boolean isLogged = ControlleurConnexion.isLogged(request);		
		if(!isLogged) response.sendRedirect(request.getContextPath() + "/commun/html/connexion.jsp");
		
		String id     = (String) request.getAttribute("id");
		String action = (String) request.getAttribute("action");
		
		// ..aosp/documents/
		if( id.isEmpty() ) {
			
			afficherFormulaireGestion(request, response);
			
		}
		
		else{
			
			// ..aosp/documents/:id/*
			int idDocument = 0;
			
			try{
				idDocument = Integer.parseInt(id);
			}
			catch(Exception e){
				
				switch(id){
					case "annuler":
						annuler(request, response);
						break;
					
					default:
						afficherFormulaireGestion(request, response);	
						break;
				}
				
			}
			
			Document document;
			
			try {
				document = serviceGestionDocument.getDocument(idDocument);
				
				switch(action){
				
				case "ajouter":
					refreshPage(request, response);
					break;
					
				case "modifier":
					refreshPage(request, response);
					break;
					
				default:
					afficherDocument(document, request, response);
					break;
					
				}
				
			} catch (DaoDocumentGetException e) {
				
				afficherFormulaireGestion(request, response);
			}
			
		}
		
			
	}
	

	private void annuler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LOG.trace("annuler");
		
		try {
			
			serviceGestionDocument.annuler();
			
		} catch (DaoDocumentAjoutException | DaoDocumentModificationException e) {
			
			request.setAttribute("erreurs", e.getMessage() );
			LOG.error( e.getMessage() );
		}
		
		refreshPage(request, response);
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		boolean isLogged = ControlleurConnexion.isLogged(request);		
		if(!isLogged) response.sendRedirect(request.getContextPath() + "/commun/html/connexion.jsp");
		
		String id     = (String) request.getAttribute("id");
		String action = (String) request.getAttribute("action");
		
		// ..aosp/documents/
		if( id.isEmpty() )  {
			
			ajouterDocument(request, response);
			
		}
		
		else{
						
			// ..aosp/documents/:id/*
			int idDocument = 0;
			
			try{
				idDocument = Integer.parseInt(id);
			}
			catch(Exception e){
				afficherFormulaireGestion(request, response);
			}
			
			Document document;
			try {
				document = serviceGestionDocument.getDocument(idDocument);
				switch(action){
				
				case "modifier":
					modifierDocument(document, request, response);
					break;
					
				case "supprimer":
					supprimerDocument(idDocument, request, response);
					break;
					
				default:
					afficherDocument(document, request, response);
					break;				
					
				}
				
			} catch (DaoDocumentGetException e) {
				e.printStackTrace();
			}
			
			
		}
	
	}

	
	// --------------------
	
	private void supprimerDocument(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LOG.debug("id: " + id);
					
		try {
			serviceGestionDocument.supprimerDocument(id);
			
		} catch (DaoDocumentSuppressionException | DaoDocumentGetException e) {
			
			request.setAttribute("erreur", e.getMessage() );
			
		}			
		
		refreshPage(request,response);
		
	}

	private void modifierDocument(Document document, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		
		Document documentRequested;
		
		try {
			
			request.setAttribute("isModifier", false);
			
			LOG.debug("modifier doc");
						
			documentRequested = documentFromRequest(request, "modifier-");
			documentRequested.setId( document.getId() );
			
			LOG.debug("documentRequested: " + documentRequested);		
			
			serviceGestionDocument.modifierDocument(documentRequested);
			
		} catch (TitreDocumentException e) {
			request.setAttribute("erreurTitre", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (DescriptionDocumentException e) {
			
			request.setAttribute("erreurDescriptif", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (DaoDocumentModificationException e) {
			request.setAttribute("erreurPersistence", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (ExemplairesDocumentException e) {
			request.setAttribute("erreurExemplaires", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
		}
		
		LOG.debug("Modification de document");
		
		afficherFormulaireGestion(request, response);
				
	}

	private void afficherDocument(Document document, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.getWriter().append("Afficher document:" + document);
		
	}


	
	private void afficherFormulaireAjouter(HttpServletRequest request, HttpServletResponse response) {
		// TODO Créer une jsp pour ajouter
		
	}

	private void afficherFormulaireModifier(Document document, HttpServletRequest request, HttpServletResponse response) {
		// TODO Créer une jsp pour modifier
		
	}

	private void afficherFormulaireGestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.trace(" GET documents/  => Accéder à l'ihm de gestion de document ");
		
		List<Document> documents;
		
		try {
			documents = serviceGestionDocument.listerDocument();
			request.setAttribute("documents", documents);
			request.setAttribute("nbAnnulations", serviceGestionDocument.getNombreAnnulations() );
			
			LOG.debug("variable documents: " + documents);
			LOG.debug("request ContextPath: " + request.getContextPath() );
			LOG.debug("request param: " + request.getParameter("titre") );
			
		} catch (DaoDocumentQueryException e) {
			
			request.setAttribute("erreur", e.getMessage() );
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(Url.IHM_GESTION_DOCUMENT ).forward(request,response);
		
	}

	private void ajouterDocument(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		try {
			
			Document document = documentFromRequest(request, "");			
			serviceGestionDocument.ajouterDocument(document);
			
		} catch (TitreDocumentException e) {
			request.setAttribute("erreurTitre", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (DescriptionDocumentException e) {
			
			request.setAttribute("erreurDescription", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (ExemplairesDocumentException e) {
			
			request.setAttribute("erreurExemplaires", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (DaoDocumentAjoutException e) {
			request.setAttribute("erreur", e.getMessage() );
		}
		
		afficherFormulaireGestion(request, response);

	}
	
	private Document documentFromRequest (HttpServletRequest request, String prefix) throws ExemplairesDocumentException, TitreDocumentException, DescriptionDocumentException{
				
		String titre          = request.getParameter(prefix + "titre");
		String descriptif     = request.getParameter(prefix + "descriptif");
		String sNbExemplaires = request.getParameter(prefix + "nbExemplaires");
		
		LOG.debug("titre: " + titre + "descriptif: " + descriptif + "sNbExemplaires: " + sNbExemplaires);
		
		Document document   = null;

		if( titre      == null || titre.isEmpty() )    throw new TitreDocumentException("Le titre doit être renseigné");
		
		try{
			int nbExemplaires = Integer.parseInt( request.getParameter(prefix + "nbExemplaires") );
			
			if(nbExemplaires < 0) throw new NumberFormatException("");
			
			document = new Document(titre, descriptif, nbExemplaires);
		}
		
		catch (NumberFormatException e){
			throw new ExemplairesDocumentException("Le nombre d'exemplaires doit être >= 0");
		}

		return document;		
	}
	
	// TODO: Implémenter le check du codePostal niveau présentation
	private boolean checkCodePostal() {
		return true;
	}


	private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
				
		response.sendRedirect(request.getContextPath() + "/aosp/documents");

	}
	

}
