package es.firmae.demo.interfaces.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureRequest implements Serializable {

    private static final long serialVersionUID = -1293434834194764403L;

    @JsonProperty(value = "large")
    private String largePicture;

    @JsonProperty(value = "medium")
    private String mediumPicture;

    @JsonProperty(value = "thumbnail")
    private String thumbnailPicture;
}
