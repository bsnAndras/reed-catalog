package com.bsnandras.reedcatalog.repositories;

import com.bsnandras.reedcatalog.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Date> {
    List<Log> findAllByOrderByDateTimeDesc();
}
