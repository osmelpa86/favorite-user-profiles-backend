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
public class NameRequest implements Serializable {

    private static final long serialVersionUID = 798218101722714008L;

    private String title;
    private String first;
    private String last;
}
