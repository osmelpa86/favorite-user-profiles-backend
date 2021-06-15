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
public class TimezoneRequest implements Serializable {

    private static final long serialVersionUID = 7360492760035612867L;

    private String offset;
    private String description;
}
