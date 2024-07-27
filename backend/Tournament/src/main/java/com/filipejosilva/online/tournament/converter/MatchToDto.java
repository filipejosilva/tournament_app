package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.MatchDto;
import com.filipejosilva.online.tournament.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchToDto {
    private PointToDto pointToDto;

    public MatchDto convert(Match match){
        MatchDto dto = new MatchDto();

        dto.setId(match.getId());
        dto.setStatus(match.getStatus());
        dto.setPlayers(pointToDto.convertList(match.getPointp()));

        if(match.getStatus().equals("FINISH")){
            dto.setWinner(pointToDto.convert(match.getWinner()));
        }

        return dto;
    }

    public List<MatchDto> convertList(List<Match> listConvert){
        return listConvert.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }

    @Autowired
    public void setPointToDto(PointToDto pointToDto) {
        this.pointToDto = pointToDto;
    }
}
