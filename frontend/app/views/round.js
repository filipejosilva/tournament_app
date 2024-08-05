import { informationBuildOneLine, informationBuildRound } from "../components/information_div.js";
import { getTournamentRounds, 
    updateRound } from "../services/round_api.js";
import { matchOverlay, normalOverlay } from "../components/overlay.js";
import { goto, gotoId } from "../main.js";

export const renderRoundPage = async (id) =>{

    //createMenu
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`;

    const currentRound = [];


    const data = await getTournamentRounds(id);

    //get the latest rounds possible if theres is not any it goes to the tournament info, remove the redirct there was an error
    data.forEach(element => {
        if(element.status === "BATTLE"){
            currentRound[0] = element;
        }
        
    });

    btns(currentRound, id);
    buildPage(currentRound, id)

    console.log(currentRound, id);



}

const btns = (roundData, tournamentId) =>{
    const Rinfo = document.getElementById("playerlist");
    const btn_div = document.createElement("div");
    btn_div.classList.add("btn_header");

    const div = document.createElement("div")

    div.appendChild(gobackBtn(tournamentId));

    div.appendChild(finishBtn(roundData[0].id, tournamentId));


    btn_div.appendChild(div);
    Rinfo.appendChild(btn_div)

}

const finishBtn = (id, tournamentId) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Finish Round";

    btn.addEventListener("click", async event => {
        event.preventDefault();
        console.log(id + " remove");

        const data = await updateRound(id);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }

        //updateRound(id);
        gotoId("/tournament_info", tournamentId)
    })

    return btn;
}

const gobackBtn = (tournamentId) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "< Go Back";

    btn.addEventListener("click", event => {
        event.preventDefault();
        gotoId("/tournament_info", tournamentId)
    })

    return btn;
}

const buildPage = async (roundData, tournamentId) => {

    const div = document.getElementById("playerlist");

    const maindiv = document.createElement("div");
    maindiv.classList.add("playerTparticipation");

    const h1 = document.createElement("h1");
    
        h1.innerText = "Matches";

        maindiv.appendChild(h1);

        const matchList = roundData[0].matches;
        
        matchList.forEach(element => {
            
            const ptournament = document.createElement("div");
            ptournament.classList.add("ptournament");

            if(element.winner !== null){
                ptournament.appendChild(informationBuildRound(`WINNER: ${element.winner.name}`));
                ptournament.appendChild(informationBuildRound("|"));
                ptournament.appendChild(informationBuildRound("MATCH:"));
            }

            ptournament.appendChild(informationBuildRound(element.players[0].name));
            
            ptournament.appendChild(informationBuildOneLine("VS"));

            if(element.players.length === 2){
                ptournament.appendChild(informationBuildRound(element.players[1].name));
                if(element.winner === null){
                    ptournament.appendChild(matchBtn(element, tournamentId));
                }
                
            }else{
                ptournament.appendChild(informationBuildRound("NO PLAYER"));
            }

            maindiv.appendChild(ptournament);

        })
  

    div.appendChild(maindiv)
}

const matchBtn = (matchData, tournamentId) =>{
    const div = document.createElement("div");
    div.classList.add("infoPT");

    const btn = document.createElement("button");
    btn.classList.add("btn_previousT");
    btn.innerText = "Register results";

    btn.addEventListener('click', event => {
        event.preventDefault();
        //console.log(matchData)
        matchOverlay(matchData, tournamentId);
    });

    div.appendChild(btn);
    return div;
}