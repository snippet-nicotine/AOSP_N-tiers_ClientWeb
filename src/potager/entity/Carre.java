package potager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//TODO: A Implementer
@Entity
@Table(name="aosp2_carre")
public class Carre implements Serializable{

	private static final long serialVersionUID = 8695011214229733217L;
	
	@Id
	private int idCarre;
	
	public Carre(){
		
	}

}
