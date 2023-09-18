package com.orion.newsdaily.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    public static final String STATUS_ACTIVE = "ACTIVE";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepo repository;


    @Value("${login.attempts.max:3}")
    private int loginAttemptsMax = 3;

    public UserService(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + userName);
        }
        boolean enabled = true;//STATUS_ACTIVE.equals(user.getStatus());
        boolean locked = true;//user.getLoginAttempts() == null || user.getLoginAttempts() < loginAttemptsMax;
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), enabled,
                true, true, locked, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }
}