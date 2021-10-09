package br.com.guigas.educabiz.assignment.educabiztransactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Invoice extends Transaction {

	@Enumerated(EnumType.STRING)
	private Status status;
	private Long clientId;
	private Integer number;

	public Invoice() {
		super();
	}

	public Invoice(Long clientId, Integer number, BigDecimal value) {
		super();
		this.status = Status.ISSUED;
		this.clientId = clientId;
		this.number = number;
		this.setCreatedDate(LocalDateTime.now());
		this.setValue(value);
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
