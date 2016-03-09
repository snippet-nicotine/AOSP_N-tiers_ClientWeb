package potager.controlleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ControlleurConnexion")
public class ControlleurConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		boolean isLogged = (boolean) session.getAttribute("isLogged");
		
		if( isLogged ){
			response.sendRedirect(request.getContextPath()+"/aosp/potagers");
		}
		else{
			response.sendRedirect(request.getContextPath()+"/commun/html/connexion.jsp");			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login    = request.getParameter("login");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		session.setAttribute("isLogged", false);
		
		if( !login.isEmpty() ){
			session.setAttribute("login", login);
			session.setAttribute("username", login);
			session.setAttribute("isLogged", true);			
		}
		
		System.out.println("session logged: " + session.getAttribute("isLogged"));
		
		doGet(request, response);
	}
	
	public static boolean isLogged(HttpServletRequest request){
		
		boolean isLogged = false;		
		HttpSession session = request.getSession(true);
		
		Object isLoggedObj = (Object) session.getAttribute("isLogged");
		
		if(isLoggedObj == null)	isLogged = false;
		else                    isLogged = (boolean) session.getAttribute("isLogged");		
		
		return isLogged;
		
	}

}
