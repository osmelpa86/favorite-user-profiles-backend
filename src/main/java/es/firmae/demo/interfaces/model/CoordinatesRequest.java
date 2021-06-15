package es.firmae.demo.interfaces.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinatesRequest implements Serializable {

    private static final long serialVersionUID = 3205209674461429255L;

    private String latitude;
    private String longitude;
}
