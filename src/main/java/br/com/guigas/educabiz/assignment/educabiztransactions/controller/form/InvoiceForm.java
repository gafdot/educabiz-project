package br.com.guigas.educabiz.assignment.educabiztransactions.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Invoice;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Status;

public class InvoiceForm {

	@NotNull
	private Long clientId;
	@NotNull
	private Integer number;
	@NotNull
	private BigDecimal value;
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Invoice convert() {
		return new Invoice(clientId, number, value);
	}

	public Invoice edit(Invoice invoice) {
		invoice.setClientId(clientId);
		invoice.setNumber(number);
		invoice.setValue(value);
		if(status == null) {
			this.status = Status.ISSUED;
		}
		invoice.setStatus(status);
		return invoice;
	}

}
