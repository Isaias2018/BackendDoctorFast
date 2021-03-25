package com.doctorfast.org.service.implementation;

import com.doctorfast.org.model.*;
import com.doctorfast.org.repository.*;
import com.doctorfast.org.requests.NuevoPasswordRequest;
import com.doctorfast.org.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PacienteRepository pacienteRepository;
    private RolRepository rolRepository;
    private UsuarioRolRepository usuarioRolRepository;
    private DoctorRepository doctorRepository;
    private EspecialidadRepository especialidadRepository;

    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PacienteRepository pacienteRepository,
                              RolRepository rolRepository, UsuarioRolRepository usuarioRolRepository,
                              DoctorRepository doctorRepository, EspecialidadRepository especialidadRepository,
                              PasswordEncoder bcryptEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.rolRepository = rolRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.doctorRepository = doctorRepository;
        this.especialidadRepository = especialidadRepository;
        this.bcryptEncoder=bcryptEncoder;
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws Exception {
        String password = bcryptEncoder.encode(paciente.getUsuario().getPassword());
        paciente.getUsuario().setPassword(password);
        Usuario usuario = usuarioRepository.save(paciente.getUsuario());

        UsuarioRol usuarioRol = new UsuarioRol();
        Rol rol = rolRepository.getOne(1);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRolRepository.save(usuarioRol);

        return pacienteRepository.save(paciente);
    }

    @Override
    public Doctor registrarDoctor(Doctor doctor) throws Exception {
        String password = bcryptEncoder.encode(doctor.getUsuario().getPassword());
        doctor.getUsuario().setPassword(password);
        Usuario usuario = usuarioRepository.save(doctor.getUsuario());

        UsuarioRol usuarioRol = new UsuarioRol();
        Rol rol = rolRepository.getOne(2);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRolRepository.save(usuarioRol);

        Especialidad especialidad = especialidadRepository.findByDescripcion(doctor.getEspecialidad().getNombre());
        doctor.setEspecialidad(especialidad);

        return doctorRepository.save(doctor);
    }

    @Override
    public List<Rol> listarRoles() throws Exception {
        return rolRepository.findAll();
    }

    @Override
    public List<Paciente> listarPacientes() throws Exception {
        return pacienteRepository.findAll();
    }

    @Override
    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioRepository.findAll();
    }

    @Override
    public int cambiarPassword(NuevoPasswordRequest nuevoPasswordRequest) throws Exception {

        String password = bcryptEncoder.encode(nuevoPasswordRequest.getNuevoPassword());
        nuevoPasswordRequest.setNuevoPassword(password);

        return usuarioRepository.findByUserNameDniEmail(nuevoPasswordRequest.getNombreUsuario(),
                nuevoPasswordRequest.getDni(),nuevoPasswordRequest.getCorreo(),nuevoPasswordRequest.getNuevoPassword());

    }
}
