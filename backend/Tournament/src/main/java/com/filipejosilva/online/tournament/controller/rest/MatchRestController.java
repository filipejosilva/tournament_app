package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.MatchDto;
import com.filipejosilva.online.tournament.converter.MatchToDto;
import com.filipejosilva.online.tournament.exception.MatchNotFoundException;
import com.filipejosilva.online.tournament.exception.PointNotFoundException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Point;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.MatchService;
import com.filipejosilva.online.tournament.service.PointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/match")
public class MatchRestController {
    private MatchService matchService;
    private PointService pointService;
    private MatchToDto matchToDto;

    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDto> getMatch(@PathVariable Integer id){

        try {
            Match match = matchService.get(id);
            MatchDto dto = matchToDto.convert(match);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (MatchNotFoundException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = {"/{id}/{pid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity winner(@PathVariable Integer id, @PathVariable Integer pid){

        try {
            Match match = matchService.get(id);
            Point point = pointService.get(pid);
            if(match.getPointp().contains(point)){
                match.setWinner(point);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }catch (MatchNotFoundException | PointNotFoundException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }
    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
    @Autowired
    public void setMatchToDto(MatchToDto matchToDto) {
        this.matchToDto = matchToDto;
    }
}
