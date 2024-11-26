package com.example.section1.config;

import com.example.section1.model.Customer;
import com.example.section1.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class BankUserDetailsService implements UserDetailsService {
    @Autowired
    private final CustomerRepository customerRepository;

    public BankUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Customer customer= customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User details not found for thr user "+ username));
       List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
       return new User(customer.getEmail(),customer.getPwd(),authorities);
    }
}
