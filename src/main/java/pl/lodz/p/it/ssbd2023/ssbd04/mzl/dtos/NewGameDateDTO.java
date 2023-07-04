package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.AbstractDTO;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NewGameDateDTO extends AbstractDTO {
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime newDate;

    private long gameVersion;
}
