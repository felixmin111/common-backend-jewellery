package com.autowise.demo.controller;

import com.autowise.demo.dto.ImageDto;
import com.autowise.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto upload(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }

    @GetMapping
    public List<ImageDto> getAll() {
        return service.getAll();
    }
}