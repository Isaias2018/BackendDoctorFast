package com.doctorfast.org.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RatingResponse {

    private Integer idRating;

    private String calificacion;

    private Integer doctorId;

}
