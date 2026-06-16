-- =================================================================
-- SEZIONE DI POPOLAZIONE MASSIVA: BAR, UTENTI, PREFERITI, RECENSIONI
-- =================================================================

-- 1. POPOLAZIONE BAR (Aggiunti per completezza del database)
INSERT INTO Bar (nomeBar, città, indirizzo) VALUES
('Bar Manhattan', 'Milano', 'Via Montenapoleone 10'),
('The Speakeasy', 'Roma', 'Via del Corso 50'),
('Tiki Lounge', 'Napoli', 'Lungomare Caracciolo 12'),
('Dry Martini Club', 'Torino', 'Piazza San Carlo 5'),
('Cocktail & Co.', 'Bologna', 'Via Indipendenza 22'),
('Havana Nights', 'Palermo', 'Via Maqueda 100'),
('Gin Palace', 'Firenze', 'Piazza della Repubblica 8');

-- 2. POPOLAZIONE UTENTI (30 Utenti realistici con ruoli diversi)
INSERT INTO Utenti (email, password, nome, cognome, dataNascita, ruoloUtente, dataIscrizione, numeroRicetteCreate, numeroRecensioniPositive, numeroRecensioniEffettuate) VALUES
('mario.rossi@email.com', 'pwd_1', 'Mario', 'Rossi', '1985-05-12', 'Utente', '2023-01-15', 0, 0, 0),
('luca.bianchi@email.com', 'pwd_2', 'Luca', 'Bianchi', '1990-08-23', 'Barman', '2023-02-10', 3, 12, 15),
('giulia.verdi@email.com', 'pwd_3', 'Giulia', 'Verdi', '1992-11-05', 'Utente', '2023-02-14', 1, 5, 6),
('anna.neri@email.com', 'pwd_4', 'Anna', 'Neri', '1988-03-30', 'Utente', '2023-03-01', 0, 2, 3),
('marco.gialli@email.com', 'pwd_5', 'Marco', 'Gialli', '1995-07-19', 'Barman', '2023-03-10', 5, 20, 22),
('sofia.blu@email.com', 'pwd_6', 'Sofia', 'Blu', '1998-01-22', 'Utente', '2023-04-05', 0, 1, 2),
('davide.rosa@email.com', 'pwd_7', 'Davide', 'Rosa', '1982-09-14', 'Admin', '2022-11-20', 10, 50, 50),
('elena.viola@email.com', 'pwd_8', 'Elena', 'Viola', '1993-06-08', 'Utente', '2023-05-12', 0, 3, 4),
('matteo.grigi@email.com', 'pwd_9', 'Matteo', 'Grigi', '1991-12-01', 'Barman', '2023-05-20', 2, 8, 10),
('chiara.marroni@email.com', 'pwd_10', 'Chiara', 'Marroni', '1997-04-15', 'Utente', '2023-06-01', 0, 0, 1),
('alessandro.ferri@email.com', 'pwd_11', 'Alessandro', 'Ferri', '1989-10-10', 'Utente', '2023-06-15', 1, 4, 5),
('francesca.ricci@email.com', 'pwd_12', 'Francesca', 'Ricci', '1994-02-28', 'Utente', '2023-07-01', 0, 2, 2),
('lorenzo.marini@email.com', 'pwd_13', 'Lorenzo', 'Marini', '1987-08-05', 'Barman', '2023-07-10', 4, 15, 18),
('sara.galli@email.com', 'pwd_14', 'Sara', 'Galli', '1996-11-11', 'Utente', '2023-08-01', 0, 1, 1),
('andrea.fontana@email.com', 'pwd_15', 'Andrea', 'Fontana', '1990-05-25', 'Utente', '2023-08-15', 0, 3, 4),
('valentina.costa@email.com', 'pwd_16', 'Valentina', 'Costa', '1993-09-30', 'Utente', '2023-09-01', 0, 0, 0),
('simone.giordano@email.com', 'pwd_17', 'Simone', 'Giordano', '1986-01-17', 'Barman', '2023-09-10', 3, 10, 12),
('martina.rizzo@email.com', 'pwd_18', 'Martina', 'Rizzo', '1999-03-22', 'Utente', '2023-10-01', 0, 2, 3),
('gabriele.lombardi@email.com', 'pwd_19', 'Gabriele', 'Lombardi', '1991-07-07', 'Utente', '2023-10-15', 1, 5, 6),
('alice.barbieri@email.com', 'pwd_20', 'Alice', 'Barbieri', '1995-12-12', 'Utente', '2023-11-01', 0, 1, 2),
('nicola.moretti@email.com', 'pwd_21', 'Nicola', 'Moretti', '1984-04-04', 'Admin', '2022-12-01', 8, 40, 45),
('giorgia.conti@email.com', 'pwd_22', 'Giorgia', 'Conti', '1992-08-18', 'Utente', '2023-11-15', 0, 3, 4),
('federico.de.luca@email.com', 'pwd_23', 'Federico', 'De Luca', '1988-06-29', 'Barman', '2023-12-01', 2, 7, 9),
('camilla.mancini@email.com', 'pwd_24', 'Camilla', 'Mancini', '1997-10-05', 'Utente', '2023-12-10', 0, 0, 1),
('riccardo.villa@email.com', 'pwd_25', 'Riccardo', 'Villa', '1990-02-14', 'Utente', '2024-01-05', 0, 2, 3),
('beatrice.russo@email.com', 'pwd_26', 'Beatrice', 'Russo', '1994-05-30', 'Utente', '2024-01-15', 0, 1, 1),
('tommaso.greco@email.com', 'pwd_27', 'Tommaso', 'Greco', '1989-09-09', 'Barman', '2024-02-01', 3, 11, 14),
('elisa.bruno@email.com', 'pwd_28', 'Elisa', 'Bruno', '1996-01-25', 'Utente', '2024-02-10', 0, 0, 0),
('daniele.gallo@email.com', 'pwd_29', 'Daniele', 'Gallo', '1993-11-20', 'Utente', '2024-02-20', 0, 2, 2),
('silvia.leone@email.com', 'pwd_30', 'Silvia', 'Leone', '1991-04-10', 'Utente', '2024-03-01', 0, 1, 2);

