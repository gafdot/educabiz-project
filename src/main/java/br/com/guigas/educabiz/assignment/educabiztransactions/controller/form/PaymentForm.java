package br.com.guigas.educabiz.assignment.educabiztransactions.controller.form;

import java.math.BigDecimal;
import java.util.Optional;

import javax.management.InstanceNotFoundException;
import javax.validation.constraints.NotNull;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Invoice;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Method;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Payment;
import br.com.guigas.educabiz.assignment.educabiztransactions.repository.InvoiceRepository;

public class PaymentForm {

	@NotNull
	private Long invoiceId;
	@NotNull
	private Method method;
	@NotNull
	private BigDecimal value;

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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Payment convert(InvoiceRepository invoiceRepository) throws InstanceNotFoundException {
		Optional<Invoice> optional = invoiceRepository.findById(invoiceId);
		if (optional.isEmpty()) {
			throw new InstanceNotFoundException("The provided invoice id didn't match with any one registered");
		}
		return new Payment(invoiceId, method, value);
	}

	public Payment edit(Payment payment) {
		payment.setInvoiceId(invoiceId);
		payment.setMethod(method);
		payment.setValue(value);
		return payment;
	}

}
