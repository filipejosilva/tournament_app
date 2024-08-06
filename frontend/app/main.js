import { renderIndex } from "./views/index.js";
import { renderDeck } from "./views/deck.js";
import { renderTList } from "./views/tournament_list.js";
import { renderTournamentPage } from "./views/tournament.js";
import { renderPlayerList } from "./views/player_list.js";
import {renderPlayerInfo } from "./views/player.js";
import { renderRoundPage } from "./views/round.js";
import { renderAddPlayerTournament } from "./views/add_player_tournament.js";
import { renderAddDeckPlayer } from "./views/add_deck_player.js";
import { renderRemoveDeckPlayer } from "./views/remove_deck_player.js";
import { addNew } from "./views/new.js";

//Mapping urls
const mapping = [
    {url:"/", page: renderIndex},
    {url:"/decks", page: renderDeck},
    {url:"/tournaments", page: renderTList},
    {url:"/tournament_info", page: renderTournamentPage},
    {url:"/players", page: renderPlayerList},
    {url:"/player_info", page: renderPlayerInfo},
    {url:"/round", page: renderRoundPage},
    //{url:"/tournament/add", page: renderAddPlayerTournament},
    //{url:"/player/deck/add", page: renderAddDeckPlayer},
    //{url:"/player/deck/remove", page: renderRemoveDeckPlayer},
    {url: "/new", page: addNew}
]

render();
window.addEventListener('popstate',render);


export function goto(url){
    //redirect to the correct page
    if(url === "/new_tournament" || url === "/new_player" || url === "/new_deck"){
        gotoId("/new", url)
        return;
    }

    const map = mapping.find(element => element.url === url)

    if(!map){
        goto("/");
        return;
    }


    window.history.pushState("","", url);
    render();
}

export const gotoId = (url, id) => {
    const map = mapping.find(element => element.url === url)

        if(!map){
        goto("/");
        return;
    }

    window.history.pushState("","", url);
    map.page(id);
}


function render(){
    const currentUrl = document.location.pathname;

    const map = mapping.find(element => element.url === currentUrl);

    if(!map){
        goto("/");
        return;
    }

    const container = document.getElementById("container");
    container.innerHTML = "";

    map.page(container);

}
