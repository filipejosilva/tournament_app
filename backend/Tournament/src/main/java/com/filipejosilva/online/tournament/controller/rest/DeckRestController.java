package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.DeckDto;
import com.filipejosilva.online.tournament.converter.DeckToDto;
import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Deck;
import com.filipejosilva.online.tournament.model.Package;
import com.filipejosilva.online.tournament.model.Player;
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
    public ResponseEntity<Package> add(@Valid @RequestBody Deck deck , BindingResult bindingResult){
        Package aPackage = new Package();
        if (bindingResult.hasErrors()){
            aPackage.setName("Error");
            aPackage.setMessage("Something went wrong, try again");
            return new ResponseEntity<>(aPackage, HttpStatus.BAD_REQUEST);
        }

        if(deck.getId() != null){
            aPackage.setName("Error");
            aPackage.setMessage("Something went wrong");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Deck newDeck = deck;

        List<Deck> list = deckService.getList();
        for(Deck d : list){
            if(newDeck.getLeader().equals(d.getLeader())){
                aPackage.setName("Error");
                aPackage.setMessage("Deck is already in");
                return new ResponseEntity<>(aPackage, HttpStatus.BAD_REQUEST);
            }
        }

        deckService.add(newDeck);
        aPackage.setName("Successful");
        aPackage.setMessage("Deck register");
        return new ResponseEntity<>(aPackage, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Package> delete(@PathVariable Integer id){
        try {
            Deck delete = deckService.get(id);

            Package send = new Package();
            if(!delete.getPlayers().isEmpty()){

                send.setName("Error");
                send.setMessage("Theres 1 or more players with this deck on their profile");
                return new ResponseEntity<>(send, HttpStatus.BAD_REQUEST);
            }
            send.setName("Successful");
            send.setMessage("Deck Removed");
            deckService.remove(delete.getId());

            return new ResponseEntity<>(send, HttpStatus.OK);
        }catch (DeckNotFoundException e){
            Package send = new Package();
            send.setName("Error");
            send.setMessage(e.getMessage());
            return new ResponseEntity<>(send, HttpStatus.BAD_REQUEST);
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
