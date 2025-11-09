package br.com.fiap.service;

import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Especialidade;
import br.com.fiap.repository.EspecialidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EspecialidadeService {

    @Inject
    EspecialidadeRepository especialidadeRepository;

    public List<Especialidade> listarTodos() {
        return especialidadeRepository.listAll();
    }

    public Especialidade buscarPorId(Integer id) {
        return especialidadeRepository.findByIdOptional(id.longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade não encontrada: " + id));
    }

    @Transactional
    public Especialidade criar(Especialidade especialidade) {
        especialidadeRepository.persist(especialidade);
        return especialidade;
    }

    @Transactional
    public Especialidade atualizar(Integer id, Especialidade dados) {
        Especialidade existente = buscarPorId(id);
        if (dados.getNome() != null) {
            existente.setNome(dados.getNome());
        }
        return existente;
    }

    @Transactional
    public void deletar(Integer id) {
        Especialidade existente = buscarPorId(id);
        especialidadeRepository.delete(existente);
    }
}
