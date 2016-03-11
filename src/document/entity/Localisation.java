package document.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aosp2_localisation")
public class Localisation implements Serializable{
	

	private static final long serialVersionUID = -4695346207446779672L;
	
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String lieu;
	private String emplacement;
		
	
	public Localisation() {
		super();
	}
		
	
	public Localisation(String lieu, String emplacement) {
		this();
		this.lieu = lieu;
		this.emplacement = emplacement;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getEmplacement() {
		return emplacement;
	}
	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}



	@Override
	public String toString() {
		return "Localisation [id=" + id + ", lieu=" + lieu + ", emplacement=" + emplacement + "]";
	}
	

}
