package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.DeckDto;
import com.filipejosilva.online.tournament.command.TournamentDto;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.model.Tournament;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeckToDto {

    public DeckDto convert(Deck deck){
        DeckDto dto = new DeckDto();

        dto.setId(deck.getId());
        dto.setName(deck.getLeader());
        dto.setNumber(deck.getPlayers().size());

        return dto;
    }

    public List<DeckDto> convertList(List<Deck> listConvert){
        return listConvert.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }
}
