-- POPOLAZIONE CATEGORIE DRINK
INSERT INTO Categorie (nomeCategoria, descrizione) VALUES
('The Unforgettables', 'I grandi classici del mixology.'),
('Contemporary Classics', 'Drink classici ma di epoca moderna.'),
('New Era Drinks', 'Cocktail innovativi.'),
('Tiki', 'Cocktail esotici a base di frutta e rum.'),
('Mocktail', 'Drink analcolici.');

-- UTENTE ANONIMO
INSERT INTO Utenti(email, password, nome, cognome, dataNascita, ruoloUtente, dataIscrizione, numeroRicetteCreate, numeroRecensioniPositive, numeroRecensioniEffettuate)
VALUES ('anonimo', '0', 'utente', 'anonimo', '2000-01-01', 'Anonimo', DATE(NOW()), 0, 0, 0);

-- DRINK IBA 
INSERT INTO Drink (drinkID, nome, descrizione, foto, nomeCategoria, IBA) VALUES
(1, 'Negroni', 'Un classico cocktail italiano, preparato con ghiaccio e mescolato. Guarnire con mezza fetta d''arancia.', 'negroni.jpeg', 'The Unforgettables', TRUE),
(2, 'Mojito', 'Un tradizionale highball cubano, perfettamente rinfrescante con menta e lime.', 'mojito.jpeg', 'Contemporary Classics', TRUE),
(3, 'Moscow Mule', 'Un vodka buck speziato e rinfrescante, tipicamente servito in una tazza di rame.', 'moscow_mule.jpeg', 'Contemporary Classics', TRUE),
(4, 'Espresso Martini', 'Un cocktail freddo al gusto di caffè, perfetto per darsi una carica.', 'espresso_martini.jpeg', 'New Era Drinks', TRUE),
(5, 'Mai Tai', 'Il cocktail Tiki per eccellenza, inventato da Trader Vic nel 1944.', 'mai_tai.jpeg', 'Tiki', TRUE),
(6, 'Shirley Temple', 'Una bevanda analcolica dolce, intitolata alla famosa bambina prodigio del cinema.', 'shirley_temple.jpeg', 'Mocktail', TRUE),
(7, 'Americano', 'Cocktail italiano leggero e amaricante, servito con ghiaccio e scorza d''arancia.', 'americano.jpg', 'The Unforgettables', TRUE),
(8, 'Dry Martini', 'Elegante cocktail a base di gin e vermouth dry, simbolo della mixology classica.', 'dry_martini.jpg', 'The Unforgettables', TRUE),
(9, 'Manhattan', 'Cocktail classico americano a base di whiskey e vermouth rosso.', 'manhattan.jpg', 'The Unforgettables', TRUE),
(10, 'Daiquiri', 'Cocktail cubano essenziale a base di rum, lime e zucchero.', 'daiquiri.jpg', 'The Unforgettables', TRUE),
(11, 'Whiskey Sour', 'Un sour equilibrato con whiskey, limone e zucchero.', 'whiskey_sour.jpg', 'The Unforgettables', TRUE),
(12, 'Margarita', 'Cocktail messicano iconico a base di tequila, triple sec e lime.', 'margarita.jpg', 'Contemporary Classics', TRUE),
(13, 'Cosmopolitan', 'Cocktail moderno a base di vodka, triple sec, lime e cranberry.', 'cosmopolitan.jpg', 'Contemporary Classics', TRUE),
(14, 'Bloody Mary', 'Cocktail speziato a base di vodka e succo di pomodoro.', 'bloody_mary.jpg', 'Contemporary Classics', TRUE),
(15, 'French 75', 'Cocktail frizzante con gin e champagne.', 'french_75.jpg', 'Contemporary Classics', TRUE),
(16, 'Vesper', 'Celebre cocktail associato a James Bond.', 'vesper.jpg', 'New Era Drinks', TRUE),
(17, 'Boulevardier', 'Versione whiskey del Negroni, intensa e strutturata.', 'boulevardier.jpg', 'Contemporary Classics', TRUE),
(18, 'Caipirinha', 'Cocktail brasiliano a base di cachaca, lime e zucchero.', 'caipirinha.jpg', 'Contemporary Classics', TRUE),
(19, 'Clover Club', 'Cocktail elegante con gin, lampone e limone.', 'clover_club.jpg', 'Contemporary Classics', TRUE),
(20, 'Dark n Stormy', 'Cocktail speziato con rum scuro e ginger beer.', 'dark_n_stormy.jpg', 'Contemporary Classics', TRUE),
(21, 'Gin Fizz', 'Cocktail agrumato e frizzante con gin e soda.', 'gin_fizz.jpg', 'The Unforgettables', TRUE),
(22, 'John Collins', 'Long drink classico a base di gin e limone.', 'john_collins.jpg', 'The Unforgettables', TRUE),
(23, 'Planters Punch', 'Cocktail tropicale a base di rum e agrumi.', 'planters_punch.jpg', 'Contemporary Classics', TRUE),
(24, 'Pisco Sour', 'Cocktail peruviano a base di pisco e limone.', 'pisco_sour.jpg', 'Contemporary Classics', TRUE),
(25, 'Sea Breeze', 'Cocktail leggero con vodka e succhi di frutta.', 'sea_breeze.jpg', 'Contemporary Classics', TRUE),
(26, 'Sex on the Beach', 'Cocktail fruttato e dolce molto popolare.', 'sex_on_the_beach.jpg', 'Contemporary Classics', TRUE),
(27, 'Singapore Sling', 'Cocktail complesso e aromatico nato a Singapore.', 'singapore_sling.jpg', 'Contemporary Classics', TRUE),
(28, 'Tequila Sunrise', 'Cocktail colorato a base di tequila e arancia.', 'tequila_sunrise.jpg', 'Contemporary Classics', TRUE),
(29, 'Tommys Margarita', 'Versione moderna del Margarita con sciroppo d''agave.', 'tommys_margarita.jpg', 'New Era Drinks', TRUE),
(30, 'Bramble', 'Cocktail moderno a base di gin e mora.', 'bramble.jpg', 'New Era Drinks', TRUE),
(31, 'Last Word', 'Cocktail bilanciato con gin, chartreuse e maraschino.', 'last_word.jpg', 'New Era Drinks', TRUE),
(32, 'Paper Plane', 'Cocktail moderno con bourbon e aperitivi amari.', 'paper_plane.jpg', 'New Era Drinks', TRUE),
(33, 'Naked and Famous', 'Cocktail contemporaneo con mezcal e aperitivi.', 'naked_and_famous.jpg', 'New Era Drinks', TRUE),
(34, 'Penicillin', 'Cocktail moderno con whisky e zenzero.', 'penicillin.jpg', 'New Era Drinks', TRUE),
(35, 'Corpse Reviver No 2', 'Classico pre-proibizionismo rinato in epoca moderna.', 'corpse_reviver_no_2.jpg', 'New Era Drinks', TRUE),
(36, 'Paloma', 'Cocktail messicano fresco e agrumato.', 'paloma.jpg', 'New Era Drinks', TRUE);

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
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Vermouth Dry', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Whiskey Rye', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Angostura Bitters', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Sciroppo di Zucchero', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo di Limone Fresco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Tequila Blanco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Triple Sec', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo di Cranberry', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo di Pomodoro', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Salsa Worcestershire', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Tabasco', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Champagne', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Vodka Citron', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Lillet Blanc', 0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Bourbon',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Cachaca',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Sciroppo di Lampone',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Albume',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Pisco',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo di Pompelmo',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Liquore alla Pesca',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Succo d''Arancia',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Cherry Heering',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Benedictine',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Sciroppo d''Agave',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Liquore alla Mora',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Chartreuse Verde',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Liquore Maraschino',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Aperol',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Amaro Nonino',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Mezcal',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Chartreuse Gialla',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Sciroppo di Miele',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Assenzio',0);
INSERT INTO Ingredienti (nomeIngrediente, volteUtilizzato) VALUES ('Soda al Pompelmo',0);

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

