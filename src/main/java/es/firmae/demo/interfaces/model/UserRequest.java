package es.firmae.demo.interfaces.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements Serializable {

    private static final long serialVersionUID = -2363582990447004534L;

    private String gender;

    private NameRequest name;

    private String email;

    @JsonProperty(value = "nat")
    private String nationality;

    private DobRequest dob;

    @JsonProperty(value = "registered")
    private RecordRequest record;

    private LocationRequest location;

    private String phone;

    private String cell;

    private PictureRequest picture;

    private Boolean favorite;
}
