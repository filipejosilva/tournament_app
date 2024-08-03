import { getTournaments } from "../services/tournament_api.js";
import { createMenu } from "../components/menu_render.js";
import { informationBuildVertical } from "../components/information_div.js";
import { btnViewTournament } from "../components/btns.js";

export function renderTList (){
    //createMenu
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`;

    buildBasic();

}

const buildBasic = async() =>{

    const tournamentList = document.getElementById("playerlist");

    const title = document.createElement("h1");
    title.innerText = "Tournament List";
    tournamentList.appendChild(title);

    const data = await getTournaments();
    //console.log(data);

    data.forEach(element => {
        const main = document.createElement("div");
        main.classList.add("plist")
        main.appendChild(informationBuildVertical("Tournament", element.name));
        main.appendChild(informationBuildVertical("Players", element.players));
        main.appendChild(informationBuildVertical("Date", element.date));
        main.appendChild(informationBuildVertical("Status", element.status));
        main.appendChild(btnViewTournament(element.id))

        tournamentList.appendChild(main);
    });

}
