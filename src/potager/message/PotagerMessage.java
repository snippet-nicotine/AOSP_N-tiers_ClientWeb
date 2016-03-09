package potager.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import potager.clientServeur.ServiceGestionPotager;
import potager.config.Url;
import potager.entity.Potager;
import potager.service.exception.DaoPotagerQueryException;


@WebServlet("/PotagerEnvoiMessage")
public class PotagerMessage extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TopicConnection	   connection 	= null;
	private TopicSession 	   session 	    = null;
	private TopicPublisher     sender       = null;
	
	private ServiceGestionPotager serviceGestionPotager;

	@Override
	public void init() throws ServletException {

		super.init();
		
		InitialContext initialContext;
		
		try {
			initialContext = new InitialContext();
			serviceGestionPotager = (ServiceGestionPotager) initialContext.lookup( Url.EJB_SERVICE_GESTION_POTAGER );
			
		} catch (NamingException e) {
			e.printStackTrace();
		}

		final String destinationName = "/ConnectionFactory";
		final String JMS_QUEUE_JNDI	 = "/jboss/exported/jms/topic/TopicPotager"; 
		final String JMS_USERNAME	 = "jmsuser";       
		final String JMS_PASSWORD	 = "jmsuser@123"; 

		try {         
			Context ctx = new InitialContext();
			TopicConnectionFactory connectionFactory = (TopicConnectionFactory) ctx.lookup(destinationName); 
			connection  = connectionFactory.createTopicConnection(JMS_USERNAME, JMS_PASSWORD); 
			session 	= connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic queue = (Topic) ctx.lookup(JMS_QUEUE_JNDI);
			sender 	= session.createPublisher(queue);
			connection.start();
		} 
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out 	= response.getWriter();
		TextMessage message;
		
		out.println("TOPIC");
		
		try {
			
			List<Potager> potagers = serviceGestionPotager.listerPotager();
			
			for(Potager potager : potagers){
				message = session.createTextMessage(potager.getNom());
				sender.send(message);				
			}
			out.println("Les demandes d'arrosage ont été envoyé");
			
		} catch (JMSException | DaoPotagerQueryException e) {
			e.printStackTrace();
		}

		out.println("Lorsqu'un jardinier sera disponible, il arrosera le potager");

	}
	
	@Override
	protected void finalize() throws Throwable {
		if (connection != null)   {
			try {
				connection.close();
			} catch (JMSException e) {                    
				e.printStackTrace();
			}
		} 
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
