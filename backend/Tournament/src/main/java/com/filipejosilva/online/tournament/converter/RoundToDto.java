package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.MatchDto;
import com.filipejosilva.online.tournament.command.RoundDto;
import com.filipejosilva.online.tournament.model.Match;
import com.filipejosilva.online.tournament.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoundToDto {
    private MatchToDto matchToDto;

    public RoundDto convert(Round round){
        RoundDto roundDto = new RoundDto();

        roundDto.setId(round.getId());
        roundDto.setStatus(round.getStatus());
        roundDto.setMatches(matchToDto.convertList(round.getMatches()));

        return roundDto;
    }

    public List<RoundDto> convertList(List<Round> listConvert){
        return listConvert.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }

    @Autowired
    public void setMatchToDto(MatchToDto matchToDto) {
        this.matchToDto = matchToDto;
    }
}
