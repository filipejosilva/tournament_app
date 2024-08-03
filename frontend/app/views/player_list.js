import { createMenu } from "../components/menu_render.js";
import { getPlayerList } from "../services/player_api.js";
import { goto } from "../main.js";
import { informationBuildVertical } from "../components/information_div.js";
import { btnViewPlayer } from "../components/btns.js";


export const renderPlayerList = () =>{

    createMenu();
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`

    buildPage();
}

const buildPage = async () =>{

    const playerList = document.getElementById("playerlist");
    const title = document.createElement("h1");
    title.innerText = "Player List";
    playerList.appendChild(title);

    const data = await getPlayerList ();
    console.log(data);

    data.forEach(element => {
        const main = document.createElement("div");
        main.classList.add("plist")
        main.appendChild(informationBuildVertical("Name", element.name));
        main.appendChild(informationBuildVertical("Main Deck", element.mainDeck));
        main.appendChild(btnViewPlayer(element.id))

        playerList.appendChild(main);
    });

}