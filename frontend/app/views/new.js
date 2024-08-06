import { normalOverlay } from "../components/overlay.js";
import { goto } from "../main.js";
import { newTournament } from "../services/tournament_api.js";
import { newDeck} from "../services/deck_api.js";
import { newPlayer } from "../services/player_api.js";

export const addNew = url => {
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`


    buildPage(url);

}

function buildPage (url){

    const div = document.getElementById("playerlist")

    const h1 = document.createElement("h1")
    switch(url){
        case "/new_tournament":
            h1.innerText = "New Tournament";
            div.appendChild(h1);
            break;
        case "/new_player":
            h1.innerText = "New Player";
            div.appendChild(h1);
            break;
        case "/new_deck":
                h1.innerText = "New Deck";
                div.appendChild(h1);
                break;
        default:
            break;
    }

    div.appendChild(form(url))

}

function form(url){

    const form = document.createElement("form")
    form.action = "POST";

    switch(url){
        case "/new_tournament":
            return tournamentForm();
        case "/new_player":
            return playerForm();
        case "/new_deck":
            return deckForm();;
        default:
            break;
    }

}

const tournamentForm = () => {

    const form = document.createElement("form")
    form.action = "post";

    const h2 = document.createElement("h2")
    h2.innerText = "Tournament name";

    const input = document.createElement("input")
    input.classList.add("input")

    const h2Date = document.createElement("h2")
    h2Date.innerText = "Date";
            
    const inputDate = document.createElement("input")
    inputDate.classList.add("input")

    form.appendChild(h2);
    form.appendChild(input)
    form.appendChild(h2Date)
    form.appendChild(inputDate)

    const div = document.createElement("div")

    const btnContainer = document.createElement("div")
    btnContainer.classList.add("btn_footer");

    //Add btn

    const btn = document.createElement("button")
    btn.classList.add("btn")
    btn.innerText = "Add"

    btn.addEventListener("click", async event =>{
        event.preventDefault()

        const tournament = {
            id: null,
            name: input.value,
            status: "OPEN",
            date: inputDate.value,
            winner: null,

        }
        const data = await newTournament(tournament);
        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }
        goto("/tournaments")
    })

    div.appendChild(btn)
    div.appendChild(cancelBtn())

    btnContainer.appendChild(div)

    form.appendChild(btnContainer)
    return form;

}

const playerForm = () => {

    const form = document.createElement("form")
    form.action = "POST";

    const h2 = document.createElement("h2")
    h2.innerText = "Player name";

    const input = document.createElement("input")
    input.classList.add("input")

    form.appendChild(h2);
    form.appendChild(input)

    const div = document.createElement("div")

    const btnContainer = document.createElement("div")
    btnContainer.classList.add("btn_footer");

    //Add btn

    const btn = document.createElement("button")
    btn.classList.add("btn")
    btn.innerText = "Add"

    btn.addEventListener("click", async event =>{
        event.preventDefault()

        const player = {
            id: null,
            nickname: input.value,
            mainDeck: null

        }

        const data = await newPlayer(player);
        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }
        goto("/players")

    })

    div.appendChild(btn)
    div.appendChild(cancelBtn())

    btnContainer.appendChild(div)

    form.appendChild(btnContainer)

    return form;
    
}

const deckForm = () => {

    const form = document.createElement("form")
    form.action = "POST";

    const h2 = document.createElement("h2")
    h2.innerText = "Deck name";

    const input = document.createElement("input")
    input.classList.add("input")

    form.appendChild(h2);
    form.appendChild(input)

    const div = document.createElement("div")

    const btnContainer = document.createElement("div")
    btnContainer.classList.add("btn_footer");

    //Add btn

    const btn = document.createElement("button")
    btn.classList.add("btn")
    btn.innerText = "Add"

    btn.addEventListener("click", async event =>{
        event.preventDefault()

        const deck = {
            id: null,
            leader: input.value,
        }

        const data = await newDeck(deck);
        if(data.name === "Error"){
            normalOverlay(data.message)
            return;
        }
        goto("/decks")

    })

    div.appendChild(btn)
    div.appendChild(cancelBtn())

    btnContainer.appendChild(div)

    form.appendChild(btnContainer)
    
    return form;
}

const cancelBtn = () => {
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Cancel"

    btn.addEventListener("click", event =>{
        event.preventDefault
        goto("/")
    })
    return btn;
}