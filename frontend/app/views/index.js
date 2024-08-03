import { createMenu } from "../components/menu_render.js";
import { getTournaments } from "../services/tournament_api.js";
import { informationBuildVertical } from "../components/information_div.js";
import { btnViewTournament } from "../components/btns.js";

export function renderIndex(){
    createMenu();
    const container = document.getElementById("container");
    container.innerHTML = "";
    
    container.innerHTML = ` <h1>Organize Tournaments for your store!</h1> 

        <div id="previous">

        </div>

    `

    previousTournament();

}

const previousTournament = async() => {

    const previous = document.getElementById("previous");
    previous.innerHTML = "<h1>Previous tournaments</h1>";
    const data = await getTournaments();
    data.forEach(element => {
        if(element.status === "CLOSED"){

            const ptournament = document.createElement("div");
            ptournament.classList.add("ptournament");

            ptournament.appendChild(informationBuildVertical("Tournament", element.name));
            ptournament.appendChild(informationBuildVertical("Winner", element.winner));
            ptournament.appendChild(informationBuildVertical("Nº of Players", element.players));
            ptournament.appendChild(informationBuildVertical("Date", element.date));

            ptournament.appendChild(btnViewTournament(element.id));
            previous.appendChild(ptournament);
        }
        
    });
}

