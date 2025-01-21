//I may need to add goto and other stuff to make them work when that option is available
import { gotoId } from "../main.js"; 
import { normalOverlay } from "./overlay.js";
import { registerPlayer, removePlayer, startTournamentRound } from "../services/tournament_api.js";
import { editOverlay } from "./overlay.js";

import { off, addPlayerTournamentOverlay } from "./overlay.js";

export const addPlayer = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Add Player";

    btn.addEventListener("click", event => {
        event.preventDefault();

        addPlayerTournamentOverlay(id)
        //gotoId("/tournament/add", id)
    })

    return btn;
}


export const editTournament = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Edit tournament";

    btn.addEventListener("click", event => {
        event.preventDefault();
        editOverlay("tournament", id)
    })

    return btn;
}

export const startTournament = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Start Tournament";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await startTournamentRound(id);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return
        }

        gotoId("/tournament_info", id)

    })

    return btn;
}

export const currentRound = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Current Round";

    btn.addEventListener("click", event => {
        event.preventDefault();
        document.getElementById("container").innerHTML = "";
        gotoId("/round", id)
    })

    return btn;
}

export const finishRound = (id) => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "New Round";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await startTournamentRound(id);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return
        }

        gotoId("/tournament_info", id)
    })

    return btn;
}

export const registerPlayerBtn = (tournamentId, playerId) =>{
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Add Player";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await registerPlayer(tournamentId, playerId);

        if(data.name === "Error"){
            off()
            normalOverlay(data.message)
            return
        }
        off()
        gotoId("/tournament_info", tournamentId)
    })

    return btn;
}

export const removePlayerBtn = (tournamentId, playerId) =>{
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Remove";

    btn.addEventListener("click", async event => {
        event.preventDefault();

        const data = await removePlayer(tournamentId, playerId);

        if(data.name === "Error"){
            normalOverlay(data.message)
            return
        }

        gotoId("/tournament_info", tournamentId)
    })

    return btn;
}