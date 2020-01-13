package com.insilicokdd.admin;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;


@Entity
@Table(name = "member_tbl")
@Indexed
public class Member extends Person {
	@Embedded
	private final Account account;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private int id;

	@Column(insertable = false, updatable = false)
	private String token;



	public Member() {
		id = 0;
		account = null;

	}

	Member(int id, String name, String phone, String country, String city, String postCode, Gender gender,
			String username, String password, String token) {
		super(name, phone, country, city, postCode, gender);
		this.account = new Account(username, password);
		this.id = id;
		this.token = token;
	}


    public static class Account {
		enum AccountStatus { ACTIVE, CLOSED, BLACKLISTED, NONE }

		@Enumerated(EnumType.STRING)
		private final AccountStatus status;

		private final String username;
		private final String password;
		
		private String token;

		public Account() {
			username = "";
			password = "";
			token = null;
			status = AccountStatus.NONE;
		}

		public Account(String username, String password) {
			this.username = username;
			this.password = password;
			this.status = AccountStatus.ACTIVE;
		}


		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public AccountStatus getStatus() {
			return status;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;

		}

	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}


	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", account=" + account + "]";
	}

}