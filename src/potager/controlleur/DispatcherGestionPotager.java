package potager.controlleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import potager.config.Url;

/**
 * Servlet implementation class DispatcherGestionPotager
 */
@WebServlet(		
		name        = "/DispatcherGestionPotager",
		urlPatterns = "/aosp/*"
		)
public class DispatcherGestionPotager extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String nomControlleur = getControlleur(request);
		String id             = getId(request);
		String action         = getAction(request);
		
		request.setAttribute("id", id);
		request.setAttribute("action", action);
			
		switch(nomControlleur){		
		
			case Url.CONTROLLEUR_GESTION_POTAGER:			
				request.getRequestDispatcher("/ControlleurPotagers").forward(request, response);
				break;
			
			case Url.CONTROLLEUR_GESTION_CARRE:
				request.getRequestDispatcher("/ControlleurCarres").forward(request, response);
				break;
				
			case "connexion":
				request.getRequestDispatcher("/ControlleurConnexion").forward(request, response);		
				break;
			
			case "logout":
				session.invalidate();
				response.sendRedirect(request.getContextPath() + "/commun/html/connexion.jsp");	
				break;
			
			case "message":
				request.getRequestDispatcher("/PotagerEnvoiMessage").forward(request, response);	
				break;

			default:
				
				boolean isLogged = ControlleurConnexion.isLogged(request);		

				if( isLogged )  response.sendRedirect(request.getContextPath() + "/aosp/potagers");
				else      		response.sendRedirect(request.getContextPath() + "/commun/html/connexion.jsp");					
				
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		doGet(request, response);
	}
	
	/**
	 * Permet de r�cup�rer le controlleur demand� � partir d'une request. </br>
	 * Le pathInfo du request doit �tre de la forme:
	 * <i> /:controlleur/* </i>
	 * @param request
	 * @return
	 */
	private String getControlleur(HttpServletRequest request){
		
		String result = "";
		String pathInfos = request.getPathInfo();
		
		if(pathInfos != null){
			
			String[] pathInfosTable = pathInfos.split("/");
			
			//On r�cup�re la premi�re occurence apr�s le slash: /occurence1/... => occurence1
			//
			if( pathInfosTable.length > 1 )	result = pathInfosTable[1];
			
		}
		
		return result;		
	}
	
	/**
	 * Permet de r�cup�rer l'id de la ressource demand�e � partir d'une request. </br>
	 * Le pathInfo du request doit �tre de la forme:
	 * <i> /:controlleur/:id </i>
	 * @param request
	 * @return l'id de la resource demand�e
	 */
	private String getId(HttpServletRequest request){
		
		String result = "";
		String pathInfos = request.getPathInfo();
		
		if(pathInfos != null){
			
			String[] pathInfosTable = pathInfos.split("/");
			
			if( pathInfosTable.length > 2 )	result = pathInfosTable[2];
			
		}
		
		return result;		
	}
	
	/**
	 * Permet de r�cup�rer l'action de la ressource demand�e � partir d'une request. </br>
	 * Le pathInfo du request doit �tre de la forme:
	 * <i> /:controlleur/:id/:action </i>
	 * @param request
	 * @return l'id de la resource demand�e
	 */
	private String getAction(HttpServletRequest request){
		
		String result = "";
		String pathInfos = request.getPathInfo();
		
		if(pathInfos != null){
			
			String[] pathInfosTable = pathInfos.split("/");
			
			if( pathInfosTable.length > 3 )	result = pathInfosTable[3];
			
		}
		
		return result;		
	}
	

}
