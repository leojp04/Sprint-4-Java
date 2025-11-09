package br.com.fiap.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_IMREA_ATENDIMENTO")
@SequenceGenerator(name = "atend_seq", sequenceName = "SQ_T_IMREA_ATEND", allocationSize = 1)
public class Atendimento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atend_seq")
    @Column(name = "ID_ATENDIMENTO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_CONSULTA", nullable = false)
    private Agendamento consulta;

    @Column(name = "NR_GRAVIDADE")
    private Integer gravidade;

    @Column(name = "DT_ABERTURA", nullable = false)
    private LocalDateTime abertura;

    @Column(name = "DT_ENCERRAMENTO")
    private LocalDateTime encerramento;

    @Column(name = "NR_PRIORIDADE_CALC")
    private BigDecimal prioridade;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Agendamento getConsulta() { return consulta; }
    public void setConsulta(Agendamento consulta) { this.consulta = consulta; }

    public Integer getGravidade() { return gravidade; }
    public void setGravidade(Integer gravidade) { this.gravidade = gravidade; }

    public LocalDateTime getAbertura() { return abertura; }
    public void setAbertura(LocalDateTime abertura) { this.abertura = abertura; }

    public LocalDateTime getEncerramento() { return encerramento; }
    public void setEncerramento(LocalDateTime encerramento) { this.encerramento = encerramento; }

    public BigDecimal getPrioridade() { return prioridade; }
    public void setPrioridade(BigDecimal prioridade) { this.prioridade = prioridade; }
}
