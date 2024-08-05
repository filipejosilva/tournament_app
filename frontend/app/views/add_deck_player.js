import { getDecks } from "../services/deck_api.js";
import { getDeckList } from "../services/player_api.js";
import { informationBuildVertical } from "../components/information_div.js";
import { addDeckPlayerBtn } from "../components/btn_player.js";
import { gotoId } from "../main.js";

export const renderAddDeckPlayer = playerId => {

    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`
    
    btns(playerId)
    buildDeck(playerId);

}

const buildDeck = async (playerId) =>{

    const deckList = document.getElementById("playerlist");
    const title = document.createElement("h1");
    title.innerText = "Decks Available";
    deckList.appendChild(title);

    const data = await getDecks();
    console.log(data);

    const dataDeckList = await getDeckList(playerId);

    data.forEach(element => {

        var sameDeck = false;

        dataDeckList.forEach(check => {
            if(element.id=== check.id){
                sameDeck = true;
            }
        })

        if(sameDeck !== true){
            const main = document.createElement("div");
            main.classList.add("plist")
            main.appendChild(informationBuildVertical("Deck", element.name));
            main.appendChild(addDeckPlayerBtn(playerId, element.id))

            deckList.appendChild(main);
        }

        
    });

}

const btns = playerId =>{
    const Pinfo = document.getElementById("playerlist");
    const btn_div = document.createElement("div");
    btn_div.classList.add("btn_header");

    const div = document.createElement("div")

        div.appendChild(goBackBtn(playerId));


    btn_div.appendChild(div);
    Pinfo.appendChild(btn_div)
}

function goBackBtn(playerId){
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "< Go Back";

    btn.addEventListener("click", event => {
        event.preventDefault();
        gotoId("/player_info", playerId)
    })

    return btn;
}