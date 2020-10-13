package it.alecsandria.pg.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "seller_sites")
public class Seller_Sites {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int idSeller;
	private int idSite;
	
	public Seller_Sites () {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_seller() {
		return idSeller;
	}
	public void setId_seller(int id_seller) {
		this.idSeller = id_seller;
	}
	public int getId_site() {
		return idSite;
	}
	public void setId_site(int id_site) {
		this.idSite = id_site;
	}

}
