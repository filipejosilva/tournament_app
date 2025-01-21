const url = "http://localhost:8080/tournament/api/player";

export const getPlayerList = async() => {

    const response = await fetch(url);

    const data = await response.json();
    
    return data;
}

export const getPlayer = async (id) => {

    const response = await fetch(url + `/${id}`);

    const data = await response.json();
    
    return data;
}

export const getTournamentList = async(id) => {
    const tournamentListUrl = url + `/${id}/tournaments`

    const response = await fetch(tournamentListUrl);

    const data = await response.json();

    return data;
}

export const getDeckList = async(id) => {
    const ListUrl = url + `/${id}/decks`

    const response = await fetch(ListUrl);

    const data = await response.json();

    return data;
}

export const addDeckPlayer = async(playerId, deckId) => {
    const ListUrl = `${url}/${playerId}/${deckId}`

    const response = await fetch(ListUrl, {
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

export const removeDeckPlayer = async(playerId, deckId) => {
    const ListUrl = `${url}/${playerId}/${deckId}`

    const response = await fetch(ListUrl, {
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

export const newPlayer = async (player) =>{
    const newUrl = `${url}/add`

    const response = await fetch(newUrl, {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(player)
    });

    const data = await response.json();

    return data;

}

export const editPlayer = async (player) =>{
    const newUrl = `${url}/edit`

    const response = await fetch(newUrl, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(player)
    });

    const data = await response.json();

    return data;

}