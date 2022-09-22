[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2205/gr2205)

# IT1901 Prosjekt - Script

_Script lets you organize notes and reminders to assist in day to day activities_

Alt av dokumentasjon ligger under [docs](link) og er sortert etter utgivelser av prosjektet.

## Prosjekt
Prosjektet er bygd med maven og består av tre delmoduler: core, data og ui. Core-modulen inneholder hovedklassene og data-modulen inneholder både data og klasser for filbehandling. Ui-modulen inneholder kontroller samt fxml-filer og alt annet av ressurser som trengs til brukergrensesnittet.

## Roadmap
- [x] Modular structure
- [ ] User interface
    - [x] Design and fxml (UI/UX)
    - [ ] Controller
- [ ] Unit tests and CD/CI pipeline
- [ ] Search functionality
- [ ] Custom JSON serializer

## Running the FXUI Client
The javafx local client has only been configured to boot with maven from the `script/ui` folder, so to run the FXUI, run

```sh
cd script && mvn clean compile install && cd ui && mvn javafx:run
```

## License
Distributed under the MIT License.
