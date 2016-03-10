package document.clientServeur;

import java.util.List;

import document.entity.Document;
import document.service.exception.DaoDocumentAjoutException;
import document.service.exception.DaoDocumentGetException;
import document.service.exception.DaoDocumentModificationException;
import document.service.exception.DaoDocumentQueryException;
import document.service.exception.DaoDocumentSuppressionException;
import document.service.exception.DescriptionDocumentException;
import document.service.exception.ExemplairesDocumentException;
import document.service.exception.TitreDocumentException;

public interface ServiceGestionDocument {
	
	public Document ajouterDocument(Document document)
			throws TitreDocumentException, DescriptionDocumentException,  DaoDocumentAjoutException;;
	
	public Document creerDocument(String titre, String descriptif, int nbExemplairesDispo) 
			throws TitreDocumentException, DescriptionDocumentException, ExemplairesDocumentException, DaoDocumentAjoutException;
	
	public Document getDocument(int idDocument) throws DaoDocumentGetException;
	
	public Document modifierDocument(Document document) throws DaoDocumentModificationException, TitreDocumentException, DescriptionDocumentException, ExemplairesDocumentException;
	
	public void    supprimerDocument(int idDocument) throws DaoDocumentSuppressionException, DaoDocumentGetException;
	
	public void annuler() throws DaoDocumentAjoutException, DaoDocumentModificationException;
	
	public int getNombreAnnulations();
	
	/**
	 * Renvoie tout les documents de l'application
	 * @return
	 * @throws DaoDocumentQueryException 
	 */
	public List<Document> listerDocument() throws DaoDocumentQueryException;

	
}
