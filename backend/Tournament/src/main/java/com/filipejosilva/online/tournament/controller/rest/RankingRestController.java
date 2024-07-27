package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.RankingDto;
import com.filipejosilva.online.tournament.converter.RankingToDto;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Ranking;
import com.filipejosilva.online.tournament.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/ranking")
public class RankingRestController {

    private RankingService rankingService;
    private RankingToDto rankingToDto;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingDto>> getRankings(@PathVariable Integer id) throws TournamentNotFoundException {

        Ranking ranking;
        try{
            ranking = rankingService.getRankings(id);
        }catch (TournamentNotFoundException e){
            e.getMessage();
            return null;
        }
        List<RankingDto> rankingDto = rankingToDto.convertList(ranking.getRanking());
        return new ResponseEntity<>(rankingDto, HttpStatus.OK);
    }

    @Autowired
    public void setRankingService(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    @Autowired
    public void setRankingToDto(RankingToDto rankingToDto) {
        this.rankingToDto = rankingToDto;
    }
}
