package com.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int tax;

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	private String customer;

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "invoice", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Item> items;

	public Invoice() {
		items = new ArrayList<>();
	}

	public void addItem(Item item) {
		item.setInvoice(this);
		items.add(item);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getTotal() {
		return items.stream().mapToInt(a -> a.getTotal()).sum();
	}

	public int getItemCount() {
		return items.stream().mapToInt(a -> a.getOfItem()).sum();
	}

	public int getSubTotal() {
		return items.stream().map(Item::getTotal).mapToInt(i -> i).sum();
	}

	public int getTaxTotal() {
		return tax += getSubTotal();
	}

}
