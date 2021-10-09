package br.com.guigas.educabiz.assignment.educabiztransactions.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guigas.educabiz.assignment.educabiztransactions.model.Invoice;
import br.com.guigas.educabiz.assignment.educabiztransactions.model.Status;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Query("select i from Invoice i where i.createdDate >= :pStartDate and i.createdDate <= :pEndDate")
	public List<Invoice> findByDate(@Param("pStartDate") LocalDateTime startDate, @Param("pEndDate") LocalDateTime endDate);
	
	public List<Invoice> findByStatus(Status status);
}