-- Americano
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Campari', 7, 3, 'cl'),
('Vermouth Rosso Dolce', 7, 3, 'cl'),
('Soda', 7, 1, 'spruzzata');

-- Dry Martini
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Gin', 8, 6, 'cl'),
('Vermouth Dry', 8, 1, 'cl');

-- Manhattan
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Whiskey Rye', 9, 5, 'cl'),
('Vermouth Rosso Dolce', 9, 2, 'cl'),
('Angostura Bitters', 9, 2, 'dash');

-- Daiquiri
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Rum Bianco', 10, 6, 'cl'),
('Succo di Lime Fresco', 10, 2, 'cl'),
('Sciroppo di Zucchero', 10, 0.5, 'cl');

-- Whiskey Sour
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Whiskey Rye', 11, 4.5, 'cl'),
('Succo di Limone Fresco', 11, 3, 'cl'),
('Sciroppo di Zucchero', 11, 1.5, 'cl');

-- Margarita
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Tequila Blanco', 12, 5, 'cl'),
('Triple Sec', 12, 2, 'cl'),
('Succo di Lime Fresco', 12, 1.5, 'cl');

-- Cosmopolitan
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Vodka Citron', 13, 4, 'cl'),
('Triple Sec', 13, 1.5, 'cl'),
('Succo di Cranberry', 13, 3, 'cl'),
('Succo di Lime Fresco', 13, 1, 'cl');

