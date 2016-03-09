package utilisateur.entity;

import java.io.Serializable;

public class Jardinier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8754664668653019633L;
	private int id;
	private String nom;
	
	public Jardinier(){
		this.nom = "Jardinier temp";
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
