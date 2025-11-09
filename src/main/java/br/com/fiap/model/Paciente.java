package br.com.fiap.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_IMREA_PACIENTE")
@SequenceGenerator(name = "paciente_seq", sequenceName = "SQ_T_IMREA_PACIENTE", allocationSize = 1)
public class Paciente extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente_seq")
    @Column(name = "ID_PACIENTE")
    private Integer id;

    @Column(name = "NM_PACIENTE", nullable = false, length = 100)
    private String nome;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "NR_CPF", unique = true, length = 11)
    private String cpf;

    @Column(name = "DS_EMAIL", length = 120)
    private String email;

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
