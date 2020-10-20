package it.alecsandria.pg.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wrapper {

	private List<WrapperAttributes> values;

	public List<WrapperAttributes> getValues() {
		return values;
	}

	public void setValues(List<WrapperAttributes> values) {
		this.values = values;
	}
	
	@JsonIgnore
	private int idProduct;

	@JsonIgnore
	public int getIdProduct() {
		return idProduct;
	}

	@JsonIgnore
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
}