-- admins
INSERT INTO Utenti (email, password, nome, cognome, dataNascita, ruoloUtente, dataIscrizione, numeroRicetteCreate, numeroRecensioniPositive, numeroRecensioniEffettuate) VALUES
('rocco.greco@email.com', 'pwd_31', 'Rocco', 'Greco', '1994-07-12', 'Admin', '2024-03-01', 0, 1, 2);

-- 3. POPOLAZIONE SALVATAGGIO PREFERITI (~150 record)
-- Ogni utente ha tra 3 e 8 drink preferiti
INSERT INTO salvataggioPreferiti (drinkID, userID, dataSalvataggio) VALUES
(1, 2, '2023-03-01'), (4, 2, '2023-03-05'), (12, 2, '2023-04-10'), (17, 2, '2023-05-12'),
(2, 3, '2023-03-12'), (5, 3, '2023-03-15'), (8, 3, '2023-04-01'), (15, 3, '2023-05-20'), (21, 3, '2023-06-01'),
(1, 4, '2023-04-05'), (7, 4, '2023-04-10'), (9, 4, '2023-05-01'),
(3, 5, '2023-04-15'), (10, 5, '2023-04-20'), (14, 5, '2023-05-05'), (20, 5, '2023-06-10'), (24, 5, '2023-07-01'), (30, 5, '2023-08-15'),
(6, 6, '2023-05-01'), (13, 6, '2023-05-10'), (26, 6, '2023-06-05'),
(1, 7, '2023-01-10'), (2, 7, '2023-01-15'), (3, 7, '2023-02-01'), (4, 7, '2023-02-10'), (5, 7, '2023-03-01'), (8, 7, '2023-03-15'), (12, 7, '2023-04-01'), (16, 7, '2023-05-01'),
(7, 8, '2023-06-01'), (11, 8, '2023-06-15'), (18, 8, '2023-07-01'), (22, 8, '2023-08-01'),
(4, 9, '2023-06-10'), (9, 9, '2023-06-20'), (17, 9, '2023-07-05'), (31, 9, '2023-08-10'), (34, 9, '2023-09-01'),
(6, 10, '2023-07-01'), (28, 10, '2023-07-15'),
(1, 11, '2023-07-10'), (8, 11, '2023-07-20'), (15, 11, '2023-08-01'), (19, 11, '2023-09-01'),
(2, 12, '2023-08-01'), (13, 12, '2023-08-15'), (25, 12, '2023-09-01'),
(3, 13, '2023-08-10'), (10, 13, '2023-08-20'), (18, 13, '2023-09-05'), (23, 13, '2023-10-01'), (36, 13, '2023-10-15'),
(5, 14, '2023-09-01'), (20, 14, '2023-09-15'), (27, 14, '2023-10-01'),
(1, 15, '2023-09-10'), (7, 15, '2023-09-20'), (12, 15, '2023-10-05'), (17, 15, '2023-11-01'),
(4, 16, '2023-10-01'), (14, 16, '2023-10-15'),
(2, 17, '2023-10-10'), (9, 17, '2023-10-20'), (11, 17, '2023-11-01'), (21, 17, '2023-11-15'), (32, 17, '2023-12-01'),
(6, 18, '2023-11-01'), (26, 18, '2023-11-15'), (28, 18, '2023-12-01'),
(1, 19, '2023-11-10'), (8, 19, '2023-11-20'), (16, 19, '2023-12-05'), (33, 19, '2023-12-15'),
(3, 20, '2023-12-01'), (15, 20, '2023-12-15'), (24, 20, '2024-01-05'),
(1, 21, '2023-01-05'), (2, 21, '2023-01-10'), (3, 21, '2023-02-01'), (4, 21, '2023-02-15'), (5, 21, '2023-03-01'), (6, 21, '2023-03-15'), (7, 21, '2023-04-01'), (8, 21, '2023-04-15'), (9, 21, '2023-05-01'), (10, 21, '2023-05-15'),
(12, 22, '2024-01-01'), (13, 22, '2024-01-15'), (25, 22, '2024-02-01'), (29, 22, '2024-02-15'),
(4, 23, '2024-01-10'), (11, 23, '2024-01-20'), (17, 23, '2024-02-05'), (34, 23, '2024-02-20'),
(6, 24, '2024-01-15'), (28, 24, '2024-02-01'),
(1, 25, '2024-02-01'), (9, 25, '2024-02-15'), (19, 25, '2024-03-01'),
(2, 26, '2024-02-10'), (14, 26, '2024-02-25'), (22, 26, '2024-03-05'),
(3, 27, '2024-03-01'), (10, 27, '2024-03-10'), (18, 27, '2024-03-20'), (23, 27, '2024-04-01'), (36, 27, '2024-04-15'),
(5, 28, '2024-03-05'), (20, 28, '2024-03-20'),
(1, 29, '2024-03-10'), (7, 29, '2024-03-25'), (12, 29, '2024-04-05'),
(4, 30, '2024-04-01'), (15, 30, '2024-04-15'), (30, 30, '2024-04-25');

