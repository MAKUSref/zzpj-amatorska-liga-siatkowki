package pl.lodz.p.it.ssbd2023.ssbd04.mok.mappers;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.*;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.CaptainDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.CoachDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.ManagerDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mok.dtos.RoleDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.Game;

import java.util.*;
import java.util.stream.Collectors;

public class RoleDTOMapper {
    public static ManagerDTO managerToDTO(Manager manager){
        if (manager.getTeam() == null) {
            return new ManagerDTO(manager.getId(), manager.getVersion(), null);
        }
        return new ManagerDTO(manager.getId(), manager.getVersion(), manager.getTeam().getId());
    }

    private static RoleDTO adminToDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getVersion());

    }

    private static RoleDTO captainToDTO(Captain captain) {
        if (captain.getTeam() == null) {
            return new CaptainDTO(captain.getId(), captain.getVersion(), null);
        }
        return new CaptainDTO(captain.getId(), captain.getVersion(), captain.getTeam().getId());
    }

    private static RoleDTO refereeToDTO(Referee referee) {
        List<UUID> gamesId = referee.getGames().stream().map(Game::getId).toList();
        return new RefereeDTO(referee.getId(), referee.getVersion(), gamesId);
    }

    private static RoleDTO coachToDTO(Coach coach) {
        if (coach.getTeam() == null) {
            return new CoachDTO(coach.getId(), coach.getVersion(), null);
        }
        return new CoachDTO(coach.getId(), coach.getVersion(), coach.getTeam().getId());
    }

    public static Manager DTOToManager(ManagerDTO managerDTO){
        return new Manager(null);
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
            case ("ADMIN"):
                role = new Admin();
                break;
            default:
                throw BaseApplicationException.incorrectRole();
        }
        BeanUtils.copyProperties(role, roleDTO);

        return role;
    }

    public static RoleDTO roleToDTO(Role role) {
        if (role instanceof Manager) {
            return managerToDTO((Manager) role);
        }

        if (role instanceof Coach) {
            return coachToDTO((Coach) role);
        }

        if (role instanceof Referee) {
            return refereeToDTO((Referee) role);
        }

        if (role instanceof Captain) {
            return captainToDTO((Captain) role);
        }

        if (role instanceof Admin) {
            return adminToDTO((Admin) role);
        }

        return null;
    }

    public static List<RoleDTO> roleListToRoleDTOList(Collection<Role> roles) {
        return null == roles ? null : roles.stream()
                .filter(Objects::nonNull)
                .map(RoleDTOMapper::roleToDTO)
                .collect(Collectors.toList());
    }
}
