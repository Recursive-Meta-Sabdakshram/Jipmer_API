package com.jipmer.repository;



import com.jipmer.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository  extends JpaRepository<Admission, Long>  {


    Admission getById(int id);
}
