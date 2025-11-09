package br.com.fiap.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "T_IMREA_ESPECIALIDADE")
@SequenceGenerator(name = "esp_seq", sequenceName = "SQ_T_IMREA_ESPECIALIDADE", allocationSize = 1)
public class Especialidade extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esp_seq")
    @Column(name = "ID_ESPECIALIDADE")
    private Integer id;

    @Column(name = "NM_ESPECIALIDADE", nullable = false, length = 60)
    private String nome;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
