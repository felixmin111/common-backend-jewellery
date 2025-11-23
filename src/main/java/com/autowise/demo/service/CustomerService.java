package com.autowise.demo.service;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.model.Customer;
import com.autowise.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerDto> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        return toResponse(customer);
    }

    public CustomerDto create(CustomerDto request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role(request.getRole())
                .hashPassword(request.getPassword())
                .build();

        Customer saved = customerRepository.save(customer);
        return toResponse(saved);
    }

    public CustomerDto update(Long id, CustomerDto request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        if (request.getName() != null) {
            customer.setName(request.getName());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getRole() != null) {
            customer.setRole(request.getRole());
        }
        if (request.getPassword() != null) {
            customer.setHashPassword(request.getPassword());
        }

        return toResponse(customer);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found: " + id);
        }
        customerRepository.deleteById(id);
    }


    private CustomerDto toResponse(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .role(customer.getRole())
                .build();
    }
}