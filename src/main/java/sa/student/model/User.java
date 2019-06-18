package sa.student.model;

import java.io.Serializable;

public class User implements Serializable{

	private Long id;

	private String name;

	private String last_name;

	private Integer personalId;

	private String password;

	private Integer room_id;

	private String username;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Integer getPersonal_id() {
		return personalId;
	}

	public void setPersonal_id(Integer personal_id) {
		this.personalId = personal_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoom_id() {
		return room_id;
	}

	public void setRoom_id(Integer room_id) {
		this.room_id = room_id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
