package pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm;

import pl.lodz.p.it.ssbd2023.ssbd04.exceptions.BaseApplicationException;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models.AlgorithmConfig;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models.TeamScore;
import pl.lodz.p.it.ssbd2023.ssbd04.mzl.algorithm.models.Window;
import pl.lodz.p.it.ssbd2023.ssbd04.entities.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class Algorithm {
    private final Round round;
    private AlgorithmConfig config;
    private List<TeamScore> teams;
    private List<Venue> venues;

    public Algorithm(AlgorithmConfig config, List<Team> teams, List<Venue> venues, Round round) {
        this.config = config;
        this.venues = venues;
        this.round = round;

        this.teams = teams.stream().map(this::calculateTeamScore).toList();
    }

    private TeamScore calculateTeamScore(Team team) {
        var teamScore = new TeamScore();
        teamScore.setTeam(team);

        int score = 0;
        if (team.getOverallTeamScores() != null) {
            for (var teamOverall: team.getOverallTeamScores()) {
                score = teamOverall.getLostSets() +
                        teamOverall.getWonSets() * 5 +
                        teamOverall.getLostGames() * 3 +
                        teamOverall.getWonGames() * 10;
            }
        }
        teamScore.setMMR(score);
        return teamScore;
    }
    private int getExpectedGames(int numberOfTeams) {
        int resault = 0;
        for (int i = 0; i < numberOfTeams; i++) {
            resault += i;
        }
        return resault;
    }

    private List<Window> generateWindows(int numberOfTeams, List<Venue> venues) {
        var windows = new ArrayList<Window>();

        if (this.config.getStartDate().after(this.config.getEndDate())) {
            throw BaseApplicationException.timetableStartDateAfterEndDate();
        }

        int weekDay = 0;
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTimeZone(TimeZone.getTimeZone("GMT"));
        currentDay.setTime(this.config.getStartDate());
        var dayFormater = new SimpleDateFormat("EEEE");
        int weekWindows = 0;

        var numberOfGamesInDay = (config.getDayEnd().getTime() - config.getDayStart().getTime())
                / config.getRoundMaxTime().getTime();

        while (!currentDay.getTime().after(config.getEndDate())) {
            if (config.getExpectedDaysFilterValues().contains(dayFormater.format(currentDay.getTime()))) {
                currentDay.set(Calendar.HOUR_OF_DAY, config.getDayStart().getHours());
                currentDay.set(Calendar.MINUTE, config.getDayStart().getMinutes());
                currentDay.set(Calendar.SECOND, config.getDayStart().getSeconds());
                for (int i = 0; i < numberOfGamesInDay; i++) {
                    for (var venue : venues) {
                        var window = new Window();
                        window.setDate(currentDay.getTime());
                        window.setVenue(venue);
                        windows.add(window);
                        weekWindows++;
                    }
                    currentDay.add(Calendar.HOUR_OF_DAY, config.getRoundMaxTime().getHours());
                    currentDay.add(Calendar.MINUTE, config.getRoundMaxTime().getMinutes());
                    currentDay.add(Calendar.SECOND, config.getRoundMaxTime().getSeconds());
                }
            }
            currentDay.add(Calendar.DATE, 1);
            if (weekDay == 6) {
                if (weekWindows < numberOfTeams) {
                    throw BaseApplicationException.timetableNotEnoughFreeSpace();
                }
                weekWindows = 0;
                weekDay = 0;
            } else {
                weekDay++;
            }
        }

        return windows;
    }

    public List<Game> calculateTimetable() {
        var numberOfTeams = teams.size();
        int numberOfWindows = 0;


        Map<UUID, List<TeamScore>> leagues = new HashMap<>();
        for (var team: teams) {
            if (leagues.containsKey(team.getTeam().getLeague().getId())) {
                leagues.get(team.getTeam().getLeague().getId()).add(team);
            } else {
                leagues.put(team.getTeam().getLeague().getId(), new ArrayList<>(List.of(team)));
            }
        }
        List<Integer> sizeOfTeams = new ArrayList<>(leagues.values().stream().map(List::size).toList());
        Collections.sort(sizeOfTeams);
        var maxTeamInLeague = sizeOfTeams.stream().findFirst().get();
        var expectedWindows = generateWindows(maxTeamInLeague, venues);

        if (expectedWindows.size() < getExpectedGames(teams.size())) {
            throw BaseApplicationException.timetableNotEnoughFreeSpace();
        }

        String pattern = "yyyyMMdd";
        SimpleDateFormat dayFormat = new SimpleDateFormat(pattern);

        var daysWindows = new HashMap<String, List<Window>>();
        for (var window: expectedWindows) {
            var day = dayFormat.format(window.getDate());
            if (daysWindows.containsKey(day)) {
                daysWindows.get(day).add(window);
            } else {
                daysWindows.put(day, new ArrayList<>(List.of(window)));
            }
        }

        var days = new ArrayList<>(daysWindows.keySet().stream().toList());
        days.sort(Comparator.naturalOrder());

        for (var leagueTeams: leagues.values()) {
            if (leagueTeams.size() < 2) {
                throw BaseApplicationException.timetableLittleLeague();
            }
            leagueTeams.sort(Comparator.comparingInt(TeamScore::getMMR));

            List<List<TeamScore>> games = new ArrayList<>();
            for (int teamId = 0; teamId < leagueTeams.size() - 1; teamId++) {
                for (int enemyTeamId = teamId + 1; enemyTeamId < leagueTeams.size(); enemyTeamId++) {
                    games.add(List.of(leagueTeams.get(teamId), leagueTeams.get(enemyTeamId)));
                }
            }


            int i = 0;
            while (i < games.size()) {
                var game = games.get(i);
                for (var day: days) {
                    var isNextDay = true;
                    for (var dayWindow: daysWindows.get(day)) {
                        if (dayWindow.getTeamA() != null) {
                            continue;
                        }
                        Calendar dateBefore = Calendar.getInstance();
                        dateBefore.setTimeZone(TimeZone.getTimeZone("GMT"));
                        dateBefore.setTime(dayWindow.getDate());
                        dateBefore.add(Calendar.HOUR_OF_DAY, (-2) * (config.getRoundMaxTime().getHours()));
                        dateBefore.add(Calendar.MINUTE, (-2) * (config.getRoundMaxTime().getMinutes()));
                        dateBefore.add(Calendar.SECOND, (-2) * (config.getRoundMaxTime().getSeconds()));

                        Calendar dateAfter = Calendar.getInstance();
                        dateAfter.setTimeZone(TimeZone.getTimeZone("GMT"));
                        dateAfter.setTime(dayWindow.getDate());
                        dateAfter.add(Calendar.HOUR_OF_DAY, 2 * (config.getRoundMaxTime().getHours()));
                        dateAfter.add(Calendar.MINUTE, 2 * (config.getRoundMaxTime().getMinutes()));
                        dateAfter.add(Calendar.SECOND, 2 * (config.getRoundMaxTime().getSeconds()));

                        var isCorrect = true;
                        for (var anotherDayWindow : daysWindows.get(day)) {
                            if (
                                anotherDayWindow != dayWindow &&
                                anotherDayWindow.getTeamA() != null &&
                                (
                                        anotherDayWindow.getTeamA() == game.get(0).getTeam() ||
                                        anotherDayWindow.getTeamB() == game.get(0).getTeam() ||
                                        anotherDayWindow.getTeamA() == game.get(1).getTeam() ||
                                        anotherDayWindow.getTeamB() == game.get(1).getTeam()
                                ) &&
                                (anotherDayWindow.getDate().after(dateBefore.getTime()) &&
                                anotherDayWindow.getDate().before(dateAfter.getTime()))
                            ) {
                                isCorrect = false;
                                break;
                            }
                        }
                        if (!isCorrect) {
                            continue;
                        }

                        dayWindow.setTeamA(game.get(0).getTeam());
                        dayWindow.setTeamB(game.get(1).getTeam());
                        isNextDay = false;
                        break;
                    }
                    if (isNextDay) {
                        continue;
                    }
                    break;
                }
                i++;
            }

            round.addLeague(leagueTeams.get(0).getTeam().getLeague());
        }
        return expectedWindows.stream().filter(window -> window.getTeamA() != null).map(window -> {
            var game = new Game();
            game.setTeamA(new GameSquad(window.getTeamA()));
            game.setTeamB(new GameSquad(window.getTeamB()));
            game.setStartTime(window.getDate().toInstant()
                    .atZone(ZoneId.of("GMT"))
                    .toLocalDateTime());
            game.setQueue(1);
            game.setVenue(window.getVenue());
            game.setRound(round);
            round.addGame(game);
            return game;
        }).toList();
    }
}