-- 4. POPOLAZIONE RECENSIONI (~100 record realistici e variegati)
INSERT INTO recensioni (drinkID, userID, descrizione, dataRecensione, voto) VALUES
(1, 2, 'Il Negroni perfetto, equilibrio straordinario tra amaro e dolce.', '2023-06-10', 5),
(1, 4, 'Buono, ma avrei preferito una percentuale maggiore di Gin.', '2023-06-12', 4),
(1, 7, 'Un classico intramontabile, preparato a regola d''arte.', '2023-07-01', 5),
(1, 11, 'Troppo forte per i miei gusti, ma riconosco la qualità.', '2023-08-15', 3),
(2, 3, 'Mojito rinfrescante, ottima la quantità di menta fresca.', '2023-07-01', 5),
(2, 5, 'Buono, ma il rum era un po'' troppo aggressivo.', '2023-07-10', 4),
(2, 12, 'Il miglior Mojito che abbia mai bevuto in città!', '2023-08-05', 5),
(3, 5, 'Moscow Mule servito nel classico bicchiere di rame, ottimo.', '2023-08-01', 5),
(3, 13, 'Zenzero troppo poco pronunciato, deludente.', '2023-08-20', 2),
(4, 2, 'Espresso Martini fantastico, la crema di caffè era perfetta.', '2023-09-01', 5),
(4, 9, 'Buono, ma un po'' troppo dolce per essere un vero espresso martini.', '2023-09-15', 4),
(4, 23, 'Mi ha svegliato al instante! Ottimo rapporto qualità-prezzo.', '2023-10-01', 5),
(5, 6, 'Mai Tai un po'' troppo dolce e sciropposo per i miei gusti.', '2023-08-02', 3),
(5, 14, 'Autentico sapore tropicale, mi ha trasportato alle Hawaii.', '2023-09-10', 5),
(7, 4, 'Americano leggero e perfetto come aperitivo pre-cena.', '2023-09-05', 5),
(7, 15, 'Buono, ma la soda era quasi piatta.', '2023-09-20', 3),
(8, 3, 'Dry Martini elegante, gin di ottima qualità.', '2023-10-01', 5),
(8, 11, 'Troppo secco, quasi solo gin. Non per tutti.', '2023-10-15', 3),
(9, 4, 'Manhattan intenso e strutturato, ottimo whiskey.', '2023-10-10', 5),
(9, 17, 'Classico americano rispettato in ogni dettaglio.', '2023-11-01', 5),
(10, 5, 'Daiquiri essenziale e pulito, ottimo equilibrio acido.', '2023-11-05', 5),
(10, 27, 'Il lime non era freschissimo, peccato.', '2023-11-20', 3),
(11, 8, 'Whiskey Sour con albume montato a perfezione.', '2023-11-10', 5),
(11, 17, 'Buono, ma l''albume sapeva leggermente di uovo.', '2023-12-01', 3),
(12, 2, 'Margarita con bordo di sale perfetto, molto rinfrescante.', '2023-12-05', 5),
(12, 22, 'Tequila di buona qualità, triple sec ben bilanciato.', '2024-01-10', 4),
(13, 6, 'Cosmopolitan fruttato e elegante, colore bellissimo.', '2024-01-15', 5),
(13, 12, 'Un po'' troppo acido, avrei aggiunto più sciroppo.', '2024-01-20', 3),
(14, 5, 'Bloody Mary speziato al punto giusto, ottimo brunch.', '2024-02-01', 5),
(14, 26, 'Troppo Tabasco, ha coperto il sapore del pomodoro.', '2024-02-10', 2),
(15, 3, 'French 75 frizzante e sofisticato, perfetto per le feste.', '2024-02-15', 5),
(15, 20, 'Champagne di buona qualità, cocktail ben riuscito.', '2024-02-25', 4),
(16, 7, 'Vesper elegante, mi sento quasi come James Bond.', '2024-03-01', 5),
(16, 19, 'Interessante, ma il Lillet Blanc era poco percepibile.', '2024-03-10', 4),
(17, 2, 'Boulevardier robusto, l''alternativa whiskey al Negroni funziona.', '2024-03-15', 5),
(17, 15, 'Ottimo, ma il bourbon era un po'' troppo giovane.', '2024-03-20', 4),
(18, 8, 'Caipirinha autentica, cachaca di ottima qualità.', '2024-04-01', 5),
(18, 13, 'Rinfrescante, ma lo zucchero non era sciolto bene.', '2024-04-10', 3),
(19, 11, 'Clover Club delicato e floreale, ottimo uso del lampone.', '2024-04-15', 5),
(20, 5, 'Dark n Stormy speziato e avvolgente, ginger beer ottima.', '2024-04-20', 5),
(20, 14, 'Buono, ma il rum scuro era di bassa lega.', '2024-04-25', 3),
(21, 3, 'Gin Fizz classico, la schiuma era persistente e leggera.', '2024-05-01', 5),
(21, 17, 'Rinfrescante, perfetto per una giornata calda.', '2024-05-05', 4),
(22, 8, 'John Collins equilibrato, non troppo zuccherino.', '2024-05-10', 5),
(23, 13, 'Planters Punch tropicale e complesso, ottima miscela di rum.', '2024-05-15', 5),
(24, 5, 'Pisco Sour cremoso e con il giusto tocco di amaro.', '2024-05-20', 5),
(24, 20, 'Il pisco era un po'' troppo aggressivo, sbilanciato.', '2024-05-25', 3),
(25, 12, 'Sea Breeze leggero e dissetante, ottimo per l''estate.', '2024-06-01', 4),
(26, 6, 'Sex on the Beach molto dolce, quasi come un succo.', '2024-06-05', 3),
(26, 18, 'Divertente e colorato, perfetto per iniziare la serata.', '2024-06-10', 4),
(27, 14, 'Singapore Sling complesso e aromatico, una vera esperienza.', '2024-06-15', 5),
(28, 10, 'Tequila Sunrise visivamente bellissimo e buono.', '2024-06-20', 4),
(28, 24, 'Troppo sciroppo di granatina, stucchevole.', '2024-06-25', 2),
(29, 22, 'Tommys Margarita superiore all''originale, l''agave fa la differenza.', '2024-07-01', 5),
(30, 5, 'Bramble moderno e fruttato, il liquore alla mora è geniale.', '2024-07-05', 5),
(30, 30, 'Ottimo, ma il gin era troppo predominante.', '2024-07-10', 4),
(31, 9, 'Last Word bilanciato perfettamente, il chartreuse domina con eleganza.', '2024-07-15', 5),
(31, 19, 'Troppo erbaceo per i miei gusti, ma tecnicamente ineccepibile.', '2024-07-20', 4),
(32, 17, 'Paper Plane sorprendente, l''Amaro Nonino si sposa bene col bourbon.', '2024-08-01', 5),
(33, 19, 'Naked and Famous affumicato e agrumato, molto interessante.', '2024-08-05', 5),
(33, 27, 'Il mezcal era troppo potente, ha coperto gli altri ingredienti.', '2024-08-10', 3),
(34, 5, 'Penicillin speziato e avvolgente, lo zenzero fresco si sente.', '2024-08-15', 5),
(34, 23, 'Ottima idea, ma il miele era troppo presente.', '2024-08-20', 4),
(35, 9, 'Corpse Reviver No 2 storico e ben eseguito, l''assenzio è il tocco finale.', '2024-09-01', 5),
(36, 13, 'Paloma rinfrescante e alternativa, la soda al pompelmo è perfetta.', '2024-09-05', 5),
(36, 27, 'Semplice ma efficace, molto bevibile.', '2024-09-10', 4),
(1, 25, 'Sempre una garanzia, non delude mai.', '2024-09-15', 5),
(2, 26, 'Buono, ma ho avuto Mojito migliori.', '2024-09-20', 3),
(4, 30, 'Caffè di ottima qualità, si sente.', '2024-09-25', 5),
(8, 25, 'Vermouth di qualità, gin eccellente.', '2024-10-01', 5),
(12, 29, 'Margarita classico, niente da eccepire.', '2024-10-05', 4),
(17, 25, 'Boulevardier robusto, perfetto per l''inverno.', '2024-10-10', 5);

