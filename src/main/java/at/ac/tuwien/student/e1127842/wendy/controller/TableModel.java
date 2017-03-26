package at.ac.tuwien.student.e1127842.wendy.controller;

/**
 * Model fuer Reservierungstabelle im Mainframe
 * @author Lorenz Leutgeb
 *
 */
public class TableModel {
	private int id; 
	private String name; 
	private String preis; 
	private String pferd; 
	
	
	public TableModel(int id, String name, String preis, String pferd){
		this.id = id; 
		this.name = name; 
		this.preis = preis; 
		this.pferd = pferd; 
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPreis() {
		return preis;
	}


	public void setPreis(String preis) {
		this.preis = preis;
	}


	public String getPferd() {
		return pferd;
	}


	public void setPferd(String pferd) {
		this.pferd = pferd;
	}

}
