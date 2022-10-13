# Å lage release 2

Her skal vi kort forklare hvordan vi har arbeidet for å lage versjon 2 av appen vår. Vi skal også reflektere over hva som har fungert bra, og hva vi bør forandre før vi begynner på versjon 3.

## Hva har fungert så langt?

### Ukentlige møter
Hver uke har vi minst ett møte hvor vi ser på hvor langt vi har kommet og hva som gjenstår i arbeidsprosessen. Dette gjør det lett å kartlegge hva vi burde prioritere, og gjør det lett å delegere arbeidsområder til medlemmene av gruppa.

### Programmere sammen
Som oftest når vi jobber sitter vi sammen i en gruppe og jobber med våre arbeidsområder. Dersom noen har noen spørsmål er det veldig lett å få kontakt med en annen i gruppa når vi alle sitter i samme rom. I tillegg er det lettere å koordinere merge-requests, og løse eventuelle merge-conflicts.

### Bruk av JaCoCo, Checkstyle og Spotbugs
JaCoCo har gjort det veldig enkelt å se hvor stor del av koden som er blitt testet og hvilke deler av koden som mangler testing. Checkstyle gjør koden vår mer konsekvent og gjør det lettere for en tredjepart å lese koden.


## Hva har ikke fungert?

### Lage brukerhistorier etter å ha kodet
I både release 1 og release 2 har vi laget brukerhistoriene etter vi har blitt ferdige med versjonen av appen. Dette har vi innsett at ikke går så bra, for det blir mer som at vi former brukerhistoriene etter appen, istedenfor å forme appen etter brukerhistoriene. Brukerhistoriene er ment som et verktøy for en utvikler til å lettere kartlegge nødvendige funksjonaliteter, og det er ikke slik vi har brukt dem til nå. For release 3 skal vi da begynne med å lage brukerhistoriene, for å lettere vite hvilke funksjonaliteter vi burde jobbe med.

### Store issues
Gjennom utviklingsprosessen av release 1 og 2 har vi lagt merke til vi starter med noen store issues, og etterhvert som vi programmerer oppretter vi mindre issues som faller inn under en stor issue vi har fra før. Dette blir rotete for vi ender opp med flere issues som gjør samme ting. Derfor burde vi prøve å holde oss til små issues, som ikke endrer på for mye av gangen.



