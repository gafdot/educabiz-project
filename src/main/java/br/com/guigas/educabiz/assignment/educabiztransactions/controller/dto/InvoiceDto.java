package br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Invoice;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Status;

public class InvoiceDto extends TransactionDto {

	private Status status;
	private Long clientId;
	private Integer number;

	public InvoiceDto(Invoice invoice) {
		this.clientId = invoice.getClientId();
		this.number = invoice.getNumber();
		this.status = invoice.getStatus();
		this.setCreatedDate(invoice.getCreatedDate());
		this.setId(invoice.getId());
		this.setValue(invoice.getValue());
	}

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

}
