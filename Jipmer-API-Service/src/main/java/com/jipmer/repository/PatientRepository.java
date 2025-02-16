package com.jipmer.repository;

import com.jipmer.entity.Message;
import com.jipmer.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findById(long id);
}
