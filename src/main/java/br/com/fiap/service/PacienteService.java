package br.com.fiap.service;

import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Paciente;
import br.com.fiap.repository.PacienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PacienteService {

    @Inject
    PacienteRepository pacienteRepository;

    public List<Paciente> listarTodos() {
        return pacienteRepository.listAll();
    }

    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findByIdOptional(id.longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + id));
    }

    @Transactional
    public Paciente salvar(Paciente paciente) {
        pacienteRepository.persist(paciente);
        return paciente;
    }

    @Transactional
    public Paciente atualizar(Integer id, Paciente dados) {
        Paciente existente = buscarPorId(id);

        if (dados.getNome() != null) existente.setNome(dados.getNome());
        if (dados.getCpf() != null) existente.setCpf(dados.getCpf());
        if (dados.getEmail() != null) existente.setEmail(dados.getEmail());
        if (dados.getDataNascimento() != null) existente.setDataNascimento(dados.getDataNascimento());

        return existente;
    }

    @Transactional
    public void deletar(Integer id) {
        Paciente existente = buscarPorId(id);
        pacienteRepository.delete(existente);
    }
}
