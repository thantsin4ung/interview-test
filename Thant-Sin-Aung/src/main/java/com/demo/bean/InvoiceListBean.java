package com.demo.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.demo.entity.Invoice;
import com.demo.service.InvoiceService;

@Model
public class InvoiceListBean {

	private String customer;

	private List<Invoice> list;

	@Inject
	private InvoiceService service;

	@PostConstruct
	private void postConstruct() {
		innerSearch();
	}

	public String search() {
		innerSearch();
		return null;
	}

	public String delete(int id) {
		service.delete(id);
		return "/list-Invoice?faces-redirect=true";
	}

	private void innerSearch() {
		list = service.search(customer);
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public List<Invoice> getList() {
		return list;
	}

	public void setList(List<Invoice> list) {
		this.list = list;
	}

	public InvoiceService getService() {
		return service;
	}

	public void setService(InvoiceService service) {
		this.service = service;
	}

}
