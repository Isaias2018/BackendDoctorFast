package com.doctorfast.org.repository;

import com.doctorfast.org.model.Doctor;
import com.doctorfast.org.model.Especialidad;
import com.doctorfast.org.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {




}
