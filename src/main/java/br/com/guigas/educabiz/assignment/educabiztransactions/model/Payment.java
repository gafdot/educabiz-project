package br.com.guigas.educabiz.assignment.educabiztransactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Payment extends Transaction {

	private Long invoiceId;
	@Enumerated(EnumType.STRING)
	private Method method;
	
	public Payment() {
		super();
	}

	public Payment(Long invoiceId, Method method, BigDecimal value) {
		super();
		this.invoiceId = invoiceId;
		this.method = method;
		this.setValue(value);
		this.setCreatedDate(LocalDateTime.now());
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

}
