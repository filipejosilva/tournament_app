package com.filipejosilva.online.tournament.converter;

import com.filipejosilva.online.tournament.command.DeckDto;
import com.filipejosilva.online.tournament.command.PlayerDto;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerToDto {
    private DeckToDto deckToDto;

    public PlayerDto convert(Player player){
        PlayerDto dto = new PlayerDto();

        dto.setId(player.getId());
        dto.setName(player.getNickname());
        dto.setMainDeck(player.getMainDeck());
        dto.setTournaments(player.getTournaments().size());
        dto.setOther(deckToDto.convertList(player.getDecks()));

        return dto;
    }

    public List<PlayerDto> convertList(List<Player> listConvert){
        return listConvert.stream()
                .map(this::convert)
                .collect(Collectors.toList());

    }

    @Autowired
    public void setDeckToDto(DeckToDto deckToDto) {
        this.deckToDto = deckToDto;
    }
}
