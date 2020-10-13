package it.alecsandria.pg.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "sites")
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name; 
	private String urlRobotService;
	
	public Site() {}
	
	public Site(String name, String url_robot_service) {
		super();
		this.name = name;
		this.urlRobotService = url_robot_service;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrlRobotService() {
		return urlRobotService;
	}

	public void setUrlRobotService(String urlRobotService) {
		this.urlRobotService = urlRobotService;
	}
	
}
