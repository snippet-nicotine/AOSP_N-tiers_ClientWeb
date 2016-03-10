package document.config;

public class Url {
		
	public static final String CONTROLLEUR_GESTION_POTAGER = "potagers";
	public static final String IHM_GESTION_POTAGER         = "/potager/gestionPotager.jsp";
	
	public static final String CONTROLLEUR_GESTION_DOCUMENT = "documents";
	public static final String IHM_GESTION_DOCUMENT         = "/document/gestionDocument.jsp";
	
	public static final String CONTROLLEUR_GESTION_CARRE = "carres";
	public static final String IHM_GESTION_CARRE         = "/potagers/gestionCarre.jsp";
	
	public static final String EJB_SERVICE_GESTION_POTAGER  = "ejb:/AOSP_N-tiers_Serveur/FacadeServiceGestionPotager!potager.clientServeur.ServiceGestionPotager";
	public static final String EJB_SERVICE_GESTION_DOCUMENT = "ejb:/AOSP_N-tiers_Serveur/FacadeServiceGestionDocument!document.clientServeur.ServiceGestionDocument";

}
