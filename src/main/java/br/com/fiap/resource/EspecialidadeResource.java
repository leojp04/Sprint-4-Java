package br.com.fiap.resource;

import br.com.fiap.model.Especialidade;
import br.com.fiap.service.EspecialidadeService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/especialidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialidadeResource {

    @Inject
    EspecialidadeService especialidadeService;

    @GET
    public List<Especialidade> listarTodos() {
        return especialidadeService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Especialidade buscarPorId(@PathParam("id") Integer id) {
        return especialidadeService.buscarPorId(id);
    }

    @POST
    @Transactional
    public Response criar(Especialidade especialidade) {
        Especialidade salvo = especialidadeService.salvar(especialidade);
        return Response.ok(salvo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Especialidade atualizar(@PathParam("id") Integer id, Especialidade especialidade) {
        return especialidadeService.atualizar(id, especialidade);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Integer id) {
        especialidadeService.deletar(id);
        return Response.noContent().build();
    }
}
