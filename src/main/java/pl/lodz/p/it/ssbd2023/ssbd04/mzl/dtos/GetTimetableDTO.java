package pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2023.ssbd04.utils.etag.SignableEnt;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetTimetableDTO implements SignableEnt {
    private UUID id;
    private long version;
    private Date startDate;
    private Date endDate;
    private List<UUID> roundList;

    @Override
    public String getPayload() {
        return this.getId().toString() + this.getVersion();
    }
}