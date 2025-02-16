package com.jipmer.controller;


import com.jipmer.dto.PatientDTO;
import com.jipmer.entity.Admission;
import com.jipmer.entity.Doctor;
import com.jipmer.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jipmer.repository.AdmissionRepository;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class JipmerLoginController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AdmissionRepository admissionRepository;

    // Login Method
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);

        if (doctor.isPresent()) {
            return ResponseEntity.ok(doctor.get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
    @GetMapping("/admissions")
    public List<PatientDTO> getAllAdmissions() {

       List<Admission> admissions =   admissionRepository.findAll();
        List<PatientDTO> list = new ArrayList<PatientDTO>();
       for(Admission admission : admissions) {

           PatientDTO patient = new PatientDTO();
           patient.setId(admission.getPatient().getId());
           patient.setName(admission.getPatient().getName());
           patient.setSeverity(admission.getSeverity());
           patient.setLastVisit(admission.getCreatedDate().toString());
           patient.setAdmissionId(admission.getId().toString());
           list.add(patient);
       }
    return list;

    }




}
