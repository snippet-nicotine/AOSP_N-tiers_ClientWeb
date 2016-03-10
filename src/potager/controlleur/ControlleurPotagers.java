package potager.controlleur;

import java.io.IOException;
import java.util.HashMap;
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
import potager.clientServeur.ServiceGestionPotager;
import potager.config.Url;
import potager.entity.Potager;
import potager.service.exception.CPPotagerException;
import potager.service.exception.DaoPotagerAjoutException;
import potager.service.exception.DaoPotagerGetException;
import potager.service.exception.DaoPotagerModificationException;
import potager.service.exception.DaoPotagerQueryException;
import potager.service.exception.DaoPotagerSuppressionException;
import potager.service.exception.DimensionPotagerException;
import potager.service.exception.NomPotagerException;
import potager.service.exception.ProprietairePotagerException;
import utilisateur.entity.Jardinier;

/**
 * Servlet implementation class ControlleurPotagers
 * Voici les url patterns:
 * 			[GET]  /potagers/            => liste les potagers</br>
 * 			[GET]  /potagers/1           => affiche le potager 1</br>
 * 			[GET]  /potagers/1/ajouter   => affiche la page d'ajout</br>
 * 			[GET]  /potagers/1/modifier  => affiche la page de modification de potager 1</br>
 * 			[POST] /potagers/            => ajoute un potager</br>
 * 		    [POST] /potagers/1           => update le potager 1</br>
 * 			[POST] /potagers/1/supprimer => supprime le potager 1</br>
 */
