const url = "http://localhost:8080/tournament/api/ranking";
export const getRanking = async(id) => {

    const rankingUrl = url + `/${id}`;

    const response = await fetch(rankingUrl);

    const data = await response.json();

    return data;
}
