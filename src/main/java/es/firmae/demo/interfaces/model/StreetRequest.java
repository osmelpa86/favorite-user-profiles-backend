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
public class StreetRequest implements Serializable {

    private static final long serialVersionUID = 8346551842208511734L;

    private int number;
    private String name;
}
