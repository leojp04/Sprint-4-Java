package br.com.fiap.repository;

import br.com.fiap.model.Paciente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {
    // dá pra criar findByCpf aqui se quiser
}
