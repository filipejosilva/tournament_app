import { goto, gotoId } from "../main.js";
import { deleteDeck } from "../services/deck_api.js";
import { normalOverlay } from "./overlay.js";

//TournamentList, player info and index
export const btnViewTournament = (id) =>{
    const div = document.createElement("div");
    div.classList.add("infoPT");

    const btn = document.createElement("button");
    btn.classList.add("btn_previousT");
    btn.innerText = "View Tournament";

    btn.addEventListener('click', event => {
        event.preventDefault();
        console.log(id)
        gotoId("/tournament_info", id);
    });

    div.appendChild(btn);
    return div;
}

//View Player, tournament in open and player List
export const btnViewPlayer = (id) =>{
    const div = document.createElement("div");
    div.classList.add("infoPT");

    const btn = document.createElement("button");
    btn.classList.add("btn_previousT");
    btn.innerText = "View Player";

    btn.addEventListener('click', event => {
        event.preventDefault();
        console.log("View player " + id)
        gotoId("/player_info", id);
    });

    div.appendChild(btn);
    return div;
}

//Delete decks, if I have another delete I may reuse this to create other buttons

export const btnRemoveDeck = (id) =>{
    const div = document.createElement("div");
    div.classList.add("infoPT");

    const btn = document.createElement("button");
    btn.classList.add("btn_previousT");
    btn.innerText = "Delete Deck";

    btn.addEventListener('click', async event => {
        event.preventDefault();
        const data = await deleteDeck(id);
        if(data.name === "Error"){
            normalOverlay(data.message);
            return
        }

        goto("/decks");
    });

    div.appendChild(btn);
    return div;
}