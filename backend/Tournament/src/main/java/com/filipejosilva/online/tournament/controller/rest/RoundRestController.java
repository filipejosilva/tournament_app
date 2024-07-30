package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.MatchDto;
import com.filipejosilva.online.tournament.command.RoundDto;
import com.filipejosilva.online.tournament.converter.RoundToDto;
import com.filipejosilva.online.tournament.exception.*;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Package;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.MatchService;
import com.filipejosilva.online.tournament.service.RoundService;
import com.filipejosilva.online.tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/round")
public class RoundRestController {

    private RoundService roundService;
    private RoundToDto roundToDto;
    private TournamentService tournamentService;
    private MatchService matchService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MatchDto>> getRound(@PathVariable Integer id){

        try {
            Round round = roundService.get(id);
            RoundDto roundDto = roundToDto.convert(round);
            return new ResponseEntity<>(roundDto.getMatches(), HttpStatus.OK);
        }catch ( RoundNotFoundException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/tournament/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoundDto>> getRounds(@PathVariable Integer id){

        try {
            Tournament tournament = tournamentService.get(id);
            List<RoundDto> roundDto = roundToDto.convertList(tournament.getRounds());
            return new ResponseEntity<>(roundDto, HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            e.getMessage();
            //JSON TO RETURN FOR SITE ERROR INFORMATION
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Package> updateRound(@PathVariable Integer id){

        try {
            Round round = roundService.get(id);
            matchService.checkMatches(round);
            roundService.updateRound(round);
            Package aPackage = new Package();
            aPackage.setMessage("Updated successful");
            aPackage.setName("Successful");
            return new ResponseEntity<>(aPackage, HttpStatus.OK);
        }catch (RoundNotFoundException | MatchNotFinishException e){
            Package aPackage = new Package();
            aPackage.setMessage("Theres matches still in progress! Don't forget to register the results");
            aPackage.setName("Error");
            return new ResponseEntity<>(aPackage, HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }
    @Autowired
    public void setRoundToDto(RoundToDto roundToDto) {
        this.roundToDto = roundToDto;
    }
    @Autowired
    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
    @Autowired
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }
}
