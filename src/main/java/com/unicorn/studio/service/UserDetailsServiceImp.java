package com.unicorn.studio.service;



import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.exception.UnverifiedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).get();
        if (user == null) throw new UsernameNotFoundException(username);
        if (user.getConfirmationTokenExpireAt() != null) throw new UnverifiedAccountException("Please confirm your account to login");
        Set<GrantedAuthority> userRole = new HashSet<>();
        userRole.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), userRole);
    }
}
