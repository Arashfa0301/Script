# UI-module

Ui-modulen inneholder logikken som styrer brukergrensesnittet til appen, i tillegg til kommunikasjonen med REST-API.

## Struktur
I `src/main/java/ui` ligger kontrollerne og andre klasser som er med på å styre brukergrensesnittet til appen. `ScriptController` er den som styrer hoved-brukergrensesnittet til appen. Denne benytter seg av alle grunnklassene i `core`-modulen. Den tar utgangspunkt i en `User` som man logger inn på. `ScriptController` bruker også `data`-modulen til å lagre og hente data, og vise det i brukergrensesnittet.

`LoginController` og `RegisterController` styrer innloggingen og opprettingen av brukere. De benytter seg kun av `User` klassen i `core` modulen i tillegg til `data` modulen for å hente innloggingsinformasjon eller opprette en ny bruker.

`BoardElementController` er en selvlagd javafx-komponent, som er inspirert av virkemåten til [React](https://reactjs.org/). Den sikrer bedre kontroll på elementene i et `Board` objekt, og er med på lime sammen komponentene i brukergrensesnittet og klassene i `core` modulen.

`Cross` og `Globals` klassene brukes kun til konstanter og egne UI-elementer. `RemoteModelAccess` brukes til å kommunisere med REST-APIet. `WindowManager` brukes til å håndtere bytte mellom fxml-filene.

Alt av ressurser finnes under `src/resources/` og inkluderer `images` for bilder og `ui` for fxml.

Tester til kontrollerene ligger under `src/main/test/java/ui` og tester funksjonaliteten til elementene i brukergrensesnittet.

## Kompilering
For å kompilere programmet til en kjørbar fil gjør følgende:

For MacOS: Kjør `mvn clean compile javafx:jlink jpackage:jpackage` i `script/ui`. Deretter dra filikonet over i application mappen, i popup-vinduet som vises.

For Windows: Kjør `mvn clean compile javafx:jlink jpackage:jpackage` i `script/ui`. Gå inn i `ui/target/dist`. Her ligger det en .exe-fil. Kjør denne, og følg installasjons-prosessen.