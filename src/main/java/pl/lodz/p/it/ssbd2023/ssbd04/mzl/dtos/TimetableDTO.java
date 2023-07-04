package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDTO {
    private AlgorithmConfigDTO config;

    private List<String> leagues;

    private List<String> venues;
}
