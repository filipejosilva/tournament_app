import { goto } from "../main.js";
export function createMenu(){
    const menus = document.getElementById("nav");
    menus.innerHTML = `<ul class="menu">
            <li class="logo" id="hover"><a id="homepage">Tournament App organizer</a></li>
            
            <li class="item has-submenu"><a tabindex="0">New</a>
                <ul class="submenu">
                    <li class="subitem"><a id="newT" >Tournaments</a></li>
                    <li class="subitem"><a id="newP" >Player</a></li>
                    <li class="subitem"><a id="newD" >Deck</a></li>
                </ul>
            </li>
            <li class="item"><a id="redirectT">Tournaments</a></li>
            <li class="item"><a id="redirectP">Players</a></li>
            <li class="item"><a id="redirectD">Decks</a></li>
            
            <li class="toggle"><a href="#"><i class="fas fa-bars"></i></a></li>
        </ul>`

        menufunctions();

        redirects();
        
}

function menufunctions(){
    const toggle = document.querySelector(".toggle");
    const menu = document.querySelector(".menu");

    /* Toggle mobile menu */

    function toggleMenu() {
        if (menu.classList.contains("active")) {
            menu.classList.remove("active");
            // adds the menu (hamburger) icon
            toggle.querySelector("a").innerHTML = "<i class='fas fa-bars'></i>";

        } else {
            menu.classList.add("active");

            // adds the close (x) icon
            toggle.querySelector("a").innerHTML = "<i class='fas fa-times'></i>";

        }

    }
    /* Event Listener */
    toggle.addEventListener("click", toggleMenu, false);

    const items = document.querySelectorAll(".item");
    /* Activate Submenu */
    function toggleItem() {
        if (this.classList.contains("submenu-active")) {

            this.classList.remove("submenu-active");

        } else if (menu.querySelector(".submenu-active")) {

            menu.querySelector(".submenu-active").classList.remove("submenu-active");
            this.classList.add("submenu-active");
        } else {

            this.classList.add("submenu-active");
        }

    }

    /* Event Listeners */

    for (let item of items) {

        if (item.querySelector(".submenu")) {

        item.addEventListener("click", toggleItem, false);
        item.addEventListener("keypress", toggleItem, false);

        }   

    }

    /* Close Submenu From Anywhere */

    function closeSubmenu(e) {

        if (menu.querySelector(".submenu-active")) {

        let isClickInside = menu
            .querySelector(".submenu-active")
            .contains(e.target);
        if (!isClickInside && menu.querySelector(".submenu-active")) {
            menu.querySelector(".submenu-active").classList.remove("submenu-active");
        }
        }
    
    }
    
    /* Event listener */
    document.addEventListener("click", closeSubmenu, false);
  

}

const redirects = () => {

    redirect("homepage", "/");
    redirect("redirectD", "/decks")
    redirect("redirectT", "/tournaments")
    redirect("redirectP", "/players");
    redirect("newT", "/new_tournament");
    redirect("newP", "/new_player");
    redirect("newD", "/new_deck");

}

const redirect = (redirect, url) => {
    document.getElementById(redirect).addEventListener('click', event => {
        event.preventDefault;
        goto(url);
    });
}