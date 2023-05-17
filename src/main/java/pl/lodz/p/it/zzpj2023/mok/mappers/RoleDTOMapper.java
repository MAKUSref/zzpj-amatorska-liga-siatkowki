package pl.lodz.p.it.zzpj2023.mok.mappers;

import jakarta.ws.rs.NotSupportedException;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.it.zzpj2023.mok.dtos.CaptainDTO;
import pl.lodz.p.it.zzpj2023.mok.dtos.CoachDTO;
import pl.lodz.p.it.zzpj2023.mok.dtos.ManagerDTO;
import pl.lodz.p.it.zzpj2023.mok.dtos.RoleDTO;
import pl.lodz.p.it.zzpj2023.mok.entities.*;
import pl.lodz.p.it.zzpj2023.mok.entities.*;

public class RoleDTOMapper {
    public static ManagerDTO managerToDTO(Manager manager){
        return new ManagerDTO(manager.getId(), manager.getVersion(), manager.getTeam().getId());
    }

    public static Manager DTOToManager(ManagerDTO managerDTO){
        return new Manager(null);
    }
    public static Coach DTOToCoach(CoachDTO coachDTO){
        return new Coach(null);
    }
    public static Captain DTOToCaptain(CaptainDTO captainDTO){
        return new Captain(null);
    }

    @SneakyThrows
    public static Role DTOToRole(RoleDTO roleDTO) {
        Role role;
        switch (roleDTO.getRole()) {
            case ("MANAGER"):
                role = new Manager();
                break;
            case ("COACH"):
                role = new Coach();
                break;
            case ("REFEREE"):
                role = new Referee();
                break;
            case ("CAPTAIN"):
                role = new Captain();
                break;
            default:
                throw new NotSupportedException();
        }
        BeanUtils.copyProperties(role, roleDTO);

        return role;
    }
}
