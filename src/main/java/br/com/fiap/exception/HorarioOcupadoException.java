package br.com.fiap.exception;

public class HorarioOcupadoException extends RuntimeException {
    public HorarioOcupadoException(String message) {
        super(message);
    }

    public HorarioOcupadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
