import { getTournament, getPlayerListTournament } from "../services/tournament_api.js";
import { informationBuildHorizontal, informationBuildOneLine, informationBuildOneLineBold } from "../components/information_div.js";
import { getRanking } from "../services/ranking_api.js";
import { btnViewPlayer } from "../components/btns.js";
import { addPlayer,startTournament,currentRound,finishRound, editTournament, removePlayerBtn } from "../components/btn_tournament.js";
import { getTournamentRounds } from "../services/round_api.js";

export const renderTournamentPage = async (id) =>{

    //createMenu
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`;


    const data = await getTournament(id);

    await btns(data);
    buildPage(data)

    playersTournament(data, id);

}


//Creating the btns
const btns = async(data) =>{
    const Tinfo = document.getElementById("playerlist");
    const btn_div = document.createElement("div");
    btn_div.classList.add("btn_header");

    const div = document.createElement("div")

    switch(data.status){
        case "OPEN":
            div.appendChild(editTournament(data.id));
            div.appendChild(addPlayer(data.id));
            div.appendChild(startTournament(data.id));
            break;
        case "PLAY":

            //trying to make a function that only put the current btn when we have a round available

            const rounds = await getTournamentRounds(data.id);
            rounds.forEach(element => {
                if(element.status === "BATTLE"){
                    div.appendChild(currentRound(data.id));
                }
                
            });
            //div.appendChild(currentRound(data.id));
            div.appendChild(finishRound(data.id));
            break;
        case "CLOSED":
            //console.log(data.status)
            break;
        default:
        console.log("Something went wrong")
    }

    btn_div.appendChild(div);
    Tinfo.appendChild(btn_div)

}


const buildPage = (data) => {

    const tournamentInfo = document.getElementById("playerlist");

    const h1 = document.createElement("h1");
    h1.innerText = "Tournament Information";

    tournamentInfo.appendChild(h1);
    tournamentInfo.appendChild(tournament(data))

}


const tournament = (data) => {
    const maindiv = document.createElement("div");
    const infodiv = document.createElement("div");

    maindiv.classList.add("playerinfocenter")
    infodiv.setAttribute("id", "playerinfo");

    infodiv.appendChild(informationBuildHorizontal("Tournament Name", data.name))
    infodiv.appendChild(informationBuildHorizontal("Players", data.players))
    infodiv.appendChild(informationBuildHorizontal("Status", data.status))
    infodiv.appendChild(informationBuildHorizontal("Date", data.date))


    maindiv.appendChild(infodiv);

    return maindiv;

}


const playersTournament = async (data, tid) => {

    const div = document.getElementById("playerlist");

    const maindiv = document.createElement("div");
    maindiv.classList.add("playerTparticipation");

    const h1 = document.createElement("h1");
    
    if(data.status === "OPEN"){
        h1.innerText = "Register Players";

        maindiv.appendChild(h1);

        maindiv.appendChild(playerListBuilder("bold"));

        const playerList = await getPlayerListTournament(data.id);
        
        playerList.forEach(element => {
            
            
            maindiv.appendChild(playerListBuilder(element, tid));

        });


    }else{
        h1.innerText = "Ranking";

        maindiv.appendChild(h1);

        maindiv.appendChild(ranking("bold"));

        const playerList = await getRanking(data.id);

        var position = 1;
        
        playerList.forEach(element => {
            
            
            maindiv.appendChild(ranking(element, position));

            position++;

        });
    }

    

    div.appendChild(maindiv)
}
//build playerlist for open tournament maybe another file js?
const playerListBuilder = (list, tid) => {

    const div = document.createElement("div");
    div.classList.add("rankings")

    const playerInfodiv = document.createElement("div");
    playerInfodiv.classList.add("ptournamentinfo");

    if(list === "bold"){
        playerInfodiv.appendChild(informationBuildOneLineBold("Name"));
        playerInfodiv.appendChild(informationBuildOneLineBold("Main deck"));
        playerInfodiv.appendChild(informationBuildOneLineBold("View Player"));
        playerInfodiv.appendChild(informationBuildOneLineBold("Remove"));
    }else{
        playerInfodiv.appendChild(informationBuildOneLine(list.name))
        playerInfodiv.appendChild(informationBuildOneLine(list.mainDeck))
        playerInfodiv.appendChild(btnViewPlayer(list.id));
        playerInfodiv.appendChild(removePlayerBtn(tid, list.id));
    }

    div.appendChild(playerInfodiv);

    return div

}

//build ranking for the tournament maybe another file js?
const ranking = (list, position) => {

    const div = document.createElement("div");
    div.classList.add("rankings")

    const positionDiv = document.createElement("div");
    positionDiv.classList.add("position");

    const playerInfodiv = document.createElement("div");
    playerInfodiv.classList.add("ptournamentinfo");

    if(list === "bold"){
        positionDiv.appendChild(informationBuildOneLine("# "))
        playerInfodiv.appendChild(informationBuildOneLineBold("Name"))
        playerInfodiv.appendChild(informationBuildOneLineBold("Score"))
        playerInfodiv.appendChild(informationBuildOneLineBold("OMW"))
    }else{
        positionDiv.appendChild(informationBuildOneLineBold(`${position}º`))
        playerInfodiv.appendChild(informationBuildOneLine(list.name))
        playerInfodiv.appendChild(informationBuildOneLine(list.points))
        playerInfodiv.appendChild(informationBuildOneLine(`${list.omw}%`))
    }

    div.appendChild(positionDiv);
    div.appendChild(playerInfodiv);

    return div

}

