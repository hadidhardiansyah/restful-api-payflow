package com.hadid.payflow.repository;

import com.hadid.payflow.entity.Invoice;
import com.hadid.payflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByCreatedBy(User user);

}
