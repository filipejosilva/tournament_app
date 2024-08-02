const url = "http://localhost:8080/tournament/api/match";

export const winnerSelection = async (id, playerId) => {

    const winnerUrl = `${url}/${id}/${playerId}`;

    const response = await fetch(winnerUrl, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
    });

}