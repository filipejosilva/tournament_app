import { gotoId } from "../main.js";
import { getPlayerList } from "../services/player_api.js";
import { getPlayerListTournament, registerPlayer } from "../services/tournament_api.js";
import { informationBuildVertical} from "../components/information_div.js";
import { btnViewPlayer } from "../components/btns.js";
import { registerPlayerBtn } from "../components/btn_tournament.js";

export const renderAddPlayerTournament = (tournamentId) =>{
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`;

    btns(tournamentId)
    buildPage(tournamentId);
}

const btns = tournamentId =>{
    const Pinfo = document.getElementById("playerlist");
    const btn_div = document.createElement("div");
    btn_div.classList.add("btn_header");

    const div = document.createElement("div")

        div.appendChild(goBackBtn(tournamentId));


    btn_div.appendChild(div);
    Pinfo.appendChild(btn_div)
}

function goBackBtn(tournamentId){
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "< Go Back";

    btn.addEventListener("click", event => {
        event.preventDefault();
        gotoId("/tournament_info", tournamentId)
    })

    return btn;
}

const buildPage = async tournamentId =>{

    const deckList = document.getElementById("playerlist");
    const title = document.createElement("h1");
    title.innerText = "Players available:";
    deckList.appendChild(title);

    const data = await getPlayerList ();

    const playersTournament = await getPlayerListTournament(tournamentId)

        console.log(data)
        data.forEach(element => {

            var samePlayer = false;

            playersTournament.forEach(check =>{
                if(element.id === check.id){
                    samePlayer = true;
                }
                
            });

            if(samePlayer === false){
                const main = document.createElement("div");
                main.classList.add("plist")
                main.appendChild(informationBuildVertical("Name", element.name));
                main.appendChild(informationBuildVertical("Main Deck", element.mainDeck));
                main.appendChild(registerPlayerBtn(tournamentId, element.id))

                deckList.appendChild(main);
            }
            
        })

}