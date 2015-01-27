package br.com.intelbras.chat.model;

import java.util.Date;

public class TalkText {
	
	private Integer cod1;
	private Integer cod2;
	private String coment;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getCod1() {
		return cod1;
	}
	public void setCod1(Integer cod1) {
		this.cod1 = cod1;
	}
	public Integer getCod2() {
		return cod2;
	}
	public void setCod2(Integer cod2) {
		this.cod2 = cod2;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	
	

}

