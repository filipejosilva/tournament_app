package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.command.PlayerDto;
import com.filipejosilva.online.tournament.command.TournamentDto;
import com.filipejosilva.online.tournament.converter.PlayerToDto;
import com.filipejosilva.online.tournament.converter.TournamentToDto;
import com.filipejosilva.online.tournament.exception.RegisterErrorException;
import com.filipejosilva.online.tournament.exception.TournamentNotFoundException;
import com.filipejosilva.online.tournament.model.Package;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.service.PlayerService;
import com.filipejosilva.online.tournament.service.TournamentService;
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
@RequestMapping("api/tournament")
public class TournamentRestController {

    private TournamentService tournamentService;
    private PlayerService playerService;
    private TournamentToDto tournamentToDto;
    private PlayerToDto playerToDto;

    @RequestMapping(method = RequestMethod.GET, path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TournamentDto>> getList(){

        List<TournamentDto> list = tournamentToDto.convertList(tournamentService.list());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDto> getTournament(@PathVariable Integer id){

        try {
            Tournament tournament = tournamentService.get(id);
            TournamentDto list = tournamentToDto.convert(tournament);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@Valid @RequestBody Tournament tournament , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(tournament.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Tournament tournamentNew = tournament;
        tournamentService.newTournament(tournamentNew);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity edit(@Valid @RequestBody Tournament tournament , BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(tournament.getId() == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Tournament tournamentNew = tournamentService.get(tournament.getId());
            tournamentNew.setName(tournament.getName());
            tournamentNew.setDate(tournament.getDate());
            tournamentService.updateTournament(tournamentNew);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Integer id){
        try {

            Tournament tournament = tournamentService.get(id);
            if(tournament.getStatus().equals("PLAY")|| tournament.getStatus().equals("FINISH")){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            tournamentService.deleteTournament(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/rounds")
    public ResponseEntity createRounds(@PathVariable Integer id){
        try {

            Tournament tournament = tournamentService.get(id);

            Package aPackage = new Package();

            if (tournament.getPlayers().size() < 4) {

                aPackage.setName("Error");
                aPackage.setMessage("The tournament has less than 4 players! Please add more players.");
                return new ResponseEntity<>(aPackage, HttpStatus.BAD_REQUEST);
            }

            aPackage.setName("Successful");
            aPackage.setMessage("Tournament created!");
            tournamentService.createRounds(tournament);

            return new ResponseEntity<>(aPackage, HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method = RequestMethod.PUT, path = {"/{id}/register/{pid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> getTournament(@PathVariable Integer id, @PathVariable Integer pid){

        try {
            tournamentService.addPlayers(id, pid);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RegisterErrorException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(method = RequestMethod.GET, path = {"/{id}/players"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDto>> getPlayerList(@PathVariable Integer id){
        try {
            Tournament tournament = tournamentService.get(id);
            List<PlayerDto> listPlayer = playerToDto.convertList(tournament.getPlayers());
            return new ResponseEntity<>(listPlayer, HttpStatus.OK);
        }catch (TournamentNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Autowired
    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
    @Autowired
    public void setTournamentToDto(TournamentToDto tournamentToDto) {
        this.tournamentToDto = tournamentToDto;
    }
    @Autowired
    public void setPlayerToDto(PlayerToDto playerToDto) {
        this.playerToDto = playerToDto;
    }
}
