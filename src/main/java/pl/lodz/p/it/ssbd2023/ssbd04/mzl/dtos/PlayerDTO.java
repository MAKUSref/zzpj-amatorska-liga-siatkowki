package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO extends AbstractDTO {
    String name;
    String lastName;
    int age;
    boolean isPro;

    public PlayerDTO(UUID id, long version, String name, String lastName, int age, boolean isPro) {
        super(id, version);
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isPro = isPro;
    }

    public PlayerDTO(UUID id, long version, String name) {
        super(id, version);
        this.name = name;
    }
}
