package sa.student.model;

import java.io.Serializable;

public class User implements Serializable{

	private String name;

	private String last_name;

	private Long personalId;

	private String password;

	private Integer room_id;

	private String username;


	public User (){
		super();
	}

	public User(String name, String last_name, Long personalId, String password, Integer room_id, String username) {
		super();
		this.name = name;
		this.last_name = last_name;
		this.personalId = personalId;
		this.password = password;
		this.room_id = room_id;
		this.username = username;
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

	public Long getPersonal_id() {
		return personalId;
	}

	public void setPersonal_id(Long personal_id) {
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
