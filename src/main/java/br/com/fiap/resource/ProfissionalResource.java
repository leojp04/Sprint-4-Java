package br.com.fiap.resource;

import br.com.fiap.model.Profissional;
import br.com.fiap.service.ProfissionalService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/profissionais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfissionalResource {

    @Inject
    ProfissionalService profissionalService;

    @GET
    public List<Profissional> listarTodos() {
        return profissionalService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Profissional buscarPorId(@PathParam("id") Integer id) {
        return profissionalService.buscarPorId(id);
    }

    @POST
    @Transactional
    public Response criar(Profissional profissional) {
        Profissional salvo = profissionalService.salvar(profissional);
        return Response.ok(salvo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Profissional atualizar(@PathParam("id") Integer id, Profissional profissional) {
        return profissionalService.atualizar(id, profissional);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Integer id) {
        profissionalService.deletar(id);
        return Response.noContent().build();
    }
}
