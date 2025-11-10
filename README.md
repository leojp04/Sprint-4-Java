# IMREA API - Quarkus (DDD)

API REST para gestão de Pacientes, Profissionais, Especialidades, Agendamentos de Consultas e Atendimentos do IMREA, construída com Quarkus 3, Hibernate ORM com Panache e Oracle Database.

Este README foi personalizado para este projeto, com instruções de execução, arquitetura, endpoints e exemplos.

- Stack principal: Java 21, Quarkus 3.28, REST (quarkus-rest + Jackson), Hibernate ORM Panache, Oracle JDBC, Bean Validation, CDI (Arc).
- Porta padrão: `8080` | Dev UI: `http://localhost:8080/q/dev/`

---

## Repositório
GitHub: https://github.com/leojp04/Sprint-4-Java

## Integrantes
- Leonardo José Pereira — RM 563065 — 1TDSPW
- Fabricio Henrique Pereira — RM 563237 — 1TDSPW
- Ícaro José dos Santos — RM 562403 — 1TDSPW

---

## Sumário
- Visão Geral e Arquitetura
- Pré‑requisitos
- Configuração (Banco/Variáveis)
- Como executar (Dev, Jar, Uber‑jar, Native, Docker)
- Domínio e Modelos
- Endpoints (com exemplos)
- Regras de Negócio e Erros
- Testes
- Scripts SQL

---

## Visão Geral e Arquitetura
O projeto segue princípios de Domain‑Driven Design (DDD) de forma pragmática:

- Camada de Domínio: `br.com.fiap.model` (Entidades JPA/Panache)
- Repositórios: `br.com.fiap.repository` (acesso a dados com Panache)
- Serviços: `br.com.fiap.service` (regras de negócio e orquestração)
- Recursos REST: `br.com.fiap.resource` (exposição de endpoints HTTP)
- Exceções e Mapeamento Global: `br.com.fiap.exception` (erros -> HTTP)

Principais entidades: `Paciente`, `Profissional`, `Especialidade`, `Agendamento` (Consulta) e `Atendimento`.

---

## Pré‑requisitos
- Java 21 (JDK)
- Maven 3.9+ (ou wrapper `mvnw` incluso)
- Banco Oracle acessível (credenciais válidas)

---

## Configuração
Arquivo: `src/main/resources/application.properties`

```
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=rm563065
quarkus.datasource.password=191298
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
quarkus.datasource.jdbc.driver=oracle.jdbc.OracleDriver

quarkus.datasource.devservices.enabled=false
quarkus.hibernate-orm.database.generation=none

quarkus.http.port=8080
quarkus.http.cors.enabled=true
quarkus.http.cors.origins=*
```

Recomendado para produção: usar variáveis de ambiente em vez de credenciais em arquivo.
Exemplos de variáveis (formato Quarkus):

```
QUARKUS_DATASOURCE_USERNAME=...
QUARKUS_DATASOURCE_PASSWORD=...
QUARKUS_DATASOURCE_JDBC_URL=jdbc:oracle:thin:@<host>:<porta>:<sid>
QUARKUS_HTTP_PORT=8080
```

---

## Como executar

### Dev mode (live reload)
Windows:
```
mvnw.cmd quarkus:dev
```
Linux/macOS:
```
./mvnw quarkus:dev
```
Dev UI: `http://localhost:8080/q/dev/`

### Empacotar (JAR)
```
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Uber‑jar
```
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

### Nativo (GraalVM/Container)
```
./mvnw package -Dnative
# ou sem GraalVM local
./mvnw package -Dnative -Dquarkus.native.container-build=true
./target/imrea-quarkus-1.0.0-SNAPSHOT-runner
```

### Docker (exemplos)
Dockerfiles em `src/main/docker/` (JVM, legacy-jar, native, native-micro).
Exemplo (JVM):
```
# após mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t imrea-quarkus:dev .
docker run --rm -p 8080:8080 \
  -e QUARKUS_DATASOURCE_USERNAME=$USER \
  -e QUARKUS_DATASOURCE_PASSWORD=$PASS \
  -e QUARKUS_DATASOURCE_JDBC_URL="jdbc:oracle:thin:@host:1521:orcl" \
  imrea-quarkus:dev
```

---

## Domínio e Modelos (resumo)
- Paciente (`T_IMREA_PACIENTE`): `id`, `nome`, `dataNascimento`, `cpf`, `email`
- Especialidade (`T_IMREA_ESPECIALIDADE`): `id`, `nome`
- Profissional (`T_IMREA_MEDICO`): `id`, `nome`, `crm`, `especialidade`
- Agendamento/Consulta (`T_IMREA_CONSULTA`): `id`, `paciente`, `profissional`, `dataAgendamento (DT_INICIO)`, `dataFim (DT_FIM)`, `status`, `modalidade`, `plataforma`
- Atendimento (`T_IMREA_ATENDIMENTO`): `id`, `consulta (Agendamento)`, `gravidade`, `abertura`, `encerramento`, `prioridade`

---

## Endpoints
Base path: `/api`

### Pacientes `/api/pacientes`
- GET `/` → lista todos
- GET `/{id}` → busca por id
- POST `/` → cria
- PUT `/{id}` → atualiza
- DELETE `/{id}` → remove

Exemplo de POST:
```
POST /api/pacientes
Content-Type: application/json
{
  "nome": "Ana Maria",
  "dataNascimento": "1990-05-20",
  "cpf": "12345678901",
  "email": "ana@example.com"
}
```

### Profissionais `/api/profissionais`
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`
Body de POST (exemplo):
```
{
  "nome": "Dr. João",
  "crm": "CRM1234",
  "especialidade": { "id": 1 }
}
```

### Especialidades `/api/especialidades`
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`
Body de POST:
```
{ "nome": "Fisiatria" }
```

### Agendamentos `/api/agendamentos`
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`
Body de POST (exemplo):
```
{
  "paciente": { "id": 1 },
  "profissional": { "id": 2 },
  "dataAgendamento": "2025-11-10T14:00:00",
  "dataFim": "2025-11-10T15:00:00",
  "status": "AGENDADO",
  "modalidade": "ONLINE",
  "plataforma": "Teams"
}
```

### Atendimentos `/api/atendimentos`
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`
Body de POST (exemplo):
```
{
  "consulta": { "id": 10 },
  "gravidade": 2,
  "abertura": "2025-11-10T14:05:00"
}
```

Curl de exemplo (listar pacientes):
```
curl http://localhost:8080/api/pacientes
```

---

## Regras de Negócio e Tratamento de Erros
- Validações de negócio e regras ficam nos Services (`br.com.fiap.service.*`).
- Conflitos como horário já ocupado podem lançar `HorarioOcupadoException` (HTTP 400).
- Recursos inexistentes geram `RecursoNaoEncontradoException` (HTTP 404).
- Outras `IllegalArgumentException` → HTTP 400.
- Demais erros → HTTP 500.

Formato de erro (via `GlobalExceptionMapper`):
```
{
  "status": 400,
  "mensagem": "Detalhe do erro"
}
```

---

## Testes
- Dependências de teste: `quarkus-junit5`, `rest-assured`.
- Exemplos em `src/test/java`.

Executar testes:
```
./mvnw test
```

---

## Scripts SQL
- `Sprint4Java.sql`
- `imrea-quarkus/Sprint4Java.sql`



