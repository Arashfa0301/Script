# Script

Script er en enkel app som lar en bruker opprette, redigere, sortere og slette notater og sjekklister. Videre dokumentasjon til de forskjellige utgivelsene finnes i [/docs](/docs).

Under er det et skjermbilde av appen som er befolket med noen enkle notater og sjekklister. 

![](/docs/release3/img/MainScreen.png)

## Struktur

- [core](/script/core/) inneholder kjernelogikken som handler om oppslagsbrettene, notatene og deres innhold.
- [ui](/script/ui/) inneholder kontrollere og FXML filene som kjører brukergrensesnittet.
- [data](/script/data/) lagrer og henter frem data.
- [springboot](/script/springboot/) brukes til å lage api-et.
- [report](/script/report/) brukes kun til JaCoCo-rapporter.


## Brukerhistorier
Vi har laget noen brukerhistorier som skal hjelpe oss med å kartlegge bruksområdene til appen, og gi oss en bedre idé om hvilke funksjonaliteter appen skal ha.
Brukerhistorie #1 til release 1 ligger [her](/docs/release1/userStories.md). Brukerhistorie #2 og #3 til release 2 ligger [her](/docs/release2/userStories.md). Brukerhistorie #4 og #5 til release 3 ligger [her](/docs/release%203/userStories3.md).


## Kjøre springboot
1. Gå inn i `script`-mappen. ([script](/script/))
2. Kjør `mvn clean install -DskipUiTests -DskipTests`
3. Bytt mappe til `springboot/server`. ([springboot/server](/script/springboot/server))
4. Kjør `mvn spring-boot:run`

## Kjøre JavaFX-brukergrensesnittet
1. Pass på at du har startet springboot
2. Gå inn i `script`-mappen. ([script](/script/))
3. Kjør `mvn clean install`
4. Bytt mappe til `ui`. ([ui](/script/ui/))
5. Kjør `mvn javafx:run`

## Bygge java-klient
For å bygge en portabel versjon av applikasjonen, følg instruksjonene i [ui](/script/ui/) sin readme.