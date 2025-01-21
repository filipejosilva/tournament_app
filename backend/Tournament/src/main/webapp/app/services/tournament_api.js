const url = "http://localhost:8080/tournament/api/tournament";

export const getTournaments = async () => {

    const response = await fetch(url);

    const data = await response.json();
    
    return data;
}


export const getTournament = async (id) => {

    const response = await fetch(url + `/${id}`);

    const data = await response.json();
    
    return data;
}

export const getPlayerListTournament = async(id) => {
    const playerListUrl = url + `/${id}/players`

    const response = await fetch(playerListUrl);

    const data = await response.json();
    return data;
}

export const startTournamentRound = async(id) =>{

    const urlStart = url + `/${id}/rounds`;

    const response = await fetch(urlStart,{
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
    });

    const data = await response.json()

    return data;

}

export const registerPlayer = async(id, pid) =>{
    const registerUrl = `${url}/${id}/register/${pid}`;

    const response = await fetch(registerUrl, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
    });

    const data = await response.json();
    
    return data;
}

export const removePlayer = async(id, pid) =>{
    const removeUrl = `${url}/${id}/remove/${pid}`;

    const response = await fetch(removeUrl, {
        method: 'DELETE',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
    });

    const data = await response.json();
    
    return data;
}

export const newTournament = async (tournament) =>{
    const newUrl = `${url}/add`

    const response = await fetch(newUrl, {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(tournament)
    });

    const data = await response.json();

    return data;

}

export const editTournament = async (tournament) =>{
    const newUrl = `${url}/edit`

    const response = await fetch(newUrl, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(tournament)
    });

    const data = await response.json();

    return data;

}