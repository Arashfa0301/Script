[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2205/gr2205)

# IT1901 Prosjekt - Script

_Script lets you add notes and reminders to keep your day organized_

Dokumentasjonen til prosjektet ligger i [docs](link) og er sortert inn undermapper etter utgivelser av appen.
Script består av en server i script-mappen, og en har der også en frontend i JavaFX.
En alternativ frontend (webklient) ligger i webapp-mappen.

## Java-klient

Java-programmet vårt lar brukeren opprette notater(scripts) og legge inn påminnelser for å hjelpe brukeren holde styr i hverdagen.
Dersom man logger inn vil man kunne redigere påminnelsene.

## Nett-klient

Nettsiden vår lar deg opprette og redigere scripts og påminnelser (på en tjener), og krever alltid at man logger inn (med en eksisterende bruker). Vi har tenkt at nettsiden skal være tilgjengelig fra hvor som helst i verden, og at det derfor gir mening å kreve autentisering for å kunne redigere sine egne scripts.
Når man er logget inn, lar nettsiden deg se, redigere og slette scripts og påminnelser.

## Running the FXUI Client

The javafx local client has only been configured to boot with maven from the `script/ui` folder, so to run the FXUI, run

```sh
cd script && mvn clean compile install && cd ui && mvn javafx:run
```
