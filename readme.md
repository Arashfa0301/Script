[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2205/gr2205)

# IT1901 Prosjekt - Script

_Script lar deg organisere notater og sjekklister for å hjelpe deg med ulike aktiviteter_

Alt av dokumentasjon ligger under [docs](/docs) og er sortert etter utgivelser av prosjektet.

## Struktur
Mappen `script` utgjør kodingsprosjektet. Prosjektet er bygd med maven og består av fire delmoduler: `core`, `data`, `ui` og `springboot`. Core-modulen inneholder hovedklassene og data-modulen inneholder klassen DataHandler for filbehandling. Ui-modulen inneholder kontroller samt fxml-filer og alt annet av ressurser som trengs til brukergrensesnittet. Springboot-modulen inneholder alt som brukes til å lage api-et.

## Script-applikasjonen
Java-applikasjonen vår lar brukere opprette ulike brett hvor de kan lagre notater og sjekklister. Alt lagres automatisk, noe som leder til en god brukeropplevelse. 

## Roadmap
- [x] Modular structure
- [x] User interface
    - [x] Design and fxml (UI/UX)
    - [x] Controller
- [x] Unit tests and CD/CI pipeline
- [ ] Search functionality
- [x] List functionality with checkboxes in notes
- [x] Custom JSON serializer