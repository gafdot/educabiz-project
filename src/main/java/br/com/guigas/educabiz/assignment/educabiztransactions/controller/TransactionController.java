package br.com.guigas.educabiz.assignment.educabiztransactions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/transactions")
	public String transactions(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, Model model) {
		if (startDate == null)
			startDate = "2000-01-01";
		if (endDate == null)
			endDate = "2050-12-31";
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "transactions";
	}

	@GetMapping("/transactions/invoices")
	public String listInvoices() {
		return "invoiceList";
	}

	@GetMapping("/transactions/payments")
	public String listPayments() {
		return "paymentList";
	}

	@GetMapping("/transactions/newInvoice")
	public String createNewInvoice() {
		return "newInvoiceForm";
	}

	@GetMapping("/transactions/newPayment")
	public String createNewPayment() {
		return "newPaymentForm";
	}

	@GetMapping("/transactions/updateInvoice/{id}")
	public String updateInvoice(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "updateInvoiceForm";
	}

	@GetMapping("/transactions/updatePayment/{id}")
	public String updatePayment(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "updatePaymentForm";
	}
	
	
}
