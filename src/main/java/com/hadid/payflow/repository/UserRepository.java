package com.hadid.payflow.repository;

import com.hadid.payflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        @Query("SELECT u FROM User u " +
            "JOIN u.companies c " +
            "WHERE u.username = :username " +
            "AND c.companyId = :companyId")
    Optional<User> findByUsernameAndCompaniesCompanyId(String username, String companyId);

}
