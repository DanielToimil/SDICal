package com.sdi.model;

import java.io.Serializable;
import com.sdi.model.type.UserStatus;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String name;
	
	private Long id;

	private UserStatus status = UserStatus.ENABLED;
	

	public User(String login, String name, Long id) {
		this.login = login;
		this.name = name;
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public UserStatus getStatus() {
		return status;
	}

	public User setStatus(UserStatus status) {
		this.status = status;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + "]";
	}

}