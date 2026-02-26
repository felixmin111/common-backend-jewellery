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

    private final CustomerRepository repo;
    private final CustomerMapper mapper;

    public List<CustomerDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public CustomerDto getById(Long id) {
        return mapper.toDto(requireEntity(id));
    }

    public CustomerDto create(CustomerDto req) {
        // normalize
        req.setName(req.getName() == null ? null : req.getName().trim());
        req.setPhone(req.getPhone() == null ? null : req.getPhone().trim());
        req.setEmail(req.getEmail() == null ? null : req.getEmail().trim());

        if (repo.existsByPhone(req.getPhone())) {
            throw new IllegalArgumentException("Phone already exists: " + req.getPhone());
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        Customer entity = mapper.toEntity(req);
        Customer saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public CustomerDto update(Long id, CustomerDto req) {
        Customer entity = requireEntity(id);

        // normalize
        if (req.getName() != null) req.setName(req.getName().trim());
        if (req.getPhone() != null) req.setPhone(req.getPhone().trim());
        if (req.getEmail() != null) req.setEmail(req.getEmail().trim().toLowerCase());

        // 🔹 Phone unique check
        if (req.getPhone() != null &&
                repo.existsByPhoneAndIdNot(req.getPhone(), id)) {

            throw new IllegalArgumentException("Phone already exists: " + req.getPhone());
        }

        // 🔹 Email unique check
        if (req.getEmail() != null &&
                repo.existsByEmailIgnoreCaseAndIdNot(req.getEmail(), id)) {

            throw new IllegalArgumentException("Email already exists: " + req.getEmail());
        }

        mapper.updateEntityFromDto(req, entity);

        Customer saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Customer not found: " + id);
        repo.deleteById(id);
    }

    public Customer requireEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
}