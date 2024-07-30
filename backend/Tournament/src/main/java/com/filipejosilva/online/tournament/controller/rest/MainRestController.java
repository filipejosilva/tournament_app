package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.exception.*;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Ranking;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/app")
public class MainRestController {
    private PointService pointService;
    private TournamentService tournamentService;
    private RoundService roundService;
    private MatchService matchService;
    private RankingService rankingtService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test(@PathVariable Integer id) throws RoundNotFinishException, TournamentNotFoundException {
        /* this should be the new tournament method
        pointService.createPointsForTournament(tournamentService.get(id));
        Tournament tournament = tournamentService.get(id);
        roundService.createRound(tournament);
         */
        Tournament tournament = tournamentService.get(id);
        tournamentService.createRounds(tournament);
        return new ResponseEntity<>(tournament, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity tests() throws MatchNotFoundException, RoundNotFoundException, TournamentNotFoundException {
        Tournament tournament = null;
        try {
            tournament = tournamentService.get(1);
        } catch (TournamentNotFoundException e) {
            throw new RuntimeException(e);
        }
        Ranking ranking = rankingtService.getRankings(tournament.getId());
        Match match = matchService.get(5);
        match.setStatus("FINISH");
        match.setWinner(match.getPointp().get(0));
        matchService.updateMatch(match);
        roundService.updateRound(roundService.get(3));
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testss() throws MatchNotFoundException, RoundNotFoundException {
        Match match = matchService.get(7);
        match.setStatus("FINISH");
        match.setWinner(match.getPointp().get(0));
        matchService.updateMatch(match);
        roundService.updateRound(roundService.get(4));
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
    @Autowired
    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
    @Autowired
    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }
    @Autowired
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }
    @Autowired
    public void setRankingtService(RankingService rankingtService) {
        this.rankingtService = rankingtService;
    }
}
