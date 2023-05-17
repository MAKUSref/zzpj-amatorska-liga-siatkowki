package pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@AllArgsConstructor
public abstract class AbstractDTO implements Serializable {

    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private long version;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this.getClass() != object.getClass()) {
            return false;
        }
        AbstractDTO other = (AbstractDTO) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }

    public AbstractDTO() {
    }

}