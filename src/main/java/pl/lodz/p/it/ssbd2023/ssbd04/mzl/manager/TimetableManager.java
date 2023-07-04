package pl.lodz.p.it.ssbd2023.ssbd04.mzl.manager;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;
import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.MethodNotImplemented;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.GenericManagerExceptionsInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.Algorithm;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models.AlgorithmConfig;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.dtos.TimetableDTO;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.facade.TimetableFacade;

import java.sql.Time;
import java.util.*;
import java.util.logging.Logger;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({GenericManagerExceptionsInterceptor.class, TrackerInterceptor.class})
public class TimetableManager {
    @Inject
    TimetableFacade timetableFacade;

    @Inject
    GameManager gameManager;

    @Inject
    LeagueManager leagueManager;

    @Inject
    VenueManager venueManager;

    @PermitAll
    public Timetable getTimetable(UUID id) {
        return timetableFacade.find(id);
    }

    @PermitAll
    public List<Timetable> findAll() {
        return timetableFacade.findAll();
    }

    @RolesAllowed("generateTimetable")
    public void generateTimetable(TimetableDTO timetableDTO) {
        var teams = leagueManager.getAllLeaguesByUUIDs(timetableDTO.getLeagues().stream().map(UUID::fromString).toList());
        if (teams.size() == 0) {
            throw BaseApplicationException.timetableUseLeague();
        }
        var venues = venueManager.getAllVenueByIds(timetableDTO.getVenues().stream().map(UUID::fromString).toList());
        if (venues.size() == 0) {
            throw BaseApplicationException.timetableUseVenue();
        }

        var round1 = new Round(1);
        var round2 = new Round(2);

        var timetable = new Timetable(round1, round2);

        round1.setTimetable(timetable);
        round2.setTimetable(timetable);

        var calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTime(new Date(timetableDTO.getConfig().getStartDate()));
        calendar.add(Calendar.YEAR, -1);

        var startDate = calendar.getTime();
        calendar.setTime(new Date(timetableDTO.getConfig().getEndDate()));
        calendar.add(Calendar.YEAR, -1);

        var endDate = calendar.getTime();

        busyCheck(startDate, endDate);

        // Calculating central date
        var centralDate = calculateCentralDate(startDate, endDate);
        // Calculation for 1 Round
        var games1 = generateRound(teams, venues, round1, startDate, centralDate, timetable);

        // Calculation for 2 Round
        var games2 = generateRound(teams, venues, round2, centralDate, endDate, timetable);

        timetable.setStartDate(startDate);
        timetable.setEndDate(endDate);

        timetableFacade.create(timetable);
    }

    private void busyCheck(Date startDate, Date endDate) {
        if (timetableFacade.getTimetablesBetween(startDate, endDate).size() > 0) {
            throw BaseApplicationException.timetableDatesAreBusy();
        }
    }

    private Date calculateCentralDate(Date startDate, Date endDate) {
        var calendar1 = Calendar.getInstance();
        var calendar2 = Calendar.getInstance();
        var calendar3 = Calendar.getInstance();

        calendar1.setTime(startDate);
        long startTimeInMillis = calendar1.getTimeInMillis();

        calendar2.setTime(endDate);
        long endTimeInMillis = calendar2.getTimeInMillis();

        long centralTimeInMillis = (startTimeInMillis + endTimeInMillis) / 2;

        calendar3.setTimeInMillis(centralTimeInMillis);
        return calendar3.getTime();
    }

    private List<Game> generateRound(List<Team> teams, List<Venue> venues, Round round1, Date startDate, Date centralDate, Timetable timetable) {
        Time roundMaxTime = new Time(1, 30, 0);
        var config = new AlgorithmConfig();
        config.setStartDate(startDate);
        config.setEndDate(centralDate);
        config.setRoundMaxTime(roundMaxTime);
        var algorithm = new Algorithm(config, teams, venues, round1);
        var games = algorithm.calculateTimetable();
        return games;
    }
}
