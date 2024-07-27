package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.TournamentDto;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Ranking;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.RankingService;
import com.filipejosilva.online.tournament.service.RankingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TournamentToDto {

    private RankingService rankingService;

    public TournamentDto convert(Tournament tournament){

        try{
            TournamentDto dto = new TournamentDto();

            dto.setId(tournament.getId());
            dto.setName(tournament.getName());
            dto.setStatus(tournament.getStatus());
            dto.setDate(tournament.getDate());

            //Get the winner

            if(tournament.getStatus().equals("CLOSED")){
                Ranking ranking = rankingService.getRankings(tournament.getId());
                dto.setWinner(ranking.getRanking()[0].getPlayer().getNickname());
            }
            dto.setPlayers(tournament.getPlayers().size());

            return dto;
        }catch (TournamentNotFoundException e){
            e.getMessage();
            //throw something to get caugth in the rest api
            return null;
        }

    }

    public List<TournamentDto> convertList(List<Tournament> listConvert){
            return listConvert.stream()
                    .map(this::convert)
                    .collect(Collectors.toList());

    }

    @Autowired
    public void setRankingService(RankingService rankingService) {
        this.rankingService = rankingService;
    }
}
