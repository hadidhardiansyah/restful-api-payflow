package com.hadid.payflow.service;

import com.hadid.payflow.dto.request.UserAuthenticationRequest;
import com.hadid.payflow.dto.request.UserRegistrationRequest;
import com.hadid.payflow.dto.response.*;
import com.hadid.payflow.entity.*;
import com.hadid.payflow.enums.EmailTemplateName;
import com.hadid.payflow.exception.BusinessException;
import com.hadid.payflow.mapper.UserMapper;
import com.hadid.payflow.repository.AuthenticationRepository;
import com.hadid.payflow.repository.CompanyRepository;
import com.hadid.payflow.repository.RoleRepository;
import com.hadid.payflow.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;
    private final CustomUserDetailsService customUserDetailsService;

    public ApiResponse<UserRegistrationResponse> register(UserRegistrationRequest request) throws MessagingException {
        List<Role> userRoles = request.getRoles()
                        .stream()
                                .map(role -> "ROLE_" + role.toUpperCase())
                                        .map(roleName -> roleRepository.findRoleByName(roleName)
                                                .orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND)))
                                                .toList();

        validateUserUniqueness(request);

        Company companyId = companyRepository.findByCompanyId(request.getCompanyId())
                .orElseThrow(() -> new BusinessException(COMPANY_ID_NOT_FOUND));

        List<Company> companies = List.of(companyId);

        User user = userMapper.toUser(request, userRoles, companies, passwordEncoder);

        userRepository.save(user);
        sendValidationEmail(user);

        UserRegistrationResponse userRegistrationResponse = userMapper.toUserResponse(user);

        return ApiResponse.<UserRegistrationResponse>builder()
                .status("success")
                .message("User successfully registered")
                .data(userRegistrationResponse)
                .build();
    }

    private void validateUserUniqueness(UserRegistrationRequest request) throws BusinessException {
        boolean exists = userRepository.findByUsernameAndCompaniesCompanyId(
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

    @Transactional
    public ApiResponse<Void> activateAccount(String token) throws MessagingException {
        Authentication savedToken = authenticationRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(INVALID_ACTIVATION_CODE));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());

            throw new BusinessException(EXPIRED_ACTIVATION_CODE);
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new BusinessException(ACCOUNT_ALREADY_ACTIVATED);
        }

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        authenticationRepository.save(savedToken);

        return ApiResponse.<Void>builder()
                .status("success")
                .message("Account successfully activated")
                .build();
    }

    public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername() + ":" + request.getCompanyId(),
                            request.getPassword()
                    )
            );

            var userPrincipal = (UserPrincipal) auth.getPrincipal();
            var claims = new HashMap<String, Object>();
            claims.put("companyId", request.getCompanyId());

            var jwtToken = jwtService.generateToken(claims, userPrincipal);

            return UserAuthenticationResponse.builder()
                    .status("success")
                    .token(jwtToken)
                    .build();

        } catch (BadCredentialsException e) {
            throw new BusinessException(INVALID_CREDENTIALS);
        }
    }

}
