package com.hadid.payflow.repository;

import com.hadid.payflow.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByCompanyId(String companyId);

    Optional<Company> findByCompanyId(String companyId);

}
