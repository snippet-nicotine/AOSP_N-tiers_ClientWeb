package utilisateur.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aosp2_jardinier")
public class Jardinier implements Serializable{

	private static final long serialVersionUID = 8754664668653019633L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int idJardinier;
	private String nom;
	private String prenom;
	private String mail;
	private String motPasse;
	private String codePostal;
	
	public Jardinier(){
		this.nom = "Jardinier temp";
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "Jardinier [idJardinier=" + idJardinier + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail
				+ ", motPasse=" + motPasse + ", codePostal=" + codePostal + "]";
	}

	public int getIdJardinier() {
		return idJardinier;
	}

	public void setIdJardinier(int idJardinier) {
		this.idJardinier = idJardinier;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotPasse() {
		return motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

}
