package com.doctorfast.org.service.implementation;

import com.doctorfast.org.model.Doctor;
import com.doctorfast.org.model.Especialidad;
import com.doctorfast.org.model.Rating;
import com.doctorfast.org.repository.DoctorRepository;
import com.doctorfast.org.repository.RatingRepository;
import com.doctorfast.org.repository.UsuarioRepository;
import com.doctorfast.org.requests.RatingRequest;
import com.doctorfast.org.requests.RatingResponse;
import com.doctorfast.org.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DoctorServiceImpl implements DoctorService {

    private RatingRepository ratingRepository;
    private DoctorRepository doctorRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public DoctorServiceImpl(RatingRepository ratingRepository, DoctorRepository doctorRepository){
        this.ratingRepository = ratingRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public RatingResponse calificarDoctor(RatingRequest rating) throws Exception {
        Doctor doctor = doctorRepository.getOne(rating.getDoctorId());
        Rating calificacion = new Rating();
        calificacion.setCalificacion(rating.getCalificacion());
        calificacion.setDoctor(doctor);
        ratingRepository.save(calificacion);

        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setIdRating(calificacion.getIdRating());
        ratingResponse.setCalificacion(calificacion.getCalificacion());
        ratingResponse.setDoctorId(rating.getDoctorId());

        return ratingResponse;
    }

    @Override
    public List<Doctor> listarDoctores() throws Exception {
        return doctorRepository.findAll();
    }


    @Override
    public String calificacionpromedio(Integer id) throws Exception {
        List<String> calificaciones = new ArrayList<>();
        double promedio = 0.0;

        List<Rating> ratings = ratingRepository.findByDoctorId(id);

        for(Rating r:ratings){
            calificaciones.add(r.getCalificacion());
        }
        for (String c:calificaciones){
            promedio = promedio + Double.parseDouble(c);
        }
        promedio = promedio/calificaciones.size();

        return Double.toString(promedio);
    }

    @Override
    public Optional<Doctor> getOne(int id) throws Exception{
        return doctorRepository.findById(id);
    }




    @Override
    public Doctor edit(Doctor d) throws Exception {
        return doctorRepository.save(d);
    }

    @Override
    public Boolean existsById(int id) throws Exception {
        return doctorRepository.existsById(id);
    }

    @Override
    public Doctor modificarDoctor(Doctor doctor) throws Exception {
        return doctorRepository.save(doctor);
    }

    @Override
    public Long numeroDeDoctores() {
        return doctorRepository.count();
    }




}
