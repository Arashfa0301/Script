# Script

Script er en enkel app som lar en bruker opprette, redigere, sortere og slette notater. Videre dokumentasjon til de forskjellige utgivelsene finnes i [/docs](/docs).

Under er det et skjermbilde av appen som er befolket med noen enkle notater. 

![](/docs/release1/img/JavaFXinterface.png)

## Struktur

[core](/script/core/) inneholder kjernelogikken som handler om oppslagsbretten, notatene og deres innhold.
[ui](/script/ui/) inneholder controllere og FXML filene som kjører brukergrensesnittet
[data](/script/data/) lagrer og henter frem data

[report](/script/report/) brukes kun til JaCoCo-rapporter


## Brukerhistorier
Vi har laget noen brukerhistorier som skal hjelpe oss med å kartlegge bruksområdene til appen, og gi oss en bedre idé om hvilke funksjonaliteter appen skal ha.
Brukerhistorie #1 til release 1 ligger [her](/docs/release1/userStories.md). Brukerhistorie #2 og #3 til release 2 ligger [her](/docs/release2/userStories.md).


## Kjøre JavaFX-brukergrensesnittet
- Gå inn i `script` mappen.
- Kjør `mvn clean install`
- Gå inn i `ui`
-Kjør `mvn javafx:run`



