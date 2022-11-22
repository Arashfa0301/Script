# Dokumentasjon på API

Script APIet gjør det mulig å kommunisere med applikasjonen gjennom REST endepunkter. Dette tillater smidig utvikling ved at funksjonalitet og frontend separeres, og på denne måten kan grensesnitt implementeres akkurat slik man ønsker.

Vi kunne for eksempel laget en React app som hadde koblet opp på en tjener med dette APIet, men vi har valgt å fokusere utelukkende på frontend i JavaFX.
<br/><br/>

## Innlogging

Alle endepunkter bortsett fra registrering krever at brukeren autentiserer seg. Dette gjøres ved hjelp `basic auth` hvor brukeren legger ved `username` (brukernavn) og `password` (passord) i headeren på HTTP requesten.

For å få brukernavn og password må brukeren først registrere seg på `/auth/register/` endepunktet.
<br/><br/>

## Endepunkter

Følgende endepunkter er tilgjengelig gjennom Script Apiet.
<br/><br/>

### Register ny bruker `POST` `/auth/register`

Lager en ny bruker instans på tjeneren

#### Forespørsel

```
{
    "username": "brukernavn",
    "password": "passord",
    "firstName": "fornavn",
    "lastName": "etternavn"
}
```

Ingen av feltene kan være `null` og username har krav om å være unikt.
</br></br>

### Hent bruker `GET` `/user/{username}`

Henter produktet med `id`en som er oppgitt i URLen.

_Merk: Bare riktig autentisert bruker kan hente brukerdata._

#### Returnerer

```
{
    "username":"brukernavn",
    "firstname":"fornavn",
    "lastname":"etternavn",
    "boards":[
        {
            "notes": [
                ... liste av "notater"
            ],
            "checklists": [
                ... liste av "checklists"
            ],
            "name": "navn på board",
            "boardDescription": "beskrivelse"
        },
        ... liste av "boards"
    ]
}
```

<br/>

### Oppdatetere board `PUT` `/board/{boardname}`

_Merk: Bare autentisert bruker som "eier" boardet kan redigere._

Oppdaterer produktet med `id`en som er oppgitt i URLen. Dersom produktet ikke allerede finnes blir det lagt til.

#### Forespørsel

```
{
    "notes": [
        ... liste av "notater"
    ],
    "checklists": [
        ... liste av "checklists"
    ],
    "name": "navn på board",
    "boardDescription": "beskrivelse"
}
```

Alle feltene i boardet kan endres bortsett "name"
</br></br>

### Lag et nytt board `GET` `/boards/create/{boardname}`

_Merk: Bruker må være autentisert_

Oppretter board med `boardname` oppgitt i URLen.

_Returnerer 201 Created dersom dette var vellykket._

<br/>

### Bytt navn på board `GET` `/boards/rename/{boardname}/{newBoardName}`

_Merk: Bruker må være autentisert_

Bytter navn på et brett med `boardname` til `newBoardName`. Dette endepunktet trengs ettersom boardname blir produkt som lookup id.

_Returnerer 200 OK dersom dette var vellykket._
<br/><br/>

### Slett board `DELETE` `/boards/remove/{boardname}`

_Merk: Bruker må være autentisert_

Sletter board med navn `boardname`.

_Returnerer 200 OK dersom dette var vellykket._
<br/><br/>

### Slett bruker `DELETE` `/user/{username}/delete`

_Merk: Bruker må være autentisert_

Sletter bruker med navn `username`.

_Returnerer 200 OK dersom dette var vellykket._
<br/>