-- 5. AGGIORNAMENTO AUTOMATICO DEI CONTATORI UTENTI (Per coerenza dei dati)
-- Aggiorna il numero totale di recensioni effettuate da ogni utente
UPDATE Utenti u
JOIN (SELECT userID, COUNT(*) as cnt FROM recensioni GROUP BY userID) r ON u.userID = r.userID
SET u.numeroRecensioniEffettuate = r.cnt;

-- Aggiorna il numero di recensioni positive (voto >= 4) per ogni utente
UPDATE Utenti u
JOIN (SELECT userID, COUNT(*) as cnt FROM recensioni WHERE voto >= 4 GROUP BY userID) r ON u.userID = r.userID
SET u.numeroRecensioniPositive = r.cnt;

-- (Opzionale) Assegna un paio di ricette create ai Barman per popolare la tabella 'creazioni'
INSERT INTO creazioni (drinkID, dataCreazione, userID, barID) VALUES
(31, '2023-07-15', 5, 1),  -- Marco Gialli ha creato/salvato il Last Word al Bar Manhattan
(34, '2023-08-20', 13, 4), -- Lorenzo Marini ha creato il Penicillin al Dry Martini Club
(36, '2024-03-01', 27, 6); -- Tommaso Greco ha creato il Paloma all'Havana Nights

-- Aggiorna il contatore ricette create per questi utenti
UPDATE Utenti SET numeroRicetteCreate = 1 WHERE userID IN (5, 13, 27);
UPDATE Utenti SET numeroRicetteCreate = 2 WHERE userID IN (2); -- Luca Bianchi ne ha 2