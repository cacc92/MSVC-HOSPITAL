package com.ccarrasco.msvc.medicos.dtos;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter

public class ErrorDTO {

    private Integer status;

    private Date date;

    private Map<String, String> errors;


    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", date=" + date +
                ", errors=" + errors +
                '}';
    }
}
