package com.hadid.payflow.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserAuthenticationResponse {

    private String status;

    private List<String> roles;

    private String token;

}
