package es.firmae.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class FavoritesProfile {

    @Id
    @Column
    private String nickname;

    @Lob
    @Column(name = "favorites_json", columnDefinition="longblob")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] favoritesProfileList;
}
