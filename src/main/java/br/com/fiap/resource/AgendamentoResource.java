package br.com.fiap.resource;

import br.com.fiap.model.Agendamento;
import br.com.fiap.service.AgendamentoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService agendamentoService;

    @GET
    public List<Agendamento> listarTodos() {
        return agendamentoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Agendamento buscarPorId(@PathParam("id") Integer id) {
        return agendamentoService.buscarPorId(id);
    }

    @POST
    @Transactional
    public Response criar(Agendamento agendamento) {
        Agendamento salvo = agendamentoService.criar(agendamento);
        return Response.ok(salvo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Agendamento atualizar(@PathParam("id") Integer id, Agendamento agendamento) {
        return agendamentoService.atualizar(id, agendamento);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Integer id) {
        agendamentoService.deletar(id);
        return Response.noContent().build();
    }
}
