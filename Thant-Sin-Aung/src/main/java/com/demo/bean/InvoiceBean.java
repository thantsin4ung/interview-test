package com.demo.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.demo.entity.Invoice;
import com.demo.entity.Item;
import com.demo.service.InvoiceService;

@Named
@ViewScoped
public class InvoiceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<Invoice> invoiceSet = new HashSet<>();

	private Invoice invoice;

	private String title;

	@Inject
	private InvoiceService service;

	@PostConstruct
	private void init() {
		invoice = new Invoice();
		invoice.addItem(new Item());
		title = "Create Invoice";

		String str = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ope");

		if (null != str && !str.isEmpty()) {
			int id = Integer.parseInt(str);
			invoice = service.findById(id);

			if (null != operation && operation.equals("edit")) {
				title = "Edit Invoice";
			} else {
				title = "Invoice Details";
			}
		}

	}

	public String save() {
		service.save(invoice);
		return "/list-Invoice?faces-redirect=true";
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public InvoiceService getService() {
		return service;
	}

	public void setService(InvoiceService service) {
		this.service = service;
	}

	public boolean isEdit() {
		return !title.equals("Invoice Details");
	}

	public void removeItem(Item item) {
		invoice.getItems().remove(item);
	}

	public void addItem() {
		invoice.addItem(new Item());
	}

	public Set<Invoice> getInvoiceSet() {
		return invoiceSet;
	}

	public void setInvoiceSet(Set<Invoice> invoiceSet) {
		this.invoiceSet = invoiceSet;
	}

	// public int getTotal() {
	// return invoiceSet.stream().map(Invoice::getTotal).mapToInt(i -> i).sum();
	// }

}
