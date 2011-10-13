package br.com.promove.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Resumo implements Serializable{

	private String item;
	private String subitem;
	private Integer quantidadeItem;
	private Double percentualItem;
	private Integer quantidadeSubitem;
	private Double percentualSubitem;
	
	public Resumo() {}
	
	public Resumo(String item, Integer quantidadeItem, Double percentualItem, String subitem, Integer quantidadeSubitem, Double percentualSubitem) {
		this.item = item; 
		this.quantidadeItem = quantidadeItem; 
		this.percentualItem = percentualItem; 
		this.setSubitem(subitem); 
		this.quantidadeSubitem = quantidadeSubitem; 
		this.percentualSubitem = percentualSubitem; 
	}
	
	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setSubitem(String subitem) {
		this.subitem = subitem;
	}

	public String getSubitem() {
		return subitem;
	}

	public void setQuantidadeItem(Integer quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
	}

	public Integer getQuantidadeItem() {
		return quantidadeItem;
	}

	public void setPercentualItem(Double percentualItem) {
		this.percentualItem = percentualItem;
	}

	public Double getPercentualItem() {
		return percentualItem;
	}

	public void setQuantidadeSubitem(Integer quantidadeSubitem) {
		this.quantidadeSubitem = quantidadeSubitem;
	}

	public Integer getQuantidadeSubitem() {
		return quantidadeSubitem;
	}

	public void setPercentualSubitem(Double percentualSubitem) {
		this.percentualSubitem = percentualSubitem;
	}

	public Double getPercentualSubitem() {
		return percentualSubitem;
	}

	@Override
	public String toString() {
		return item;
	}

}
