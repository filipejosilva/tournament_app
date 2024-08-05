import { getPlayer, 
    getTournamentList } from "../services/player_api.js";
import { createMenu } from "../components/menu_render.js";
import { goto, gotoId } from "../main.js";
import { informationBuildHorizontal, 
    informationBuildVertical} from "../components/information_div.js";
import { btnViewTournament } from "../components/btns.js";
import { editBtn, 
    removeBtn,
    removeDeck,
    addDeck } from "../components/btn_player.js";

export const renderPlayerInfo = async (id) => {

    //createMenu
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`;


    const data = await getPlayer(id);

    //console.log(data);

    btns(data);
    buildPage(data)

    playersTournament(data);

}

const btns = (data) =>{
    const Pinfo = document.getElementById("playerlist");
    const btn_div = document.createElement("div");
    btn_div.classList.add("btn_header");

    const div = document.createElement("div")

    div.appendChild(addDeck(data.id));
    div.appendChild(removeDeck(data.id));
    div.appendChild(editBtn(data.id));
    div.appendChild(removeBtn(data.id));

    btn_div.appendChild(div);
    Pinfo.appendChild(btn_div)

}

const buildPage = (data) => {

    const playerInfo = document.getElementById("playerlist");

    const h1 = document.createElement("h1");
    h1.innerText = "Player Information";

    playerInfo.appendChild(h1);
    playerInfo.appendChild(player(data))


    
}

const player = (data) => {
    const maindiv = document.createElement("div");
    const infodiv = document.createElement("div");

    maindiv.classList.add("playerinfocenter")
    infodiv.setAttribute("id", "playerinfo");

    infodiv.appendChild(informationBuildHorizontal("Nickname", data.name))
    infodiv.appendChild(informationBuildHorizontal("Main Deck", data.mainDeck))

    var decks = "";

    data.other.forEach(element => {
        if(element.name !== data.mainDeck){
            decks += `${element.name} | `;
        }

    });

    infodiv.appendChild(informationBuildHorizontal("Other", decks))
    infodiv.appendChild(informationBuildHorizontal("Nº Tournament participations", data.tournaments))


    maindiv.appendChild(infodiv);

    return maindiv;

}

const playersTournament = async (data) => {

    const div = document.getElementById("playerlist");

    const maindiv = document.createElement("div");
    maindiv.classList.add("playerTparticipation");

    const h1 = document.createElement("h1");
    
        h1.innerText = "Tournament Participation";

        maindiv.appendChild(h1);

        const playerList = await getTournamentList(data.id);
        
        playerList.forEach(element => {
            
            const ptournament = document.createElement("div");
            ptournament.classList.add("ptournament");

            ptournament.appendChild(informationBuildVertical("Tournament", element.name));

            if(element.winner !== null){
                ptournament.appendChild(informationBuildVertical("Winner", element.winner));
            }
            
            ptournament.appendChild(informationBuildVertical("Nº of Players", element.players));
            ptournament.appendChild(informationBuildVertical("Date", element.date));

            ptournament.appendChild(btnViewTournament(element.id));
            maindiv.appendChild(ptournament);

        })
  

    div.appendChild(maindiv)
}