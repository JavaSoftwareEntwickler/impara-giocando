# Impara Giocando - Gioco di Matematica

## Descrizione

Il progetto **Impara Giocando** è un semplice gioco matematico sviluppato in Java con **Spring Boot**. Gli utenti possono risolvere problemi di matematica (addizione, sottrazione, moltiplicazione), selezionando la risposta corretta da un insieme di opzioni. Il sistema genera un problema matematico casuale e offre quattro soluzioni possibili, una delle quali è corretta. Se la soluzione corretta è negativa, il sistema genera almeno due risposte negative distinte.

## Funzionalità

- Generazione di problemi matematici casuali (addizione, sottrazione, moltiplicazione).
- Generazione di quattro risposte casuali con l'assicurazione che:
    - Una è la soluzione corretta.
    - Le risposte sono tutte uniche.
    - Se la risposta corretta è negativa, ci saranno almeno due risposte negative distinte.
- La risposta corretta e le soluzioni sono presentate in ordine casuale.
- Verifica della risposta dell'utente con messaggio di risultato.

## Tecnologie Utilizzate

- **Java 21**
- **Spring Boot** (MVC)
- **Thymeleaf** (per il rendering del frontend)
- **Lombok** (per la generazione automatica di getter, setter e costruttori)
- **HTML/CSS** (per la parte di visualizzazione)

## Struttura del Progetto

### `MathGameController.java`

Il controller Spring che gestisce le richieste HTTP e coordina le interazioni tra il frontend e il servizio che genera i problemi matematici.

- **`home()`**: Gestisce la pagina iniziale del gioco, dove un problema matematico e le relative soluzioni vengono presentate all'utente.
- **`checkAnswer()`**: Verifica se la risposta fornita dall'utente è corretta o sbagliata.

### `MathGameService.java`

Contiene la logica di business per generare problemi matematici e verificare le risposte.

- **`generateProblem()`**: Genera un problema matematico casuale (addizione, sottrazione, moltiplicazione).
- **`generaSoluzioni()`**: Genera quattro soluzioni casuali, una delle quali è corretta.
- **`checkAnswer()`**: Verifica se la risposta dell'utente è corretta.
- **`calcolaRispostaCorretta()`**: Calcola la risposta corretta in base all'operazione e ai numeri forniti.

### `MathProblemDTO.java`

Un **DTO** (Data Transfer Object) che contiene i dati di un problema matematico, come i numeri e l'operazione.

### `SoluzioneDTO.java`

Un **DTO** che rappresenta le quattro possibili soluzioni a un problema matematico, con una delle quattro che è la risposta corretta.

### Template Thymeleaf

- **`mathgame.html`**: La pagina principale del gioco in cui viene presentato il problema matematico e le risposte.
- **`result.html`**: Mostra il risultato (corretto o sbagliato) della risposta dell'utente.

## Esecuzione del Progetto

### Prerequisiti

- Java 21 o superiore
- Maven per la gestione delle dipendenze
- Un browser web per visualizzare l'applicazione

### Come Eseguire il Progetto

1. **Clona il repository:**
2. **Apri il progetto su cmd:**
3. **Compila ed esegui:**

   ```bash
   git clone https://github.com/JavaSoftwareEntwickler/impara-giocando.git
   cd impara-giocando
   mvn clean install
   mvn spring-boot:run
   
5. **Apri il browser e vai alla seguente URL:**
   ```bash
   http://localhost:8080/game

### Come Stoppare l'applicazione

1. **Da cmd:**
   ```bash
   ctrl c

### Licenza

Questo progetto è sotto licenza MIT. Consulta il file LICENSE per ulteriori dettagli.