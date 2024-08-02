const url = "http://localhost:8080/tournament/api/deck";

export const getDecks = async () => {

    const response = await fetch(url);

    const data = await response.json();
    
    return data;
}

export const deleteDeck = async (id) =>{

    const deleteUrl = url + `/${id}/delete`;
    const response = await fetch(deleteUrl, {
        method: 'DELETE',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
    });

    const data = await response.json()

    return data;
    
    //console.log(deleteUrl)
    
}

export const newDeck = async (deck) =>{
    const newUrl = `${url}/add`

    const response = await fetch(newUrl, {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(deck)
    });

    const data = await response.json();

    return data;

}