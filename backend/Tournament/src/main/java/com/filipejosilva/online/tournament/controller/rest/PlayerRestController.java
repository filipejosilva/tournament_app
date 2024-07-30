package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.PlayerDto;
import com.filipejosilva.online.tournament.command.TournamentDto;
import com.filipejosilva.online.tournament.converter.PlayerToDto;
import com.filipejosilva.online.tournament.converter.TournamentToDto;
import com.filipejosilva.online.tournament.exception.DeckNotFoundException;
import com.filipejosilva.online.tournament.exception.PlayerNotFoundException;
import com.filipejosilva.online.tournament.model.Player;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.DeckService;
import com.filipejosilva.online.tournament.service.PlayerService;
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
@RequestMapping("api/player")
public class PlayerRestController {

    private PlayerService playerService;
    private DeckService deckService;
    private PlayerToDto playerToDto;
    private TournamentToDto tournamentToDto;

    @RequestMapping(method = RequestMethod.GET, path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDto>> getList(){

        List<PlayerDto> list = playerToDto.convertList(playerService.list());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDto> get(@PathVariable Integer id){

        try {
            Player player = playerService.get(id);
            PlayerDto dto = playerToDto.convert(player);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (PlayerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@Valid @RequestBody Player player , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(player.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player newPlayer = player;
        playerService.addPlayer(newPlayer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@Valid @RequestBody Player player , BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Player newPlayer = playerService.get(player.getId());
            newPlayer.setMainDeck(player.getMainDeck());
            newPlayer.setNickname(player.getNickname());
            playerService.addPlayer(newPlayer);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (PlayerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePlayer(@PathVariable Integer id){
        try {
            playerService.removePlayer(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (PlayerNotFoundException e){
            //JSON TO DELETE DECKS
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/{did}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addDeck(@PathVariable Integer id, @PathVariable Integer did){
        try {
            /* Checking if the player is in the db */
            playerService.get(id);
            deckService.get(did);

            playerService.addDeck(id, did);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (PlayerNotFoundException | DeckNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}/{did}")
    public ResponseEntity removeDeck(@PathVariable Integer id, @PathVariable Integer did){
        try {
            playerService.get(id);
            deckService.get(did);

            playerService.removeDeck(id, did);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (PlayerNotFoundException | DeckNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(method = RequestMethod.GET, path = "/{id}/tournaments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TournamentDto>> getTournamentsList(@PathVariable Integer id){

        try {
            Player player = playerService.get(id);
            List<TournamentDto> tournaments = tournamentToDto.convertList(player.getTournaments());
            return new ResponseEntity<>(tournaments, HttpStatus.OK);
        }catch (PlayerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
    @Autowired
    public void setDeckService(DeckService deckService) {
        this.deckService = deckService;
    }
    @Autowired
    public void setPlayerToDto(PlayerToDto playerToDto) {
        this.playerToDto = playerToDto;
    }
    @Autowired
    public void setTournamentToDto(TournamentToDto tournamentToDto) {
        this.tournamentToDto = tournamentToDto;
    }
}
