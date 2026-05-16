-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Thu May 14 14:08:41 2026 
-- * LUN file: C:\Users\namel\OneDrive - Alma Mater Studiorum Università di Bologna\BASI DI DATI\progetto\progettoBasiDiDati2.lun 
-- * Schema: LOGICO2/1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database MixologyDB;
use MixologyDB;


-- Tables Section
-- _____________ 

-- Tables Section
-- _____________ 

create table Bar (
     barID int auto_increment not null,
     nomeBar varchar(128) not null,
     città varchar(30) not null,
     indirizzo varchar(60) not null,
     constraint IDBar primary key (barID));

create table Categorie (
     nomeCategoria varchar(64) not null,
     descrizione varchar(1000) not null,
     constraint IDCategoria primary key (nomeCategoria));

create table composizioni (
     nomeIngrediente varchar(32) not null,
     drinkID int  not null,
     quantita float not null,
     unitaDiMisura varchar(32) not null,
     constraint IDcomposizione primary key (drinkID, nomeIngrediente));

create table creazioni (
     drinkID int not null,
     dataCreazione date not null,
     userID int not null,
     constraint FKcre_Dri_ID primary key (drinkID));

create table creazioniBar (
     drinkID int not null,
     dataCreazione date not null,
     barID int not null,
     constraint FKcreBar_Dri_ID primary key (drinkID)); -- NOME CORRETTO

create table Drink (
     drinkID int auto_increment not null,
     nome varchar(32) not null,
     descrizione varchar(1000) not null,
     foto varchar(256) not null,
     nomeCategoria varchar(64) not null,
     IBA boolean not null default false,
     constraint IDDrink_ID primary key (drinkID));

create table identificazioni (
     drinkID int not null,
     keyword varchar(32) not null,
     constraint IDidentificazione primary key (drinkID, keyword));

create table Ingredienti (
     nomeIngrediente varchar(32) not null,
     volteUtilizzato int not null,
     constraint IDIngrediente primary key (nomeIngrediente));

create table occupazioni (
     userID int not null,
     barID int not null,
     constraint FKocc_Ute_ID primary key (userID));

create table recensioni (
     drinkID int not null,
     userID int not null,
     descrizione varchar(512) not null,
     dataRecensione date not null,
     voto int not null,
     constraint IDrecensione primary key (drinkID, userID));

create table salvataggioPreferiti (
     drinkID int not null,
     userID int not null,
     dataSalvataggio date not null,
     constraint IDsalvataggioPreferiti primary key (userID, drinkID));

create table Tag (
     keyword varchar(32) not null,
     constraint IDTag primary key (keyword));

create table Utenti (
     userID int auto_increment not null,
     email varchar(128) not null unique,
     password varchar(256) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     dataNascita date not null,
     ruoloUtente varchar(20) not null,
     dataIscrizione date not null,
     numeroRicetteCreate int not null,
     numeroRecensioniPositive int not null,
     numeroRecensioniEffettuate int not null,
     constraint IDUtente primary key (userID));


-- Constraints Section
-- ___________________ 

alter table composizioni add constraint FKcom_Dri
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE; 

alter table composizioni add constraint FKcom_Ing
     foreign key (nomeIngrediente)
     references Ingredienti (nomeIngrediente);

alter table creazioni add constraint FKcre_Ute
     foreign key (userID)
     references Utenti (userID) ON DELETE CASCADE;

alter table creazioni add constraint FKcre_Dri_FK
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE;

alter table creazioniBar add constraint FKcre_Bar
     foreign key (barID)
     references Bar (barID) ON DELETE CASCADE;

alter table creazioniBar add constraint FKbarCre_Dri_FK
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE;

alter table Drink add constraint FKR
     foreign key (nomeCategoria)
     references Categorie (nomeCategoria);

alter table identificazioni add constraint FKide_Tag
     foreign key (keyword)
     references Tag (keyword);

alter table identificazioni add constraint FKide_Dri
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE; 

alter table occupazioni add constraint FKocc_Ute_FK
     foreign key (userID)
     references Utenti (userID) ON DELETE CASCADE;

alter table occupazioni add constraint FKocc_Bar
     foreign key (barID)
     references Bar (barID) ON DELETE CASCADE;

alter table recensioni add constraint FKrec_Ute
     foreign key (userID)
     references Utenti (userID) ON DELETE CASCADE;

alter table recensioni add constraint FKrec_Dri
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE;

alter table salvataggioPreferiti add constraint FKsal_Ute
     foreign key (userID)
     references Utenti (userID) ON DELETE CASCADE;

alter table salvataggioPreferiti add constraint FKsal_Dri
     foreign key (drinkID)
     references Drink (drinkID) ON DELETE CASCADE;

-- Index Section
-- _____________ 

