package com.jipmer.controller;


import com.jipmer.dto.MessageDTO;
import com.jipmer.dto.PatientDTO;
import com.jipmer.entity.Patient;
import com.jipmer.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    PatientRepository patientRepo;



    @GetMapping("/detail")
    public Patient getpatient(@RequestParam long patientId) {

        return patientRepo.findById(patientId);

    }
}
