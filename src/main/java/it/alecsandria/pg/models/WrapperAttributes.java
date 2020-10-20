package it.alecsandria.pg.models;

import java.util.List;

public class WrapperAttributes {

	private String typeName;
	private List<RobotResult> attribute;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<RobotResult> getAttribute() {
		return attribute;
	}
	public void setAttribute(List<RobotResult> attribute) {
		this.attribute = attribute;
	} 
	
}
