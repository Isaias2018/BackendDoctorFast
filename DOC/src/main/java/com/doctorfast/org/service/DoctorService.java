package com.doctorfast.org.service;

import com.doctorfast.org.model.Doctor;
import com.doctorfast.org.model.Especialidad;
import com.doctorfast.org.requests.RatingRequest;
import com.doctorfast.org.requests.RatingResponse;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    RatingResponse calificarDoctor(RatingRequest rating) throws Exception;

    List<Doctor> listarDoctores () throws Exception;

    String calificacionpromedio(Integer id) throws Exception;

    Optional<Doctor> getOne(int id) throws  Exception;


    Doctor edit(Doctor d) throws Exception;

    Boolean existsById(int id) throws Exception;

    Doctor modificarDoctor(Doctor doctor) throws  Exception;

    Long numeroDeDoctores();




}
