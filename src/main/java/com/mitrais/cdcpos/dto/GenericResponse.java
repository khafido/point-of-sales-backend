package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GenericResponse {
    private Object result;
    private String message;
    private Status status;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public GenericResponse(Object result, String message) {
        this.result = result;
        this.message = message;
    }

    public enum Status {
        SUCCESS,
        ERROR_INPUT,
        ERROR_NOT_FOUND,
        ERROR_INTERNAL
    }
}
