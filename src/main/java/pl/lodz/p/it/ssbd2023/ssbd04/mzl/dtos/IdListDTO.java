package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IdListDTO {
    private List<String> idList;
}
