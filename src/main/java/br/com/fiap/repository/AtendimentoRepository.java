package br.com.fiap.repository;

import br.com.fiap.model.Atendimento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtendimentoRepository implements PanacheRepository<Atendimento> {

}
