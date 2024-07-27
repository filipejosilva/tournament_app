package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.DeckDto;
import com.filipejosilva.online.tournament.converter.DeckToDto;
import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.service.DeckService;
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
@RequestMapping("api/deck")
public class DeckRestController {
    private DeckService deckService;
    private DeckToDto deckToDto;

    @RequestMapping(method = RequestMethod.GET, path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeckDto>> getDeck(){

        List<DeckDto> list= deckToDto.convertList(deckService.getList());


        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckDto> getDeck(@PathVariable Integer id){

        try {
            Deck deck = deckService.get(id);
            DeckDto deckDto = deckToDto.convert(deck);


            return new ResponseEntity<>(deckDto, HttpStatus.OK);
        }catch (DeckNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@Valid @RequestBody Deck deck , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(deck, HttpStatus.BAD_REQUEST);
        }

        Deck newDeck = deck;
        deckService.add(newDeck);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/delete")
    public ResponseEntity delete(@PathVariable Integer id){
        try {
            Deck delete = deckService.get(id);
            deckService.remove(delete.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DeckNotFoundException e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
    @Autowired
    public void setDeckService(DeckService deckService){
        this.deckService = deckService;
    }
    @Autowired
    public void setDeckToDto(DeckToDto deckToDto) {
        this.deckToDto = deckToDto;
    }
}
