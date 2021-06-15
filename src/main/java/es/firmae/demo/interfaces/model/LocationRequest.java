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
public class LocationRequest implements Serializable {

    private static final long serialVersionUID = 2907577801416586972L;

    private StreetRequest street;
    private String city;
    private String state;
    private String country;
    private long postcode;
    private CoordinatesRequest coordinates;
    private TimezoneRequest timezone;
}
