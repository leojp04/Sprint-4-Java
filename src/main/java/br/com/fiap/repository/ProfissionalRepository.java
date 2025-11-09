package br.com.fiap.repository;

import br.com.fiap.model.Profissional;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfissionalRepository implements PanacheRepository<Profissional> {
}
