import { gotoId } from "../main.js";
import { normalOverlay } from "./overlay.js";
import { addDeckPlayer, removeDeckPlayer } from "../services/player_api.js";
import { editOverlay } from "../components/overlay.js";

export const editBtn = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Edit Player";

    btn.addEventListener("click", event => {
        event.preventDefault();
        console.log(id + "edit");
        editOverlay("player", id);
    })

    return btn;
}

export const removeBtn = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Remove Player";

    btn.addEventListener("click", event => {
        event.preventDefault();
        console.log(id + "remove");
        
    })

    return btn;
}

export const addDeck = id => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Add Deck";

    btn.addEventListener("click", event => {
        event.preventDefault();
        gotoId("/player/deck/add", id)
    })

    return btn;
}

export const removeDeck = id => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Remove Deck";

    btn.addEventListener("click", event => {
        event.preventDefault();
        gotoId("/player/deck/remove", id)
    })

    return btn;
}

export const addDeckPlayerBtn = (playerId, deckId) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Add";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await addDeckPlayer(playerId, deckId);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }
        gotoId("/player_info", playerId)
    })

    return btn;
}

export const removeDeckPlayerBtn = (playerId, deckId) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Remove";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await removeDeckPlayer(playerId, deckId);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }
        gotoId("/player_info", playerId)
    })

    return btn;
}