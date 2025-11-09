package br.com.fiap.repository;

import br.com.fiap.model.Agendamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class AgendamentoRepository implements PanacheRepository<Agendamento> {

    /**
     * Verifica se já existe consulta do mesmo profissional que colida com o intervalo informado.
     * Regra: existe conflito quando:
     * - mesmo profissional
     * - status diferente de CANCELADO
     * - início existente < novo fim
     * - e (fim existente é null ou fim existente > novo início)
     */
    public boolean existeConflitoProfissional(Integer profissionalId,
                                              LocalDateTime novaDataInicio,
                                              LocalDateTime novaDataFim) {

        Long total = getEntityManager()
                .createQuery(
                        "SELECT COUNT(a) FROM Agendamento a " +
                                "WHERE a.profissional.id = :profissionalId " +
                                "AND a.status <> 'CANCELADO' " +
                                "AND a.dataAgendamento < :novaDataFim " +
                                "AND (a.dataFim IS NULL OR a.dataFim > :novaDataInicio)",
                        Long.class)
                .setParameter("profissionalId", profissionalId)
                .setParameter("novaDataInicio", novaDataInicio)
                .setParameter("novaDataFim", novaDataFim)
                .getSingleResult();

        return total != null && total > 0;
    }
}