@WebServlet("/ControlleurPotagers")
public class ControlleurPotagers extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LogManager.getLogger();
	
	private ServiceGestionPotager serviceGestionPotager;
	private Jardinier jardinier;
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		InitialContext initialContext;

		//TODO: Gérer le jardinier via une httpSession
		jardinier = new Jardinier();

		try {
			initialContext = new InitialContext();
			serviceGestionPotager = (ServiceGestionPotager) initialContext.lookup( Url.EJB_SERVICE_GESTION_POTAGER );
			
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
		
		// ..aosp/potagers/
		if( id.isEmpty() ) {
			
			afficherFormulaireGestion(request, response);
			
		}
		
		else{
			
			// ..aosp/potagers/:id/*
			int idPotager = 0;
			
			try{
				idPotager = Integer.parseInt(id);
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
			
			Potager potager;
			
			try {
				potager = serviceGestionPotager.getPotager(idPotager);
				
				switch(action){
				
				case "ajouter":
					refreshPage(request, response);
					break;
					
				case "modifier":
					refreshPage(request, response);
					break;
					
				default:
					afficherPotager(potager, request, response);
					break;
					
				}
				
			} catch (DaoPotagerGetException e) {
				
				afficherFormulaireGestion(request, response);
			}
			
		}
		
			
	}
	

	private void annuler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LOG.trace("annuler");
		
		try {
			
			serviceGestionPotager.annuler();
			
		} catch (DaoPotagerAjoutException | DaoPotagerModificationException e) {
			
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
		
		// ..aosp/potagers/
		if( id.isEmpty() )  {
			
			ajouterPotager(request, response);
			
		}
		
		else{
						
			// ..aosp/potagers/:id/*
			int idPotager = 0;
			
			try{
				idPotager = Integer.parseInt(id);
			}
			catch(Exception e){
				afficherFormulaireGestion(request, response);
			}
			
			Potager potager;
			try {
				potager = serviceGestionPotager.getPotager(idPotager);
				switch(action){
				
				case "modifier":
					modifierPotager(potager, request, response);
					break;
					
				case "supprimer":
					supprimerPotager(idPotager, request, response);
					break;
					
				default:
					afficherPotager(potager, request, response);
					break;				
					
				}
				
			} catch (DaoPotagerGetException e) {
				e.printStackTrace();
			}
			
			
		}
	
	}

	
	// --------------------
	
	private void supprimerPotager(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LOG.debug("id: " + id);
					
		try {
			serviceGestionPotager.supprimerPotager(id);
			
		} catch (DaoPotagerSuppressionException | DaoPotagerGetException e) {
			
			request.setAttribute("erreur", e.getMessage() );
			
		}			
		
		refreshPage(request,response);
		
	}

	private void modifierPotager(Potager potager, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		
		Potager potagerRequested;
		
		try {
			
			request.setAttribute("isModifier", false);
			
			potagerRequested = potagerFromRequest(request, "modifier-");
			potager.setNom( potagerRequested.getNom() );
			potager.setLongueur( potagerRequested.getLongueur() );
			potager.setLargeur( potagerRequested.getLargeur() );
			potager.setCodePostal( potagerRequested.getCodePostal() );
			
			serviceGestionPotager.modifierPotager(potager);
			
		} catch (NomPotagerException e) {
			request.setAttribute("erreurNom", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (CPPotagerException e) {
			
			request.setAttribute("erreurCodepostal", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (DimensionPotagerException e) {
			
			request.setAttribute("erreurDimension", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (ProprietairePotagerException e){
			request.setAttribute("erreurProprietaire", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
			
		} catch (DaoPotagerModificationException e) {
			request.setAttribute("erreurPersistence", e.getMessage() );
			request.setAttribute("isModifier", true);
			LOG.error( e.getMessage() );
		}
		
		LOG.debug("Modification de potager");
		
		afficherFormulaireGestion(request, response);
				
	}

	private void afficherPotager(Potager potager, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.getWriter().append("Afficher potager:" + potager);
		
	}


	
	private void afficherFormulaireAjouter(HttpServletRequest request, HttpServletResponse response) {
		// TODO Créer une jsp pour ajouter
		
	}

	private void afficherFormulaireModifier(Potager potager, HttpServletRequest request, HttpServletResponse response) {
		// TODO Créer une jsp pour modifier
		
	}

	private void afficherFormulaireGestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.trace(" GET potagers/  => Accéder à l'ihm de gestion de potager ");
		
		List<Potager> potagers;
		
		try {
			potagers = serviceGestionPotager.listerPotager();
			request.setAttribute("potagers", potagers);
			request.setAttribute("nbAnnulations", serviceGestionPotager.getNombreAnnulations() );
			
			LOG.debug("variable potagers: " + potagers);
			LOG.debug("request ContextPath: " + request.getContextPath() );
			LOG.debug("request param: " + request.getParameter("nom") );
			
		} catch (DaoPotagerQueryException e) {
			
			request.setAttribute("erreur", e.getMessage() );
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(Url.IHM_GESTION_POTAGER ).forward(request,response);
		
	}

	private void ajouterPotager(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		try {
			
			LOG.debug( "request param: " + request.getParameter("nom") );
			Potager potager = potagerFromRequest(request, "");			
			serviceGestionPotager.ajouterPotager(potager);
			
		} catch (NomPotagerException e) {
			request.setAttribute("erreurNom", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (CPPotagerException e) {
			
			request.setAttribute("erreurCodepostal", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (ProprietairePotagerException e) {
			
			request.setAttribute("erreurProprietaire", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (DimensionPotagerException e) {
			
			request.setAttribute("erreurDimension", e.getMessage() );
			LOG.error( e.getMessage() );
			
		} catch (DaoPotagerAjoutException e) {
			request.setAttribute("erreur", e.getMessage() );
		}
		
		afficherFormulaireGestion(request, response);

	}
	
	private Potager potagerFromRequest(HttpServletRequest request, String prefix) throws DimensionPotagerException, NomPotagerException, CPPotagerException{
				
		String nom        = request.getParameter(prefix + "nom");
		String codePostal = request.getParameter(prefix + "codePostal");
		Potager potager   = null;

		if( nom == null || nom.isEmpty() )      throw new NomPotagerException("Le nom doit être renseigné");
		if( codePostal == null || !checkCodePostal() ) throw new CPPotagerException("Le code postal doit comporté 5 chiffres");
				
		try{
			int longueur      = Integer.parseInt( request.getParameter(prefix + "longueur") );
			int largeur       = Integer.parseInt( request.getParameter(prefix + "largeur") );
			
			LOG.debug("serviceGestionPotager.creerPotager(" + nom + "," + longueur + "," + largeur + "," + codePostal + "," + jardinier + ")" );
			potager = new Potager(nom, longueur, largeur, codePostal, jardinier);
		}
		
		catch (NumberFormatException e){
			throw new DimensionPotagerException("La longueur et la largeur doivent être >50");
		}

		return potager;		
	}
	
	// TODO: Implémenter le check du codePostal niveau présentation
	private boolean checkCodePostal() {
		return true;
	}


	private void refreshPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
				
		response.sendRedirect(request.getContextPath() + "/aosp/potagers");

	}
	

}
