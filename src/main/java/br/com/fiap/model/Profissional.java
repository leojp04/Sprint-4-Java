package br.com.fiap.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "T_IMREA_MEDICO")
@SequenceGenerator(name = "medico_seq", sequenceName = "SQ_T_IMREA_MEDICO", allocationSize = 1)
public class Profissional extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medico_seq")
    @Column(name = "ID_MEDICO")
    private Integer id;

    @Column(name = "NM_MEDICO", nullable = false, length = 100)
    private String nome;

    @Column(name = "NR_CRM", unique = true, length = 30)
    private String crm;

    @ManyToOne
    @JoinColumn(name = "ID_ESPECIALIDADE")
    private Especialidade especialidade;

    // ===== Getters e Setters =====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
