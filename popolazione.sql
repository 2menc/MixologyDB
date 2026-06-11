-- POPOLAZIONE CATEGORIE DRINK
INSERT INTO Categorie (nomeCategoria, descrizione) VALUES
('The Unforgettables', 'I grandi classici del mixology.'),
('Contemporary Classics', 'Drink classici ma di epoca moderna.'),
('New Era Drinks', 'Cocktail innovativi.'),
('Tiki', 'Cocktail esotici a base di frutta e rum.'),
('Mocktail', 'Drink analcolici.');

-- UTENTE ANONIMO
INSERT INTO Utenti(email, password, nome, cognome, dataNascita, ruoloUtente, dataIscrizione, numeroRicetteCreate, numeroRecensioniPositive, numeroRecensioniEffettuate)
VALUES ('anonimo', '0', 'anonimo', 'anonimo', '2000-01-01', 'Anonimo', DATE(NOW()), 0, 0, 0);

-- DRINK IBA 
INSERT INTO Drink (drinkID, nome, descrizione, foto, nomeCategoria, IBA) VALUES
(1, 'Negroni', 'Un classico cocktail italiano, preparato con ghiaccio e mescolato. Guarnire con mezza fetta d''arancia.', 'negroni.jpeg', 'The Unforgettables', TRUE),
(2, 'Mojito', 'Un tradizionale highball cubano, perfettamente rinfrescante con menta e lime.', 'mojito.jpeg', 'Contemporary Classics', TRUE),
(3, 'Moscow Mule', 'Un vodka buck speziato e rinfrescante, tipicamente servito in una tazza di rame.', 'moscow_mule.jpeg', 'Contemporary Classics', TRUE),
(4, 'Espresso Martini', 'Un cocktail freddo al gusto di caffè, perfetto per darsi una carica.', 'espresso_martini.jpeg', 'New Era Drinks', TRUE),
(5, 'Mai Tai', 'Il cocktail Tiki per eccellenza, inventato da Trader Vic nel 1944.', 'mai_tai.jpeg', 'Tiki', TRUE),
(6, 'Shirley Temple', 'Una bevanda analcolica dolce, intitolata alla famosa bambina prodigio del cinema.', 'shirley_temple.jpeg', 'Mocktail', TRUE);

-- INGREDIENTI 
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Gin', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Campari', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Vermouth Rosso Dolce', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Rum Bianco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo di Lime Fresco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Foglie di Menta', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Zucchero di Canna Bianco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Soda', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Vodka', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Ginger Beer', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Caffè Espresso', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Liquore al Caffè', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Rum Scuro', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Orange Curacao', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Sciroppo d''Orzata', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Ginger Ale', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Granatina', 0);


-- COMPOSIZIONI (Ricette con ingredienti e unità di misura in italiano)
-- Negroni
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Gin', 1, 3, 'cl'), ('Campari', 1, 3, 'cl'), ('Vermouth Rosso Dolce', 1, 3, 'cl');
-- Mojito
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Rum Bianco', 2, 4.5, 'cl'), ('Succo di Lime Fresco', 2, 2, 'cl'), ('Foglie di Menta', 2, 6, 'foglie'), 
('Zucchero di Canna Bianco', 2, 2, 'cucchiaini'), ('Soda', 2, 1, 'spruzzata');
-- Moscow Mule
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Vodka', 3, 4.5, 'cl'), ('Ginger Beer', 3, 12, 'cl'), ('Succo di Lime Fresco', 3, 0.5, 'cl');
-- Espresso Martini
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Vodka', 4, 5, 'cl'), ('Liquore al Caffè', 4, 3, 'cl'), ('Caffè Espresso', 4, 1, 'tazzina');
-- Mai Tai
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Rum Scuro', 5, 3, 'cl'), ('Rum Bianco', 5, 3, 'cl'), ('Orange Curacao', 5, 1.5, 'cl'), 
('Sciroppo d''Orzata', 5, 1.5, 'cl'), ('Succo di Lime Fresco', 5, 3, 'cl');
-- Shirley Temple
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Ginger Ale', 6, 15, 'cl'), ('Granatina', 6, 2, 'cl'),
('Zucchero di Canna Bianco', 6, 2, 'cucchiaini'), ('Soda', 6, 1, 'spruzzata');

-- POPOLAZIONE TABELLA TAG (In italiano per evitare errori di Foreign Key) [1]
INSERT INTO Tag (keyword) VALUES 
('Amaro'), ('Forte'), ('Classico'), ('Rinfrescante'), 
('Dolce'), ('Tropicale'), ('Frizzante'), ('Caffè'), ('Analcolico');

-- IDENTIFICAZIONI (Associazione Drink-Tag in italiano) [1]
INSERT INTO identificazioni (drinkID, keyword) VALUES
(1, 'Amaro'), (1, 'Forte'), (1, 'Classico'),
(2, 'Rinfrescante'), (2, 'Dolce'), (2, 'Tropicale'),
(3, 'Frizzante'), (3, 'Rinfrescante'),
(4, 'Caffè'), (4, 'Forte'),
(5, 'Tropicale'), (5, 'Dolce'), (5, 'Forte'),
(6, 'Analcolico'), (6, 'Dolce'), (6, 'Frizzante');

-- AGGIORNAMENTO CONTATORI (Nomi ingredienti aggiornati in italiano)
-- Negroni
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Campari', 'Vermouth Rosso Dolce');
-- Mojito
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Bianco', 'Succo di Lime Fresco', 'Foglie di Menta', 'Zucchero di Canna Bianco', 'Soda');
-- Moscow Mule
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Ginger Beer', 'Succo di Lime Fresco');
-- Espresso Martini
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Liquore al Caffè', 'Caffè Espresso');
-- Mai Tai
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Scuro', 'Rum Bianco', 'Orange Curacao', 'Sciroppo d''Orzata', 'Succo di Lime Fresco');
-- Shirley Temple
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Ginger Ale', 'Granatina', 'Zucchero di Canna Bianco', 'Soda');