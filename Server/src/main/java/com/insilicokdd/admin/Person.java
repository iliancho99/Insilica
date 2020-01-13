package com.insilicokdd.admin;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@MappedSuperclass
public abstract class Person {
	
	public enum Gender { MALE, FEMALE }

	@Enumerated(EnumType.STRING)
	private final Gender gender;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final String name;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final String phone;
	
	@Embedded
	private final Address address;
	
	Person() {
		this.address = null;
		this.phone = "";
		this.gender = null;
		this.name = "";
	}
	
	Person(String name, String phone, String country,
			String city, String postCode, Gender gender) {
		this.name = name;
		this.phone = phone;
		this.address = new Address(country, city, postCode);
		this.gender = gender;
	}
	
	public static class Address {
		private String country;
		private String city;
		private String postCode;
		
		Address() {}
		
		Address(String country, String city, String postCode) {
			this.country = country;
			this.city = city;
			this.postCode = postCode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
			return result;
		}
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}


	public Gender getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

}