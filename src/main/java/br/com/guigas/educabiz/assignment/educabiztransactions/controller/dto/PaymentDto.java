package br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Method;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Payment;

public class PaymentDto extends TransactionDto {

	private Long invoiceId;
	private Method method;

	public PaymentDto(Payment payment) {
		this.invoiceId = payment.getInvoiceId();
		this.method = payment.getMethod();
		this.setCreatedDate(payment.getCreatedDate());
		this.setId(payment.getId());
		this.setValue(payment.getValue());
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
