package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.MatchDto;
import com.filipejosilva.online.tournament.command.RoundDto;
import com.filipejosilva.online.tournament.converter.RoundToDto;
import com.filipejosilva.online.tournament.exception.MatchNotFoundException;
import com.filipejosilva.online.tournament.exception.RoundNotFoundException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.model.Tournament;
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRound(@PathVariable Integer id){

        try {
            Round round = roundService.get(id);
            roundService.updateRound(round);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch ( RoundNotFoundException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
