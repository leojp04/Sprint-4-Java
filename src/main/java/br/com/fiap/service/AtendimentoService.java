package br.com.fiap.service;

import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Atendimento;
import br.com.fiap.model.Agendamento;
import br.com.fiap.repository.AtendimentoRepository;
import br.com.fiap.repository.AgendamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AtendimentoService {

    @Inject
    AtendimentoRepository atendimentoRepository;

    @Inject
    AgendamentoRepository agendamentoRepository;

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.listAll();
    }

    public Atendimento buscarPorId(Integer id) {
        return atendimentoRepository.findByIdOptional(id.longValue())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendimento não encontrado: " + id));
    }

    @Transactional
    public Atendimento registrar(Atendimento atendimento) {

        if (atendimento.getConsulta() != null && atendimento.getConsulta().getId() != null) {
            Agendamento consulta = agendamentoRepository.findById(atendimento.getConsulta().getId().longValue());
            if (consulta == null) {
                throw new RecursoNaoEncontradoException("Consulta não encontrada: " + atendimento.getConsulta().getId());
            }
            atendimento.setConsulta(consulta);
        }

        if (atendimento.getAbertura() == null) {
            atendimento.setAbertura(LocalDateTime.now());
        }

        atendimentoRepository.persist(atendimento);
        return atendimento;
    }
}
