package br.com.fiap.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_IMREA_CONSULTA")
@SequenceGenerator(name = "consulta_seq", sequenceName = "SQ_T_IMREA_CONSULTA", allocationSize = 1)
public class Agendamento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_seq")
    @Column(name = "ID_CONSULTA")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTE", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICO", nullable = false)
    private Profissional profissional;

    @Column(name = "DT_INICIO", nullable = false)
    private LocalDateTime dataAgendamento;

    @Column(name = "DT_FIM")
    private LocalDateTime dataFim;

    @Column(name = "DS_STATUS", length = 20)
    private String status;

    @Column(name = "TP_MODALIDADE", length = 12)
    private String modalidade;

    @Column(name = "DS_PLATAFORMA", length = 60)
    private String plataforma;

    // ===== Getters e Setters =====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
