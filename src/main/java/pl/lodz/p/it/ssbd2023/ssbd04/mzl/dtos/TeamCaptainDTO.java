package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class TeamCaptainDTO extends TeamRepresentativeDTO {

    private String firstName;
    private String lastName;
    public TeamCaptainDTO(UUID id, long version, String firstName, String lastName) {
        super(id, version, "CAPTAIN");
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
