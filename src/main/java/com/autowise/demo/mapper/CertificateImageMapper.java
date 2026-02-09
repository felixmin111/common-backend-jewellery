package com.autowise.demo.mapper;

import com.autowise.demo.dto.CertificateImageDto;
import com.autowise.demo.model.CertificateImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateImageMapper {
    CertificateImageDto toDto(CertificateImage entity);
}