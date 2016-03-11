package potager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//TODO: Faire la javadoc
@Entity
@Table(name="aosp2_terrain")
public abstract class Terrain implements Serializable{

	private static final long serialVersionUID = -4558213931653633895L;
	
	@Id
	private int id;
	private String nom;
	private int longueur;
	private int largeur;
	private String codePostal;
	
	public Terrain(){
		
	}
	
	public Terrain(String nom, int longueur, int largeur, String codePostal){
		this();
		this.nom        = nom;
		this.longueur   = longueur;
		this.largeur    = largeur;
		this.codePostal = codePostal;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
	
	
}
