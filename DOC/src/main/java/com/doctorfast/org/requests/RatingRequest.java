package com.doctorfast.org.requests;

import lombok.Data;

@Data
public class RatingRequest {

    private String calificacion;

    private Integer doctorId;
}
