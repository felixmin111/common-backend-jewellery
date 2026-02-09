package com.autowise.demo.service;

import com.autowise.demo.dto.ImageDto;
import com.autowise.demo.mapper.ImageMapper;
import com.autowise.demo.model.Image;
import com.autowise.demo.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository repo;
    private final ImageMapper mapper;
    private final S3StorageService storage;

    public ImageDto upload(MultipartFile file) {
        String url = storage.upload(file);
        Image saved = repo.save(mapper.toEntity(url));
        return mapper.toDto(saved);
    }

    public List<ImageDto> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}