package document.clientServeur;

import java.util.List;

import document.entity.Document;
import document.entity.Localisation;
import document.service.exception.DaoDocumentAjoutException;
import document.service.exception.DaoDocumentGetException;
import document.service.exception.DaoDocumentModificationException;
import document.service.exception.DaoDocumentQueryException;
import document.service.exception.DaoDocumentSuppressionException;
import document.service.exception.DaoLocalisationException;
import document.service.exception.DocumentException;

public interface ServiceGestionDocument {
	
	public Document ajouterDocument(Document document)
			throws DocumentException, DaoDocumentAjoutException;;
	
	public Document creerDocument(String titre, String descriptif, int nbExemplairesDispo, Localisation localisation) 
			throws DocumentException, DaoDocumentAjoutException;
	
	public Document getDocument(int idDocument) throws DaoDocumentGetException;
	
	public Document modifierDocument(Document document) throws DaoDocumentModificationException, DocumentException;
	
	public void    supprimerDocument(int idDocument) throws DaoDocumentSuppressionException, DaoDocumentGetException;
	
	public List<Localisation> listerLocalisation() throws DaoLocalisationException;
	
	public void annuler() throws DaoDocumentAjoutException, DaoDocumentModificationException;
	
	public int getNombreAnnulations();
	
	/**
	 * Renvoie tout les documents de l'application
	 * @return
	 * @throws DaoDocumentQueryException 
	 */
	public List<Document> listerDocument() throws DaoDocumentQueryException;

	
}
