INSERT INTO tournaments (name, date, status) VALUES
('Store tournament #01', '09/06/2024', 'CLOSED'),
('Store tournament #02', '22/07/2024', 'OPEN'),
('Store tournament #03', '30/07/2024', 'OPEN'),
('OP07 Pre-release', '21/06/2024', 'OPEN');

INSERT INTO decks (leader) VALUES
('Green Black Perona'),
('Black Gecko moria'),
('Black Yellow Luffy'),
('Blue Nami'),
('Yellow Katakuri'),
('Green Purple Doffy'),
('Blue Purple Reiju'),
('Red Yellow Bello Betty'),
('Green Kid'),
('Red Purple Law');

INSERT INTO players (nickname, maindeck) VALUES
('Filipe', 'Green Black Perona'),
('Dabid', 'Blue Name'),
('Joao', 'Yellow Katakuri'),
('Filipe', 'Green Black Perona'),
('Dabid', 'Blue Name'),
('Joao', 'Yellow Katakuri');

INSERT INTO players_decks (players_id, decks_id) VALUES
('1', '1'),
('1', '2'),
('1', '3'),
('1', '7'),
('2', '4'),
('2', '7'),
('3', '5'),
('3', '6'),
('3', '8');

INSERT INTO players_tournaments (players_id, tournaments_id) VALUES
('1', '1'),
('2', '1'),
('3', '1'),
('1', '4'),
('2', '4'),
('3', '4'),
('4', '4'),
('5', '4');


INSERT INTO points (tournament_id, player_id, OMW, score) VALUES
('1', '1', '50.0', '6'),
('1', '2', '51.0','3'),
('1', '3', '50.0','3');

INSERT INTO rounds (tournament_id, status) VALUES
('1', 'CLOSED'),
('1', 'CLOSED');

INSERT INTO matches (winner_id, round_id, status) VALUES
('1', '1', 'FINISH'),
('3',  '1', 'FINISH'),
('1', '2', 'FINISH'),
('2', '2', 'FINISH');

INSERT INTO matches_points(matchp_id, pointp_id) VALUES
('1', '1'),
('1', '2'),
('2', '3'),
('3', '1'),
('3', '3'),
('4', '2');