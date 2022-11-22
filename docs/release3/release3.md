<# Dokumentasjon for innlevering 3

Innlevering 3 utvider notatapplikasjonen fra innlevering 2. Vi har valgt å fokusere på å videreutvikle appen vår, framfor å utvikle en webapplikasjon.

I notatapplikasjonen kan en bruker lage notater og sjekklister, fordelt på ulike brett (boards). Dataten som vil lagres og være tilgjengelig for brukeren er:

- Brett
    - Navn
    - Beskrivelse
    - Notater
        - Tittel
        - Innhold
    - Sjekklister
        - Tittel
        - Sjekklistelinjer
            - Innhold
            - Sjekket (boolsk verdi som sier om en linje i en sjekkliste er "sjekket")

Applikasjonen sitt brukergrensesnitt er en videreføring fra innlevering 2. I innlevering 2 krevde applikasjonen kun et brukernavn i innloggingsprosessen, og brukerne hadde derfor ingen sikring av sine personlige notater. I denne innleveringen har vi implementert et eget registreringsvindu, hvor en kan lage sin egen bruker med fornavn, etternavn, brukernavn og passord. Etter å ha registrert en bruker, kan man senere logge seg inn på denne brukeren for å hente inn all data lagret på brukeren.

Etter å ha logget inn, vil brukeren se et hovedvindu (se bildeeksempel under) med brukerens brett på venstre side, samt et skrivefelt og en knapp som kan brukes for å lage nye brett. Når brukeren trykker på et av brettene på venstre side, vil brettet "åpnes" på høyre side av skjermen, hvor tittel, beskrivelse og alle notater og sjekklister i brettet vil vises. Her finnes det også knapper for å lage nye notater og sjekklister. På venstresiden av skjermen vil man også finne "log out"-knappen, som vil logge ut brukeren, og ta den tilbake til innloggingskjermen om brukeren tykker på den.

Brukeren har som sagt mulighet til å lage nye brett. I tillegg har brukeren mulighet til å endre på brettets navn, samt å gi brettet en beskrivelse. Brukeren har også mulighet til å lage nye notater ved å trykke på "New note"-knappen, samt nye sjekklister ved å trykke på "New checklist". Deretter vil brukeren få opp notater og sjekklister i et grid-system. Dette vil tilpasse seg etter hvor mye man skriver i notatene og hvor mange linjer man legger til i sjekklisten, og det vil tilpasse seg etter størrelsen på vinduet. I hver rad med notater og sjekklister vil det altså være et ulikt antall elementer basert på hvor stort vinduet er. Brukeren kan deretter velge å enten skrive tekst i notatene, eller skrive tekstlinjer og "sjekke" disse i sjekklistene.

Lagring skjer automatisk i applikasjonen. Det vil si at brukeren aldri trenger å aktivt trykke på "save" eller lignende for å lagre. For å unngå å sende for mange forespørsler til api-et, blir det kun lagret ved noen få spesifikke operasjoner. Det blir lagret dersom brukeren lager et nytt brett, dersom brukeren trykker på et brett, dersom brukeren trykker på "log out"-knappen og dersom brukeren velger å makt-lukke applikasjonen. Dette kan gjøres ved å f.eks. trykke på det røde krysset oppe i høyre hjørnet eller trykke på Alt + F4 på Windows, eller ved å trykke på den røde knappen oppe i venstre hjørne eller trykke på Option + Command + Esc på macOS.

Brukeren har som sagt mulighet til å logge seg av og på, samt å registrere nye brukere. Følgende data lagres om brukeren:

- Fornavn
- Etternavn
- Brukernavn
- Passord

## Brukerhistorier

Alle brukerhistoriene er relevante for denne innleveringen. Spesielt brukerhistorie [3](/docs/release3/userStoriesRelease3.md), [4 og 5](/docs/release3/userStories3.md) er veldig relevante for de nye funksjonene vi har lagt til i denne innleveringen, siden de handler om brukere som bruker sjekklister. Brukerhistorie [1](/docs/release1/userStories1.md) og [2](/docs/release2/userStories2.md) er fortsatt relevante, ettersom de fokuserer på brukere med behov for en applikasjon hvor man kan ta notater, og denne funksjonaliteten fortsatt er tilstede i applikasjonen.

## Prosessen bak versjon 3

I [creatingRelease3.md](/docs/release3/makingRelease3.md) finnes informasjon om valgene vi har tatt i utviklingen av applikasjonen. I tillegg er det skrevet om hva som har fungert bra og dårlig når det kommer til tekniske verktøy, språk og samarbeid.

## Bildeeksempler:

Her er bildeeksempler fra innloggingsskjermen, registreringsskjermen og hovedskjermen til applikasjonen:

![LoginScreen](/docs/release3/img/LoginScreen.png)
![RegisterScreen](/docs/release3/img/RegisterScreen.png)
![MainScreen](/docs/release3/img/MainScreen.png)


## Designskisse:

Applikasjonen er fortsatt i stor grad inspirert av designskissen fra innlevering 1. I denne innleveringen har vi altså lagt til sjekklister, som man kan se i denne designskissen. Vi har ikke lagt til funksjonalitet for å endre farger på notater eller for å lage tegninger i notatene, men ellers er denne skissen fortsatt relevant.

![Designskisse](/docs/release1/img/design.png)