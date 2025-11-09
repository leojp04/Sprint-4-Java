package br.com.fiap.resource;

import br.com.fiap.model.Paciente;
import br.com.fiap.service.PacienteService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    PacienteService pacienteService;

    @GET
    public List<Paciente> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Paciente buscarPorId(@PathParam("id") Integer id) {
        return pacienteService.buscarPorId(id);
    }

    @POST
    @Transactional
    public Response criar(Paciente paciente) {
        Paciente salvo = pacienteService.salvar(paciente);
        return Response.ok(salvo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Paciente atualizar(@PathParam("id") Integer id, Paciente paciente) {
        return pacienteService.atualizar(id, paciente);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Integer id) {
        pacienteService.deletar(id);
        return Response.noContent().build();
    }
}
