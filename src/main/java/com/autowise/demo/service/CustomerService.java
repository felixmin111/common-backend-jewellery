package com.autowise.demo.service;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.mapper.CustomerMapper;
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
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public List<CustomerDto> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        return customerMapper.toDto(customer);
    }
    public CustomerDto create(CustomerDto request) {
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDto(saved);
    }

    public CustomerDto update(Long id, CustomerDto request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        customerMapper.updateEntityFromDto(request, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found: " + id);
        }
        customerRepository.deleteById(id);
    }

}