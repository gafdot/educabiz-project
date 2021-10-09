package br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class TransactionDto {

	private Long id;
	private LocalDateTime createdDate;
	private BigDecimal value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
