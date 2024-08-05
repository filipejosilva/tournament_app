//Tournament and Player information
export const informationBuildHorizontal = (info, data) =>{
    const div = document.createElement("div");
    div.classList.add("infoPlayer")

    const pInfo = document.createElement("p");
    const pData = document.createElement("p");

    pInfo.classList.add("boldinfo");
    pData.classList.add("notboldinfo");

    pData.innerText = data;
    pInfo.innerText = info;
    div.appendChild(pInfo);
    div.appendChild(pData);

    return div;
}

//Index tournamentList, decklist, playerlist and tournament list
export const informationBuildVertical =(info, data) =>{

    const div = document.createElement("div");
    div.classList.add("infoPT");
    const pbold = document.createElement("p");
    const p = document.createElement("p");
    pbold.classList.add("bold");
    p.classList.add("notbold");

    p.innerText = data;
    pbold.innerText = info;
    div.appendChild(pbold);
    div.appendChild(p);


    return div;
}

export const informationBuildOneLineBold = (data) => {

    const div = document.createElement("div");
    div.classList.add("infoPT");

    const p = document.createElement("p");
    p.classList.add("boldinfo");

    p.innerText = data;

    div.appendChild(p);

    return div
}

export const informationBuildOneLine = (data) => {

    const div = document.createElement("div");
    div.classList.add("infoPT");

    const p = document.createElement("p");
    p.classList.add("notbold");

    p.innerText = data;

    div.appendChild(p);

    return div
}

export const informationBuildRound = data =>{

    const div = document.createElement("div");
    div.classList.add("infoPT");

    const p = document.createElement("p");
    p.classList.add("boldtinfo");

    p.innerText = data;

    div.appendChild(p);

    return div
}