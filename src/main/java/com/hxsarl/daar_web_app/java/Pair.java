package com.hxsarl.daar_web_app.java;

public class Pair {

	String key;
	Integer value;
	
	public Pair(String key, Integer value) {
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public Integer getValue() {
		return value;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
