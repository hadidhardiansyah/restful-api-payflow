package com.hadid.payflow.mapper;

import com.hadid.payflow.dto.request.UserRegistrationRequest;
import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.dto.response.UserRegistrationResponse;
import com.hadid.payflow.entity.Company;
import com.hadid.payflow.entity.Role;
import com.hadid.payflow.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .build();
    }

    public User toUser(UserRegistrationRequest request, List<Role> roles, Company company, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .company(company)
                .enabled(false)
                .roles(roles)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public UserRegistrationResponse toUserResponse(User user) {
        return UserRegistrationResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .company(toCompanyResponse(user.getCompany()))
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }

}
