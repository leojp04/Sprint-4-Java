package br.com.fiap.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// Mapeia as exceções personalizadas para respostas HTTP
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof HorarioOcupadoException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErroResponse(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()))
                    .build();
        }

        if (exception instanceof RecursoNaoEncontradoException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErroResponse(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage()))
                    .build();
        }

        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErroResponse(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage()))
                    .build();
        }

        // fallback para qualquer outro erro não tratado
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErroResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                        "Erro interno no servidor: " + exception.getMessage()))
                .build();
    }

    // Classe interna para resposta de erro
    public static class ErroResponse {
        private int status;
        private String mensagem;

        public ErroResponse(int status, String mensagem) {
            this.status = status;
            this.mensagem = mensagem;
        }

        public int getStatus() {
            return status;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
