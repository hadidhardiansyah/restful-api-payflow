package com.hadid.payflow.service;

import com.hadid.payflow.entity.User;
import com.hadid.payflow.entity.UserPrincipal;
import com.hadid.payflow.exception.BusinessException;
import com.hadid.payflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.hadid.payflow.exception.BusinessErrorCodes.ACCOUNT_NOT_ACTIVATED;
import static com.hadid.payflow.exception.BusinessErrorCodes.INVALID_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(INVALID_CREDENTIALS));

        if (!user.isEnabled()) {
            throw new BusinessException(ACCOUNT_NOT_ACTIVATED);
        }

        return new UserPrincipal(user);
    }
}
