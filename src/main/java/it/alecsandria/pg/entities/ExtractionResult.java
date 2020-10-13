package it.alecsandria.pg.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "extraction_result")
public class ExtractionResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int idPoduct;
	private String pIvaDealer;
	private String urlDealer;
	private float foundedPrice;
	private float shippingPrice;
	
	public ExtractionResult() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdPoduct() {
		return idPoduct;
	}
	public void setIdPoduct(int idPoduct) {
		this.idPoduct = idPoduct;
	}
	public String getpIvaDealer() {
		return pIvaDealer;
	}
	public void setpIvaDealer(String pIvaDealer) {
		this.pIvaDealer = pIvaDealer;
	}
	public String getUrlDealer() {
		return urlDealer;
	}
	public void setUrlDealer(String urlDealer) {
		this.urlDealer = urlDealer;
	}
	public float getFoundedPrice() {
		return foundedPrice;
	}
	public void setFoundedPrice(float foundedPrice) {
		this.foundedPrice = foundedPrice;
	}
	public float getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	
}
