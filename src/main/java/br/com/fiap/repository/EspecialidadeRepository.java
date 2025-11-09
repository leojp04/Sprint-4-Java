package br.com.fiap.repository;

import br.com.fiap.model.Especialidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EspecialidadeRepository implements PanacheRepository<Especialidade> {
}
