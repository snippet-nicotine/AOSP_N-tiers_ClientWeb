package potager.entity;

import java.io.Serializable;

//TODO: A Implementer
public class Carre implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8695011214229733217L;
	private int idCarre;
	
	public Carre(){
		
	}

	public int getIdCarre() {
		return idCarre;
	}

	public void setIdCarre(int idCarre) {
		this.idCarre = idCarre;
	}

}
