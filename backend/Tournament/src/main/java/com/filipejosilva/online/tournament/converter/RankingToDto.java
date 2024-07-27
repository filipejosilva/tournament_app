package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.RankingDto;
import com.filipejosilva.online.tournament.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RankingToDto {

    public List<RankingDto> convertList(Point [] ranking){
        List<RankingDto> rankingDto = new ArrayList<>();
        for(Point p: ranking){
            RankingDto newDto = new RankingDto();
            newDto.setName(p.getPlayer().getNickname());
            newDto.setPoints(p.getScore());
            newDto.setOMW(p.getOMW());
            rankingDto.add(newDto);
        }
        return rankingDto;
    }
}
