const url = "http://localhost:8080/tournament/api/round";

export const getTournamentRounds = async(id) => {

    const roundsUrl = `${url}/tournament/${id}`;

    const response = await fetch(roundsUrl);

    const data = await response.json();
    
    return data;

}

export const updateRound = async(id) => {

    const roundsUrl = `${url}/${id}`;

    const response = await fetch(roundsUrl, {
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