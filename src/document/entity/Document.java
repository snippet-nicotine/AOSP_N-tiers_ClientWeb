package document.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Document implements Serializable{

	private static final long serialVersionUID = 5639389737306562747L;

	private int id;
	private String titre;
	
	private String descriptif;
	private int nbExemplairesDispo;
	
	public Document(){
		
	}
	
	public Document(String titre, String descriptif, int nbExemplairesDispo) {
		super();
		this.titre = titre;
		this.descriptif = descriptif;
		this.nbExemplairesDispo = nbExemplairesDispo;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String title) {
		this.titre = title;
	}
	public String getDescriptif() {
		return descriptif;
	}
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	public int getNbExemplairesDispo() {
		return nbExemplairesDispo;
	}
	public void setNbExemplairesDispo(int nbExemplairesDispo) {
		this.nbExemplairesDispo = nbExemplairesDispo;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", titre=" + titre + ", descriptif=" + descriptif + ", nbExemplairesDispo="
				+ nbExemplairesDispo + "]";
	}
	
	

}