-- Bloody Mary
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Vodka', 14, 4.5, 'cl'),
('Succo di Pomodoro', 14, 9, 'cl'),
('Succo di Limone Fresco', 14, 1.5, 'cl'),
('Salsa Worcestershire', 14, 3, 'gocce'),
('Tabasco', 14, 2, 'gocce');

-- French 75
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Gin', 15, 3, 'cl'),
('Succo di Limone Fresco', 15, 1.5, 'cl'),
('Sciroppo di Zucchero', 15, 1.5, 'cl'),
('Champagne', 15, 6, 'cl');

-- Vesper
INSERT INTO composizioni (nomeIngrediente, drinkID, quantita, unitaDiMisura) VALUES
('Gin', 16, 6, 'cl'),
('Vodka', 16, 2, 'cl'),
('Lillet Blanc', 16, 0.75, 'cl');

-- Boulevardier
INSERT INTO composizioni VALUES ('Bourbon',17,3,'cl');
INSERT INTO composizioni VALUES ('Campari',17,3,'cl');
INSERT INTO composizioni VALUES ('Vermouth Rosso Dolce',17,3,'cl');

-- Caipirinha
INSERT INTO composizioni VALUES ('Cachaca',18,5,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',18,2,'cl');
INSERT INTO composizioni VALUES ('Zucchero di Canna Bianco',18,2,'cucchiaini');

-- Clover Club
INSERT INTO composizioni VALUES ('Gin',19,4.5,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Lampone',19,1.5,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',19,1.5,'cl');
INSERT INTO composizioni VALUES ('Albume',19,1,'pezzo');

-- Dark n Stormy
INSERT INTO composizioni VALUES ('Rum Scuro',20,6,'cl');
INSERT INTO composizioni VALUES ('Ginger Beer',20,10,'cl');

-- Gin Fizz
INSERT INTO composizioni VALUES ('Gin',21,4.5,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',21,3,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Zucchero',21,1,'cl');
INSERT INTO composizioni VALUES ('Soda',21,1,'spruzzata');

-- John Collins
INSERT INTO composizioni VALUES ('Gin',22,4.5,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',22,3,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Zucchero',22,1.5,'cl');
INSERT INTO composizioni VALUES ('Soda',22,1,'spruzzata');

-- Planters Punch
INSERT INTO composizioni VALUES ('Rum Scuro',23,4.5,'cl');
INSERT INTO composizioni VALUES ('Succo d''Arancia',23,3,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',23,1.5,'cl');
INSERT INTO composizioni VALUES ('Granatina',23,1,'cl');

-- Pisco Sour
INSERT INTO composizioni VALUES ('Pisco',24,6,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',24,3,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Zucchero',24,2,'cl');
INSERT INTO composizioni VALUES ('Albume',24,1,'pezzo');

-- Sea Breeze
INSERT INTO composizioni VALUES ('Vodka',25,4,'cl');
INSERT INTO composizioni VALUES ('Succo di Cranberry',25,9,'cl');
INSERT INTO composizioni VALUES ('Succo di Pompelmo',25,3,'cl');

-- Sex on the Beach
INSERT INTO composizioni VALUES ('Vodka',26,4,'cl');
INSERT INTO composizioni VALUES ('Liquore alla Pesca',26,2,'cl');
INSERT INTO composizioni VALUES ('Succo d''Arancia',26,4,'cl');
INSERT INTO composizioni VALUES ('Succo di Cranberry',26,4,'cl');

-- Singapore Sling
INSERT INTO composizioni VALUES ('Gin',27,3,'cl');
INSERT INTO composizioni VALUES ('Cherry Heering',27,1.5,'cl');
INSERT INTO composizioni VALUES ('Benedictine',27,0.75,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',27,1.5,'cl');

-- Tequila Sunrise
INSERT INTO composizioni VALUES ('Tequila Blanco',28,4.5,'cl');
INSERT INTO composizioni VALUES ('Succo d''Arancia',28,9,'cl');
INSERT INTO composizioni VALUES ('Granatina',28,1.5,'cl');

-- Tommys Margarita
INSERT INTO composizioni VALUES ('Tequila Blanco',29,6,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',29,3,'cl');
INSERT INTO composizioni VALUES ('Sciroppo d''Agave',29,1.5,'cl');

-- Bramble
INSERT INTO composizioni VALUES ('Gin',30,5,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',30,2.5,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Zucchero',30,1,'cl');
INSERT INTO composizioni VALUES ('Liquore alla Mora',30,1.5,'cl');

-- Last Word
INSERT INTO composizioni VALUES ('Gin',31,2.25,'cl');
INSERT INTO composizioni VALUES ('Chartreuse Verde',31,2.25,'cl');
INSERT INTO composizioni VALUES ('Liquore Maraschino',31,2.25,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',31,2.25,'cl');

-- Paper Plane
INSERT INTO composizioni VALUES ('Bourbon',32,2.25,'cl');
INSERT INTO composizioni VALUES ('Aperol',32,2.25,'cl');
INSERT INTO composizioni VALUES ('Amaro Nonino',32,2.25,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',32,2.25,'cl');

-- Naked and Famous
INSERT INTO composizioni VALUES ('Mezcal',33,2.25,'cl');
INSERT INTO composizioni VALUES ('Aperol',33,2.25,'cl');
INSERT INTO composizioni VALUES ('Chartreuse Gialla',33,2.25,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',33,2.25,'cl');

-- Penicillin
INSERT INTO composizioni VALUES ('Bourbon',34,6,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',34,2.25,'cl');
INSERT INTO composizioni VALUES ('Sciroppo di Miele',34,2.25,'cl');

-- Corpse Reviver No 2
INSERT INTO composizioni VALUES ('Gin',35,2.25,'cl');
INSERT INTO composizioni VALUES ('Triple Sec',35,2.25,'cl');
INSERT INTO composizioni VALUES ('Liquore Maraschino',35,2.25,'cl');
INSERT INTO composizioni VALUES ('Succo di Limone Fresco',35,2.25,'cl');
INSERT INTO composizioni VALUES ('Assenzio',35,1,'spruzzata');

-- Paloma
INSERT INTO composizioni VALUES ('Tequila Blanco',36,5,'cl');
INSERT INTO composizioni VALUES ('Soda al Pompelmo',36,10,'cl');
INSERT INTO composizioni VALUES ('Succo di Lime Fresco',36,1.5,'cl');

 -- POPOLAZIONE TABELLA TAG
INSERT INTO Tag (keyword) VALUES 
('Amaro'), ('Forte'), ('Classico'), ('Rinfrescante'), 
('Dolce'), ('Tropicale'), ('Frizzante'), ('Caffè'), ('Analcolico'),
('Agrumato'), ('Secco'), ('Speziato'), ('Elegante'), ('Aperitivo');

-- IDENTIFICAZIONI
INSERT INTO identificazioni (drinkID, keyword) VALUES
(1, 'Amaro'), (1, 'Forte'), (1, 'Classico'),
(2, 'Rinfrescante'), (2, 'Dolce'), (2, 'Tropicale'),
(3, 'Frizzante'), (3, 'Rinfrescante'),
(4, 'Caffè'), (4, 'Forte'),
(5, 'Tropicale'), (5, 'Dolce'), (5, 'Forte'),
(6, 'Analcolico'), (6, 'Dolce'), (6, 'Frizzante'),
(7, 'Aperitivo'), (7, 'Amaro'),
(8, 'Secco'), (8, 'Elegante'),
(9, 'Forte'), (9, 'Classico'),
(10, 'Agrumato'), (10, 'Rinfrescante'),
(11, 'Agrumato'), (11, 'Forte'),
(12, 'Agrumato'), (12, 'Rinfrescante'),
(13, 'Dolce'), (13, 'Agrumato'),
(14, 'Speziato'),(14, 'Forte'),
(15, 'Frizzante'), (15, 'Elegante'),
(16, 'Forte'), (16, 'Elegante'),
(17, 'Classico'), (17, 'Amaro'), (17, 'Forte'), (17, 'Aperitivo'),
(18, 'Rinfrescante'), (18, 'Dolce'), (18, 'Tropicale'),
(19, 'Classico'), (19, 'Elegante'), (19, 'Dolce'), (19, 'Agrumato'),
(20, 'Rinfrescante'), (20, 'Speziato'), (20, 'Forte'),
(21, 'Rinfrescante'), (21, 'Frizzante'), (21, 'Agrumato'),
(22, 'Rinfrescante'), (22, 'Frizzante'), (22, 'Agrumato'),
(23, 'Tropicale'), (23, 'Dolce'), (23, 'Forte'),
(24, 'Agrumato'), (24, 'Secco'), (24, 'Elegante'),
(25, 'Rinfrescante'), (25, 'Agrumato'), (25, 'Dolce'),
(26, 'Dolce'), (26, 'Tropicale'), (26, 'Rinfrescante'),
(27, 'Tropicale'), (27, 'Dolce'), (27, 'Elegante'),
(28, 'Dolce'), (28, 'Tropicale'), (28, 'Agrumato'),
(29, 'Secco'), (29, 'Agrumato'), (29, 'Classico'),
(30, 'Agrumato'), (30, 'Dolce'), (30, 'Rinfrescante'),
(31, 'Forte'), (31, 'Elegante'), (31, 'Secco'), (31, 'Speziato'),
(32, 'Aperitivo'), (32, 'Amaro'), (32, 'Agrumato'), (32, 'Elegante'),
(33, 'Aperitivo'), (33, 'Speziato'), (33, 'Agrumato'), (33, 'Forte'),
(34, 'Speziato'), (34, 'Forte'), (34, 'Agrumato'),
(35, 'Classico'), (35, 'Forte'), (35, 'Elegante'), (35, 'Agrumato'),
(36, 'Rinfrescante'), (36, 'Frizzante'), (36, 'Agrumato'), (36, 'Aperitivo');

-- AGGIORNAMENTO CONTATORI 

UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Campari', 'Vermouth Rosso Dolce');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Bianco', 'Succo di Lime Fresco', 'Foglie di Menta', 'Zucchero di Canna Bianco', 'Soda');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Ginger Beer', 'Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Liquore al Caffè', 'Caffè Espresso');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Scuro', 'Rum Bianco', 'Orange Curacao', 'Sciroppo d''Orzata', 'Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Ginger Ale', 'Granatina', 'Zucchero di Canna Bianco', 'Soda');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Campari','Vermouth Rosso Dolce','Soda');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin','Vermouth Dry');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Whiskey Rye','Vermouth Rosso Dolce','Angostura Bitters');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Bianco','Succo di Lime Fresco','Sciroppo di Zucchero');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Whiskey Rye','Succo di Limone Fresco','Sciroppo di Zucchero');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Tequila Blanco','Triple Sec','Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka Citron','Triple Sec','Succo di Cranberry','Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka','Succo di Pomodoro','Succo di Limone Fresco','Salsa Worcestershire','Tabasco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin','Succo di Limone Fresco','Sciroppo di Zucchero','Champagne');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin','Vodka','Lillet Blanc');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Bourbon', 'Campari', 'Vermouth Rosso Dolce');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Cachaca', 'Succo di Lime Fresco', 'Zucchero di Canna Bianco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Sciroppo di Lampone', 'Succo di Limone Fresco', 'Albume');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Scuro', 'Ginger Beer');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Succo di Limone Fresco', 'Sciroppo di Zucchero', 'Soda');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Succo di Limone Fresco', 'Sciroppo di Zucchero', 'Soda');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Rum Scuro', 'Succo d''Arancia', 'Succo di Lime Fresco', 'Granatina');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Pisco', 'Succo di Limone Fresco', 'Sciroppo di Zucchero', 'Albume');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Succo di Cranberry', 'Succo di Pompelmo');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Vodka', 'Liquore alla Pesca', 'Succo d''Arancia', 'Succo di Cranberry');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Cherry Heering', 'Benedictine', 'Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Tequila Blanco', 'Succo d''Arancia', 'Granatina');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Tequila Blanco', 'Succo di Lime Fresco', 'Sciroppo d''Agave');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Succo di Limone Fresco', 'Sciroppo di Zucchero', 'Liquore alla Mora');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Chartreuse Verde', 'Liquore Maraschino', 'Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Bourbon', 'Aperol', 'Amaro Nonino', 'Succo di Limone Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Mezcal', 'Aperol', 'Chartreuse Gialla', 'Succo di Lime Fresco');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Bourbon', 'Succo di Limone Fresco', 'Sciroppo di Miele');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Gin', 'Triple Sec', 'Liquore Maraschino', 'Succo di Limone Fresco', 'Assenzio');
UPDATE Ingredienti SET volteUtilizzato = volteUtilizzato + 1 WHERE nomeIngrediente IN ('Tequila Blanco', 'Soda al Pompelmo', 'Succo di Lime Fresco');
