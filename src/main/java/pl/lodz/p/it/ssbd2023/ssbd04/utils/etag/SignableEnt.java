package pl.lodz.p.it.ssbd2023.ssbd04.utils.etag;

import jakarta.json.bind.annotation.JsonbTransient;

public interface SignableEnt {
    @JsonbTransient
    String getPayload();
}
