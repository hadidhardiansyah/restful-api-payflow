package com.hadid.payflow.service;

import com.hadid.payflow.dto.request.UserRegistrationRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.entity.Authentication;
import com.hadid.payflow.entity.Company;
import com.hadid.payflow.entity.Role;
import com.hadid.payflow.entity.User;
import com.hadid.payflow.enums.EmailTemplateName;
import com.hadid.payflow.exception.BusinessException;
import com.hadid.payflow.repository.AuthenticationRepository;
import com.hadid.payflow.repository.CompanyRepository;
import com.hadid.payflow.repository.RoleRepository;
import com.hadid.payflow.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hadid.payflow.exception.BusinessErrorCodes.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public ApiResponse<User> register(UserRegistrationRequest request) throws MessagingException {
        List<Role> userRoles = request.getRoles()
                        .stream()
                                .map(role -> "ROLE_" + role.toUpperCase())
                                        .map(roleName -> roleRepository.findRoleByName(roleName)
                                                .orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND)))
                                                .toList();

        validateUserUniqueness(request);

        Company companyId = companyRepository.findByCompanyId(request.getCompanyId())
                .orElseThrow(() -> new BusinessException(COMPANY_ID_NOT_FOUND));

        var user = User.builder()
                .company(companyId)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enabled(false)
                .roles(userRoles)
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
        sendValidationEmail(user);

        return ApiResponse.<User>builder()
                .status("success")
                .message("User successfully registered")
                .data(user)
                .build();

    }

    private void validateUserUniqueness(UserRegistrationRequest request) throws BusinessException {
        boolean exists = userRepository.findByUsernameAndCompanyCompanyId(
                request.getUsername(),
                request.getCompanyId()
        ).isPresent();
        if (exists) {
            throw new BusinessException(USERNAME_ALREADY_EXISTS);
        }
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        final String activationUrl = "http://localhost:4200/activate-account";

        emailService.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
//  Generate token
        String generateToken = generateActivationCode(6);
        var token = Authentication.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        authenticationRepository.save(token);

        return generateToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

}
