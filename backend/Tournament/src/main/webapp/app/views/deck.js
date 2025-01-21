import { getDecks} from "../services/deck_api.js";
import { informationBuildVertical } from "../components/information_div.js";
import { btnRemoveDeck } from "../components/btns.js";

export function renderDeck(){

    //createMenu();
    const container = document.getElementById("container");
    container.innerHTML = "";

    container.innerHTML = `<div id="playerlist">

    </div>`

    buildDeck();
}

const buildDeck = async () =>{

    const deckList = document.getElementById("playerlist");
    const title = document.createElement("h1");
    title.innerText = "Deck List";
    deckList.appendChild(title);

    const data = await getDecks();

    data.forEach(element => {
        const main = document.createElement("div");
        main.classList.add("plist")
        main.appendChild(informationBuildVertical("Deck", element.name));
        main.appendChild(informationBuildVertical("Nº of Players", element.number));
        main.appendChild(btnRemoveDeck(element.id))

        deckList.appendChild(main);
    });

}



