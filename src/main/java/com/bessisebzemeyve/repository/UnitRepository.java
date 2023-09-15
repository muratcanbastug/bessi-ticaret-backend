package com.bessisebzemeyve.repository;

import com.bessisebzemeyve.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    boolean existsByName(String name);
    Optional<Unit> findByName(String name);

}
