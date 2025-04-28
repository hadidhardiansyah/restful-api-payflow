package com.hadid.payflow.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserRegistrationResponse {

    private String username;

    private String email;

    private boolean enabled;

    private List<CompanyResponse> companies;

    private List<String> roles;

}
