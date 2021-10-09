package br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto;

public class OperationDto {

	private String transactionType;
	private TransactionDto transaction;

	public OperationDto(String transactionType, TransactionDto transaction) {
		this.transactionType = transactionType;
		this.transaction = transaction;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionDto getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionDto transaction) {
		this.transaction = transaction;
	}

}
