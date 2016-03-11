package potager.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import utilisateur.entity.Jardinier;


/**
 * 
 * @author Nicolas LAMBERT
 * 
 * <p> Le potager est un terrain cultivable d’un jardinier. 
 * Il a une dimension <i>(longueur x largeur)</i> et peut être géré par un jardinier.
 * Il est décomposé en plusieurs {@link potager.entity.Carre Carres} de potager. </p>
 */
@Entity
@Table(name="aosp2_potager")
public class Potager implements Serializable{

	private static final long serialVersionUID = -8065181790953611569L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected int         idPotager;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idProprietaire", unique=true)
	protected Jardinier   proprietaire;	
	
	protected LocalDate   dateCreation;	
	@Transient
	protected List<Carre> carres;
	
	@Column(length=20, nullable=false)
	private String nom;
	
	@Column(nullable=false)
	private int longueur;
	
	@Column(nullable=false)
	private int largeur;
	
	@Column(length=5, nullable=false)
	private String codePostal;
	
	public Potager(){
		
	}
	
	public Potager(String nom, int longueur, int largeur, String codePostal, Jardinier proprietaire){
		this.nom          = nom;
		this.longueur     = longueur;
		this.largeur      = largeur;
		this.codePostal   = codePostal;
		this.proprietaire = proprietaire;
		this.dateCreation = LocalDate.now();
	}
	
	public int getIdPotager() {
		return idPotager;
	}

	public void setIdPotager(int idPotager) {
		this.idPotager = idPotager;
	}

	public Jardinier getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Jardinier proprietaire) {
		this.proprietaire = proprietaire;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<Carre> getCarres() {
		return carres;
	}

	public void setCarres(List<Carre> carres) {
		this.carres = carres;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

}
