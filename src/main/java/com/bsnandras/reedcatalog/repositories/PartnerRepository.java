package com.bsnandras.reedcatalog.repositories;

import com.bsnandras.reedcatalog.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner,Long> {
    Optional<Partner> findById(Long id);
    List<Partner> findAllByOrderByName();
}
