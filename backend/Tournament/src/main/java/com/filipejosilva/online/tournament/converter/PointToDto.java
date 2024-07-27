package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.PointDto;
import com.filipejosilva.online.tournament.model.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PointToDto {

    public PointDto convert(Point point){
        PointDto dto = new PointDto();

        dto.setId(point.getId());
        dto.setName(point.getPlayer().getNickname());
        dto.setScore(point.getScore());
        dto.setOMW(point.getOMW());

        return dto;
    }

    public List<PointDto> convertList(List<Point> listConvert){
        return listConvert.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }
}
