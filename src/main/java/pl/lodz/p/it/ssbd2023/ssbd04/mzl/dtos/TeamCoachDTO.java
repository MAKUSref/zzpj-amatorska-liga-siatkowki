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
public class TeamCoachDTO extends TeamRepresentativeDTO {
    private String firstName;
    private String lastName;

    public TeamCoachDTO(UUID id, long version, String firstName, String lastName) {
        super(id, version, "COACH");
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
