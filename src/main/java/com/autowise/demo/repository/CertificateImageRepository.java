package com.autowise.demo.repository;

import com.autowise.demo.model.CertificateImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateImageRepository extends JpaRepository<CertificateImage, Long> {
    List<CertificateImage> findByGemsPackageId(Long gemsPackageId);
}