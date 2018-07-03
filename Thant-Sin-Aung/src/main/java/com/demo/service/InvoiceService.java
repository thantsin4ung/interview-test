package com.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.demo.entity.Invoice;

@Stateless
@LocalBean
public class InvoiceService {

	@PersistenceContext
	private EntityManager em;

	public Invoice findById(int id) {
		return em.find(Invoice.class, id);
	}

	public void save(Invoice invoice) {
		if (invoice.getId() > 0) {
			em.merge(invoice);
		} else {
			em.persist(invoice);
		}
	}

	public void delete(int id) {
		em.remove(findById(id));
	}

	public List<Invoice> search(String customer) {
		StringBuffer sb = new StringBuffer("select i from Invoice i where 1 = 1 ");
		Map<String, Object> params = new HashMap<>();

		if (null != customer && !customer.isEmpty()) {
			sb.append("and upper(i.customer) like upper(:customer) ");
			params.put("customer", customer.concat("%"));
		}

		TypedQuery<Invoice> query = em.createQuery(sb.toString(), Invoice.class);

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		return query.getResultList();

	}

}
