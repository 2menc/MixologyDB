# Applicazione per visualizzazione, salvataggio e condivisione di ricette, preparazioni e ingredienti per cocktail riconosciuti e creati dall’utente.
<img width="502" height="502" alt="applicationIconRedim" src="https://github.com/user-attachments/assets/318a2605-f628-4f60-bf21-fe95cfddfe8f" />

# Obiettivo
Creare un programma in cui un utente, iscritto con le proprie informazioni può visualizzare i cocktail riconosciuti dall’IBA, crearne di nuovi personalizzati e pubblicarli rendendoli visibili anche agli altri utenti. Inoltre, è possibile cercare i drink adatti ai propri gusti (tramite parole chiave, ad esempio: “dolce”, “amaro”, “rosso” ...  e ingredienti “gin”, “fragola” ...).

Un utente non registrato potrà solo visualizzare i drink, un utente registrato, invece, potrà anche crearli e pubblicarli. 

Ci sarà la possibilità di scaricare le informazioni dei drink creati per condividerli: verranno lette e salvate come file le informazioni del drink.

Ci sarà la possibilità di aggiungere recensioni alle ricette.

***

### Quindi, le funzionalità offerte dal programma saranno le seguenti:
1)	Registrazione di un nuovo utente o accesso se si ha già un account;
2)	Visualizzazione dei drink;
3)	Creazione di nuovi drink;
4)	Aggiunta di drink ai “preferiti”;
5)	Salvataggio su file dei dati dei drink;
6)	Ricerca di un drink tramite parole chiave.

# Prima di avviare l'applicativo:
1) si crei il database locale (nome: "MixologyDB", utente: "root", password: "Password");
2) si eseguano le istruzioni contenute in "MixologyDB.ddl"; 

### inoltre, si consiglia, prima di avviare l'applicativo, di popolare il database con i dati di default, eseguendo le istruzioni contenute in "src/main/mysql/popolamento.sql".
(l'applicativo è progettato per essere eseguito anche senza il popolamento iniziale, ma alcune funzioni potrebbero essere limitate).

***

### Si noti che nella stessa directory è presente un file di popolamento aggiuntivo, non strettamente necessario per l'avvio dell'applicazione, ma mostra il corretto funzionamento di alcune feature (ad es. recensioni, preferiti, ...). 
Inoltre, è importante posizionare la cartella "images" nella directory da cui si avvia il progetto (insieme a "src/" oppure, nel caso
di file jar, in una directory insieme al .jar stesso). Anche in questo caso, l'applicativo è progettato per essere eseguito anche senza
questo passaggio, ma in tal caso le foto non saranno visibili.

***

Per provare l'applicativo, è sufficiente registrarsi e/o effettuare il login in uno degli account già presenti in "popolamentoAggiuntivo.sql" 
(es. 'mario.rossi@email.com', 'pwd_1'), oppure entrare come ospite, ma ovviamente in tal caso alcune funzioni saranno limitate (si potrà cambiare 
account in qualsiasi momento).

Per provare le funzioni da admin, si effettui l'accesso con uno degli account presenti in "popolamentoAggiuntivo.sql" avente ruoloUtente = Admin
(es. 'rocco.greco@email.com', 'pwd_31').
