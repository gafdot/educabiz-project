package br.com.guigas.educabiz.assignment.educabiztransactions.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public List<Payment> findByInvoiceId(Long invoiceId);
	
	@Query("select p from Payment p where p.createdDate >= :pStartDate and p.createdDate <= :pEndDate")
	public List<Payment> findByDate(@Param("pStartDate") LocalDateTime startDate, @Param("pEndDate") LocalDateTime endDate);
}
