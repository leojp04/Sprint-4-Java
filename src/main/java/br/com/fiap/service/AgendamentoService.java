package br.com.fiap.service;

import br.com.fiap.exception.HorarioOcupadoException;
import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Agendamento;
import br.com.fiap.repository.AgendamentoRepository;
import br.com.fiap.repository.PacienteRepository;
import br.com.fiap.repository.ProfissionalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoService {

    @Inject
    AgendamentoRepository agendamentoRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    private static final long DURACAO_CONSULTA_MINUTOS = 60;

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.listAll();
    }

    public Agendamento buscarPorId(Integer id) {
        return agendamentoRepository.findByIdOptional(id.longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado: " + id));
    }

    @Transactional
    public Agendamento criar(Agendamento agendamento) {

        if (agendamento.getPaciente() == null || agendamento.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Paciente inválido");
        }
        if (agendamento.getProfissional() == null || agendamento.getProfissional().getId() == null) {
            throw new IllegalArgumentException("Profissional inválido");
        }

        // valida existência
        pacienteRepository.findByIdOptional(agendamento.getPaciente().getId().longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        profissionalRepository.findByIdOptional(agendamento.getProfissional().getId().longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado"));

        // antecedência mínima
        if (Duration.between(LocalDateTime.now(), agendamento.getDataAgendamento()).toMinutes() < 30) {
            throw new IllegalArgumentException("A consulta deve ser agendada com pelo menos 30 minutos de antecedência");
        }

        LocalDateTime inicio = agendamento.getDataAgendamento();
        LocalDateTime fim = inicio.plusMinutes(DURACAO_CONSULTA_MINUTOS);

        boolean conflito = agendamentoRepository.existeConflitoProfissional(
                agendamento.getProfissional().getId(),
                inicio,
                fim
        );
        if (conflito) {
            throw new HorarioOcupadoException("O profissional já possui uma consulta neste horário");
        }

        agendamento.setStatus("AGENDADA");
        agendamento.setDataFim(fim);

        agendamentoRepository.persist(agendamento);
        return agendamento;
    }

    @Transactional
    public Agendamento atualizar(Integer id, Agendamento dados) {
        Agendamento existente = buscarPorId(id);

        if (dados.getDataAgendamento() != null) {
            existente.setDataAgendamento(dados.getDataAgendamento());
            existente.setDataFim(dados.getDataAgendamento().plusMinutes(DURACAO_CONSULTA_MINUTOS));
        }
        if (dados.getStatus() != null) existente.setStatus(dados.getStatus());
        if (dados.getModalidade() != null) existente.setModalidade(dados.getModalidade());
        if (dados.getPlataforma() != null) existente.setPlataforma(dados.getPlataforma());

        return existente;
    }

    @Transactional
    public void deletar(Integer id) {
        Agendamento existente = buscarPorId(id);
        agendamentoRepository.delete(existente);
    }
}
