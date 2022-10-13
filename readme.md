[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2205/gr2205)

# IT1901 Prosjekt - Script

_Script lar deg organisere notater og påminnelser for å hjelpe deg med ulike aktiviteter_

Alt av dokumentasjon ligger under [docs](/docs) og er sortert etter utgivelser av prosjektet.

## Struktur
Mappen `script` utgjør kodingsprosjektet. Prosjektet er bygd med maven og består av tre delmoduler: `core`, `data` og `ui`. Core-modulen inneholder hovedklassene og data-modulen inneholder både data og klasser for filbehandling. Ui-modulen inneholder kontroller samt fxml-filer og alt annet av ressurser som trengs til brukergrensesnittet.

## Roadmap
- [x] Modular structure
- [x] User interface
    - [x] Design and fxml (UI/UX)
    - [x] Controller
- [x] Unit tests and CD/CI pipeline
- [ ] Search functionality
- [ ] List functionality with checkboxes in notes
- [x] Custom JSON serializer

## Kjøring av FXUI-klienten

Den lokale javafx-klienten er bare konfigurert til å starte opp med maven fra mappen `script/ui`, så for å kjøre FXUI, kjør

```sh
cd script && mvn clean install && cd ui && mvn javafx:run
```