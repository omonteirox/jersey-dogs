package com.jersey_crud_api.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import com.jersey_crud_api.utils.NotificationUtils;

public class Dog extends NotificationUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date birthDate;
	private String race;
	private String color;
	private Integer tutor_id;
	
	
	public Integer getTutorId() {
		return tutor_id;
	}
	public void setTutorId(Integer tutor_id) {
		this.tutor_id = tutor_id;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate){
		if (LocalDate.now().isBefore(birthDate.toLocalDate())) {
			getNotifications().add("A data de nascimento não pode ser em um dia futuro");
		}
		this.birthDate = birthDate;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", race=" + race + ", color=" + color
				+ ", tutor_id=" + tutor_id + "]";
	}
	
	
}
