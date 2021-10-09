package br.com.guigas.educabiz.assignment.educabiztransactions.service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto.InvoiceDto;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto.OperationDto;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto.PaymentDto;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.form.InvoiceForm;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.form.PaymentForm;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Invoice;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Payment;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Status;
import br.com.guigas.educabiz.assignment.educabiztransactions.repository.InvoiceRepository;
import br.com.guigas.educabiz.assignment.educabiztransactions.repository.PaymentRepository;

@Service
public class TransactionsService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	public ResponseEntity<InvoiceDto> createInvoice(@Valid InvoiceForm invoiceForm, UriComponentsBuilder builder) {
		Invoice invoice = invoiceForm.convert();
		invoiceRepository.save(invoice);
		URI uri = builder.path("/invoice/{id}").buildAndExpand(invoice.getId()).toUri();
		return ResponseEntity.created(uri).body(new InvoiceDto(invoice));
	}

	public ResponseEntity<?> createPayment(@Valid PaymentForm paymentForm, UriComponentsBuilder builder) {
		Payment payment;
		try {
			payment = paymentForm.convert(invoiceRepository);
		} catch (InstanceNotFoundException e) {
			return ResponseEntity.status(400).body("The provided invoice id didn't match with any one registered");
		}
		paymentRepository.save(payment);
		
		verifyPaymentAndInvoiceValues(payment.getInvoiceId());
		
		URI uri = builder.path("/payment/{id}").buildAndExpand(payment.getId()).toUri();
		return ResponseEntity.created(uri).body(new PaymentDto(payment));
	}

	public ResponseEntity<?> editInvoice(@Valid InvoiceForm invoiceForm, Long id) {
		Optional<Invoice> optional = invoiceRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The invoice id has not been found.");
		} else {
			Invoice editedInvoice = invoiceForm.edit(optional.get());
			if (editedInvoice.getStatus() != Status.CANCELED)
				verifyPaymentAndInvoiceValues(editedInvoice.getId());
			return ResponseEntity.ok(new InvoiceDto(editedInvoice));
		}
	}

	public ResponseEntity<?> editPayment(@Valid PaymentForm paymentForm, Long id) {
		Optional<Payment> optional = paymentRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The payment id has not been found.");
		} else {
			Payment editedPayment = paymentForm.edit(optional.get());
			verifyPaymentAndInvoiceValues(editedPayment.getInvoiceId());
			return ResponseEntity.ok(new PaymentDto(editedPayment));
		}
	}

	public ResponseEntity<List<Object>> listTransactions(String stringStartDate, String stringEndDate) {
		LocalDateTime startDate = LocalDateTime.parse(stringStartDate + "T00:00:00");
		LocalDateTime endDate = LocalDateTime.parse(stringEndDate + "T23:59:59");
		
		List<OperationDto> invoiceList = invoiceRepository.findByDate(startDate, endDate)
				.stream().map(InvoiceDto::new).collect(Collectors.toList())
				.stream().map(in -> new OperationDto("Invoice", in)).collect(Collectors.toList());
		List<OperationDto> paymentList = paymentRepository.findByDate(startDate, endDate)
				.stream().map(PaymentDto::new).collect(Collectors.toList())
				.stream().map(p -> new OperationDto("Payment", p)).collect(Collectors.toList());
		
		List<OperationDto> list = new ArrayList<OperationDto>();
		list.addAll(invoiceList);
		list.addAll(paymentList);
		list.sort(new DateComparator());
		
		List<Invoice> unpaidInvoices = invoiceRepository.findByStatus(Status.ISSUED);
		BigDecimal sum = new BigDecimal(0);
		for (Invoice invoice : unpaidInvoices) {
			sum = sum.add(invoice.getValue());
		}
		
		return ResponseEntity.ok(Arrays.asList(list, "The sum of value of invoices pending payment is: " + sum));
	}

	public ResponseEntity<List<InvoiceDto>> listInvoices() {
		List<Invoice> invoiceList = invoiceRepository.findAll();
		for (Invoice invoice : invoiceList) {
			if (invoice.getStatus() != Status.CANCELED)
				verifyPaymentAndInvoiceValues(invoice.getId());
		}
		List<InvoiceDto> invoiceDtoList = invoiceList.stream().map(InvoiceDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(invoiceDtoList);
	}

	public ResponseEntity<List<PaymentDto>> listPayments() {
		List<PaymentDto> paymentList = paymentRepository.findAll().stream().map(PaymentDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(paymentList);
	}

	private void verifyPaymentAndInvoiceValues(Long invoiceId) {
		Invoice invoice = invoiceRepository.findById(invoiceId).get();
		List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
		
		BigDecimal paidValue = new BigDecimal(0);
		for(Payment eachPayment : payments) {
			paidValue = paidValue.add(eachPayment.getValue());
		}
		if(paidValue.compareTo(invoice.getValue()) >= 0) {
			invoice.setStatus(Status.PAID);
		} else {
			invoice.setStatus(Status.ISSUED);
		}
	}
}

class DateComparator implements Comparator<OperationDto> {
	@Override
	public int compare(OperationDto o1, OperationDto o2) {
		if (o1.getTransaction().getCreatedDate().isAfter(o2.getTransaction().getCreatedDate())) 
			return 1;
		else if (o1.getTransaction().getCreatedDate().isBefore(o2.getTransaction().getCreatedDate())) 
			return -1;
		else 
			return 0;
	}
}
