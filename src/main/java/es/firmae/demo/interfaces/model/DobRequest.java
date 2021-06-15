package es.firmae.demo.interfaces.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DobRequest implements Serializable {

    private static final long serialVersionUID = -1293434834194764403L;

    private String date;
    private int age;
}
