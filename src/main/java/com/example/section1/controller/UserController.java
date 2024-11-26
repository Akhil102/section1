package com.example.section1.controller;


import com.example.section1.model.Customer;
import com.example.section1.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
//@RequiredArgsConstructor
public class UserController {
    private final CustomerRepository customerRepository;

    public UserController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){

        try{

            String hashPwd=passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            Customer savedCustomer=customerRepository.save(customer);
            if(savedCustomer.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("Customer is Created");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer registration failed");
            }

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception has occured: "+ e.getMessage());
        }

    }
}
