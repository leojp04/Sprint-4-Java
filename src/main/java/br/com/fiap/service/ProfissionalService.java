package br.com.fiap.service;

import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Profissional;
import br.com.fiap.repository.ProfissionalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfissionalService {

    @Inject
    ProfissionalRepository profissionalRepository;

    public List<Profissional> listarTodos() {
        return profissionalRepository.listAll();
    }

    public Profissional buscarPorId(Integer id) {
        return profissionalRepository.findByIdOptional(id.longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado: " + id));
    }

    @Transactional
    public Profissional salvar(Profissional profissional) {
        profissionalRepository.persist(profissional);
        return profissional;
    }

    @Transactional
    public Profissional atualizar(Integer id, Profissional dados) {
        Profissional existente = buscarPorId(id);

        if (dados.getNome() != null) existente.setNome(dados.getNome());
        if (dados.getCrm() != null) existente.setCrm(dados.getCrm());
        if (dados.getEspecialidade() != null) existente.setEspecialidade(dados.getEspecialidade());

        return existente;
    }

    @Transactional
    public void deletar(Integer id) {
        Profissional existente = buscarPorId(id);
        profissionalRepository.delete(existente);
    }
}
