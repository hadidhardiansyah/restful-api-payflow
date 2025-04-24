package com.hadid.payflow.repository;

import com.hadid.payflow.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByCompanyId(String companyId);

}
