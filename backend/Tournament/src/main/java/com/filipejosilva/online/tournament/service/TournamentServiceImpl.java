package com.filipejosilva.online.tournament.service;

import com.filipejosilva.online.tournament.exception.*;
import com.filipejosilva.online.tournament.model.Player;
import com.filipejosilva.online.tournament.model.Round;
import com.filipejosilva.online.tournament.model.Tournament;
import com.filipejosilva.online.tournament.persistence.TransactionManager;
import com.filipejosilva.online.tournament.persistence.dao.TournamentDao;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService {

    private TransactionManager tx;
    private TournamentDao tournamentDao;
    private RoundService roundService;
    private PlayerService playerService;
    private PointService pointService;
    private RankingService rankingService;

    @Override
    public Tournament get(int id) throws TournamentNotFoundException {
        return Optional.ofNullable(tournamentDao.findById(id)).orElseThrow(TournamentNotFoundException::new);
    }

    @Override
    public List<Tournament> list() {
        return tournamentDao.findAll();
    }

    @Override
    public void updateTournament(Tournament tournament) {

        try {
            tx.beginWrite();
            Tournament updateTournament = get(tournament.getId());

            updateTournament.setDate(tournament.getDate());
            updateTournament.setName(tournament.getName());

            tournamentDao.saveOrUpdate(tournament);
            tx.commit();

        }catch (PersistenceException | TournamentNotFoundException e){
            e.getMessage();
            tx.rollback();
        }

    }

    @Override
    public void changeStatus(int id, String status){

        try {
            tx.beginWrite();
            Tournament statusTournament = get(id);

            if(statusTournament.getStatus().equals("CLOSED") /*|| statusTournament.getStatus().equals("PLAY")*/){
                throw new StatusUnavailableException();
            }
            statusTournament.setStatus(status);

            tournamentDao.saveOrUpdate(statusTournament);
            tx.commit();


        }catch (PersistenceException | TournamentNotFoundException | StatusUnavailableException e){
            e.getMessage();
            tx.rollback();
        }

    }

    @Override
    public void newTournament(Tournament tournament) {

        try {
            tx.beginWrite();

            if(tournament.getId() != null){
                return;
            }

            tournamentDao.saveOrUpdate(tournament);
            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    @Override
    public void deleteTournament(int id){

        try {
            tx.beginWrite();
            tournamentDao.delete(id);
            tx.commit();

        }catch (PersistenceException e){
            e.getMessage();
            tx.rollback();
        }

    }

    /**
     * Create the rounds and matches for the tournament
     * @param tournament to know what tournament we are creating rounds;
     */
    @Override
    public void createRounds(Tournament tournament) throws RoundNotFinishException{
        if(tournament.getStatus().equals("CLOSED")){
            return;
        }
        if(tournament.getStatus().equals("OPEN")){
            /*
            * If we try to create rounds, and its open it means theres no rounds so we need to create points and a new round
             */
            pointService.createPointsForTournament(tournament);
            roundService.createRound(tournament);
            //roundService.createFirstRound(tournament);
            return;
        }
        if(tournament.getStatus().equals("PLAY")){
            /**
            We check if theres any undefeated player and if theres any round in battle status if all its true closed the tournament , if false create rounds
             */
            try {
                List<Round> rounds = tournament.getRounds();
                    for(Round r : rounds){
                        roundService.checkRound(r);
                    }

                rankingService.undefeatedPlayers(tournament.getId());

                /* may remove this after create a check on javascript ?*/

            }catch (NoUndefeatedPlayerException e) {

                /*try {
                    List<Round> rounds =tournament.getRounds();
                    for(Round r : rounds){
                        roundService.checkRound(r);
                    }
                } catch (RoundNotFinishException ex) {
                    return;
                }*/
                roundService.createRound(tournament);
                return;

            }catch ( RoundNotFinishException e){
                throw new RoundNotFinishException();
            }
            changeStatus(tournament.getId(), "CLOSED");
        }
    }

    /**
     * Add player to the tournament
     * @param tid tournamentId
     * @param pid playerId
     */
    public void addPlayers(int tid, int pid)throws RegisterErrorException{
        try {
            Tournament tournament = get(tid);
            Player player = playerService.get(pid);
            if(player.getTournaments().contains(tournament)){
                throw new RegisterErrorException();
            }
            player.getTournaments().add(tournament);
            playerService.updatePlayer(player);

        }catch (PersistenceException | TournamentNotFoundException | PlayerNotFoundException | DeckNotFoundException e){
            e.getMessage();
            throw new RegisterErrorException();
        }
    }

    /**
     * Remove player to the tournament
     * @param tid tournamentId
     * @param pid playerId
     */
    public void removePlayers(int tid, int pid)throws RegisterErrorException{
        try {
            //tx.beginWrite();
            Tournament tournament = get(tid);
            Player player = playerService.get(pid);
            if(!player.getTournaments().contains(tournament)){
                throw new RegisterErrorException();
            }
            player.getTournaments().remove(tournament);
            playerService.updatePlayer(player);
            //tx.commit();

        }catch (PersistenceException | TournamentNotFoundException | PlayerNotFoundException | DeckNotFoundException e){
            e.getMessage();
            //tx.rollback();
            throw new RegisterErrorException();
        }
    }

    /* Autowired */
    @Autowired
    public void setTx(TransactionManager tx) {
        this.tx = tx;
    }
    @Autowired
    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }
    @Autowired
    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }
    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
    @Autowired
    public void setRankingService(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
