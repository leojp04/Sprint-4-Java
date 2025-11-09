package br.com.fiap.service;

import br.com.fiap.exception.RecursoNaoEncontradoException;
import br.com.fiap.model.Agendamento;
import br.com.fiap.model.Atendimento;
import br.com.fiap.repository.AgendamentoRepository;
import br.com.fiap.repository.AtendimentoRepository;
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

        // garante que a consulta existe
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

    @Transactional
    public Atendimento atualizar(Integer id, Atendimento dados) {
        Atendimento existente = buscarPorId(id);

        if (dados.getGravidade() != null) {
            existente.setGravidade(dados.getGravidade());
        }
        if (dados.getEncerramento() != null) {
            existente.setEncerramento(dados.getEncerramento());
        }
        if (dados.getPrioridade() != null) {
            existente.setPrioridade(dados.getPrioridade());
        }
        // se quiser permitir trocar a consulta:
        if (dados.getConsulta() != null && dados.getConsulta().getId() != null) {
            Agendamento consulta = agendamentoRepository.findById(dados.getConsulta().getId().longValue());
            if (consulta == null) {
                throw new RecursoNaoEncontradoException("Consulta não encontrada: " + dados.getConsulta().getId());
            }
            existente.setConsulta(consulta);
        }

        return existente;
    }

    @Transactional
    public void deletar(Integer id) {
        Atendimento existente = buscarPorId(id);
        atendimentoRepository.delete(existente);
    }
}
