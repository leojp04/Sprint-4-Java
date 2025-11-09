package br.com.fiap.resource;

import br.com.fiap.model.Atendimento;
import br.com.fiap.service.AtendimentoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/atendimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    @Inject
    AtendimentoService atendimentoService;

    @GET
    public List<Atendimento> listarTodos() {
        return atendimentoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Atendimento buscarPorId(@PathParam("id") Integer id) {
        return atendimentoService.buscarPorId(id);
    }

    @POST
    @Transactional
    public Response criar(Atendimento atendimento) {
        Atendimento salvo = atendimentoService.registrar(atendimento);
        return Response.ok(salvo).build();
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Atendimento atualizar(@PathParam("id") Integer id, Atendimento atendimento) {
        return atendimentoService.atualizar(id, atendimento);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Integer id) {
        atendimentoService.deletar(id);
        return Response.noContent().build();
    }
}
