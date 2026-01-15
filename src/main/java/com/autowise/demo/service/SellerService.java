package com.autowise.demo.service;

import com.autowise.demo.dto.SellerDto;
import com.autowise.demo.mapper.SellerMapper;
import com.autowise.demo.model.Seller;
import com.autowise.demo.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {

    private final SellerRepository repo;
    private final SellerMapper mapper;

    public List<SellerDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public SellerDto getById(Long id) {
        return mapper.toDto(requireEntity(id));
    }

    public SellerDto create(SellerDto request) {

        // normalize email
        String email = request.getEmail();
        if (email != null) email = email.trim().toLowerCase();

        // block duplicate
        if (email != null && !email.isBlank() && repo.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        // save with normalized email
        Seller entity = mapper.toEntity(request);
        entity.setEmail(email);

        Seller saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public SellerDto update(Long id, SellerDto request) {
        Seller entity = requireEntity(id);

        String newEmail = request.getEmail();
        if (newEmail != null) newEmail = newEmail.trim().toLowerCase();

        if (newEmail != null && !newEmail.isBlank()) {
            String currentEmail = entity.getEmail();
            if (currentEmail == null) currentEmail = "";
            currentEmail = currentEmail.trim().toLowerCase();

            // email changed AND already exists for someone else
            if (!newEmail.equals(currentEmail) && repo.existsByEmailIgnoreCase(newEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
            }
        }

        mapper.updateEntityFromDto(request, entity);
        entity.setEmail(newEmail);

        return mapper.toDto(repo.save(entity));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Seller not found: " + id);
        repo.deleteById(id);
    }

    public Seller requireEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found: " + id));
    }
}
