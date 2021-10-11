package br.com.guigas.educabiz.assignment.educabiztransactions.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.guigas.educabiz.assignment.educabiztransactions.controller.dto.InvoiceDto;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.form.InvoiceForm;
import br.com.guigas.educabiz.assignment.educabiztransactions.controller.form.PaymentForm;
import br.com.guigas.educabiz.assignment.educabiztransactions.service.TransactionsService;

@RestController
@RequestMapping("/api/transactions")
@Transactional
public class TransactionsApiController {
	
	@Autowired
	private TransactionsService service;

	@GetMapping
	public ResponseEntity<List<Object>> listTransactions(
			@RequestParam String startDate, @RequestParam String endDate) {
		return service.listTransactions(startDate, endDate);
	}
	
	/*Invoice related methods*/
	@PostMapping("/invoice")
	public ResponseEntity<InvoiceDto> createInvoice(@RequestBody @Valid InvoiceForm invoiceForm, UriComponentsBuilder builder) {
		return service.createInvoice(invoiceForm, builder);
	}
	
	@PutMapping("/invoice/{id}")
	public ResponseEntity<?> editInvoice(@RequestBody @Valid InvoiceForm invoiceForm, @PathVariable Long id) {
		return service.editInvoice(invoiceForm, id);
	}
	
	@GetMapping("/invoice")
	public ResponseEntity<?> listInvoices(Long id) {
		if(id == null)
			return service.listInvoices();
		else
			return service.getInvoice(id);
	}
	
	@GetMapping("/invoice/{id}")
	public ResponseEntity<?> getInvoice(@PathVariable Long id) {
		return service.getInvoice(id);
	}
	
	/*Payment related methods*/
	@PostMapping("/payment")
	public ResponseEntity<?> createPayment(@RequestBody @Valid PaymentForm paymentForm, UriComponentsBuilder builder) {
		return service.createPayment(paymentForm, builder);
	}
	
	@PutMapping("/payment/{id}")
	public ResponseEntity<?> editPayment(@RequestBody @Valid PaymentForm paymentForm, @PathVariable Long id) {
		return service.editPayment(paymentForm, id);
	}
	
	@GetMapping("/payment")
	public ResponseEntity<?> listPayments(Long id) {
		if(id == null)
			return service.listPayments();
		else
			return service.getPayment(id);
	}
	
	@GetMapping("/payment/{id}")
	public ResponseEntity<?> getPayment(@PathVariable Long id) {
		return service.getPayment(id);
	}
}
