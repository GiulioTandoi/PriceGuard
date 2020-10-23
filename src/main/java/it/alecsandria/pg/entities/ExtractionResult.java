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
	
	private int idProduct;
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
		return idProduct;
	}
	public void setIdPoduct(int idProduct) {
		this.idProduct = idProduct;
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
