import { goto, gotoId } from "../main.js";
import { winnerSelection } from "../services/match_api.js"
import { getPlayer,editPlayer } from "../services/player_api.js";
import { getTournament, editTournament } from "../services/tournament_api.js";

export const matchOverlay = (matchData, tournamentId) => {

    buildOverlay();
    on();

    const div = document.createElement("div");
    div.setAttribute("id", "choose")

    const p = document.createElement("p");
    p.classList.add("choosetext");
    p.innerText = "Choose the Winner";

    const chooseDiv = document.createElement("div")
    chooseDiv.classList.add("choosediv")

    div.appendChild(p)

    chooseDiv.appendChild(winnerBtn(matchData.id, matchData.players[0].id, matchData.players[0].name, tournamentId))
    chooseDiv.appendChild(winnerBtn(matchData.id, matchData.players[1].id, matchData.players[1].name, tournamentId))

    div.appendChild(chooseDiv);

    document.getElementById("overlay").appendChild(div)

}

export const normalOverlay = (string) => {

    buildOverlay();
    on();

    const div = document.createElement("div");
    div.setAttribute("id", "choose")

    const p = document.createElement("p");
    p.classList.add("choosetext");
    p.innerText = string;

    div.appendChild(p)
    div.appendChild(closeBtn());

    document.getElementById("overlay").appendChild(div)

}

export const editOverlay = async (type, id) => {
    buildOverlay();
    on();

    if(type === "player"){
        const player = await getPlayer(id);
        playerEdit(player);

    }
    if(type === "tournament"){
        const tournament = await getTournament(id)
        tournamentEdit(tournament);

    }

}

const buildOverlay = () =>{

    const body = document.body;

    const div = document.createElement("div");
    div.setAttribute("id", "overlay");
    div.innerHTML = `<div id="overlay_close">
    </div>`

    body.appendChild(div);

    const overlayClose = document.getElementById("overlay_close");
    overlayClose.addEventListener("click", event => {
        event.preventDefault();
        off();
    })

}

function on(){
    document.getElementById("overlay").style.display = "flex";
}

function off(){
    document.getElementById("overlay").style.display = "none";

    document.getElementById("overlay").innerHTML = "";

    const div = document.createElement("div");
    div.setAttribute("id", "overlay_close");

    
    document.getElementById("overlay").appendChild(div);
}

function winnerBtn(matchId, playerId, name, tournamentId){
    const btn = document.createElement("button");
    btn.classList.add("choosebtn")
    btn.innerText = name;

    btn.addEventListener("click", async event =>{
        event.preventDefault();
        await winnerSelection(matchId, playerId)
        gotoId("/tournament_info", tournamentId)
        off();
    })

    return btn 
}

function closeBtn(){
    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Close";

    btn.addEventListener("click", async event =>{
        event.preventDefault();
        off();
    })

    return btn 
}

async function playerEdit (player) {
    const div = document.createElement("div");
    div.setAttribute("id", "choose")

    const p = document.createElement("p");
    p.classList.add("choosetext");
    p.innerText = "Edit player";

    //Form

    const form = document.createElement("form")
    form.action = "put";

    const h2 = document.createElement("h2")
    h2.innerText = "Player name";

    const input = document.createElement("input")
    input.classList.add("input")
    input.value = player.name

    //Select
    const h2Deck = document.createElement("h2")
    h2Deck.innerText = "Main Deck";

    const selectInput = document.createElement("select");
    selectInput.required = true;
    selectInput.classList.add("input");

    const InputOptionNull = document.createElement("option")
    
    if(player.mainDeck !== null){
        InputOptionNull.innerText = player.mainDeck;
        InputOptionNull.value = player.mainDeck
    }else{
        InputOptionNull.innerText = "--Select option--";
        InputOptionNull.value = "";
    }
    selectInput.appendChild(InputOptionNull)

    populateDecks(player.other, selectInput)

    form.appendChild(h2);
    form.appendChild(input);
    form.appendChild(h2Deck);
    form.appendChild(selectInput);

    //save Btn

    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Save";

    btn.addEventListener("click", async event =>{
        event.preventDefault();


        const editplayers = {
            id: player.id,
            nickname: input.value,
            mainDeck: selectInput.value,

        }

        const data = await editPlayer(editplayers);

        if(data.name === "Error"){
            off()
            normalOverlay(data.message)
            return
        }

        off();
        gotoId("/player_info", player.id)
    })


    //append

    div.appendChild(p)
    div.appendChild(form)
    div.appendChild(btn)
    div.appendChild(closeBtn());

    document.getElementById("overlay").appendChild(div)
}

function tournamentEdit (tournament){
    const div = document.createElement("div");
    div.setAttribute("id", "choose")

    const p = document.createElement("p");
    p.classList.add("choosetext");
    p.innerText = "Edit tournament";

    //form
    const form = document.createElement("form")
    form.action = "put";

    const h2 = document.createElement("h2")
    h2.innerText = "Tournament name";

    const input = document.createElement("input")
    input.classList.add("input")
    input.value = tournament.name

    const h2Date = document.createElement("h2")
    h2Date.innerText = "Date";
            
    const inputDate = document.createElement("input")
    inputDate.classList.add("input")
    inputDate.value = tournament.date

    form.appendChild(h2);
    form.appendChild(input)
    form.appendChild(h2Date)
    form.appendChild(inputDate)

    //save Btn

    const btn = document.createElement("button");
    btn.classList.add("btn")
    btn.innerText = "Save";

    btn.addEventListener("click", async event =>{
        event.preventDefault();

        tournament.name = input.value;
        tournament.date = inputDate.value;

        const data = await editTournament(tournament)
        if(data.name === "Error"){
            off()
            normalOverlay(data.message)
            return
        }

        gotoId("/tournament_info", tournament.id)
        off();
    })

    div.appendChild(p)
    div.appendChild(form)
    div.appendChild(btn)
    div.appendChild(closeBtn());

    document.getElementById("overlay").appendChild(div)
}

function populateDecks(decklist, select){

    decklist.forEach(element => {
        const option = document.createElement ("option")
        option.innerText = element.name;
        select.appendChild (option);
    });

}

