# Dokumentasjon for innlevering 2

Innlevering 2 implementerer et system for å skrive og lagre notater (notes) i ulike brett (boards), som hører til ulike brukere (users).

Data som kan lagres i programmet er:

- Brukere
    - Brett
        - Navn
        - Beskrivelse
        - Notater
            - Tittel
            - Tekst
        

Brukergrensesnittet fra lansering 1 er for det meste videreført. Den største forskjellen er at det i denne lanseringen er lagt til en User-klasse, og et eget brukergrensesnitt for å akkommodere dette. Her kan man logge seg inn på en bruker ved å skrive inn et brukernavn, og så bli overført til hoved-brukergrensesnittet. Dvs. at dersom man skriver inn brukernavnet på en bruker man tidligere har lagt til brett og notater på, så vil man kunne finne igjen disse. Det er også lagt inn funksjon for å logge ut igjen, og komme tilbake til innloggings-brukergrensesnittet. I tillegg er det lagt til automatisk lagring, og lagringsknappen som fantes i lansering 1 er fjernet. Det er også lagt til håndtering av endring på skjermstørrelse. Slik vil antall rader og kolonner med notater i et brett justere seg etter størrelsen på vinudet.

I lansering 1 ble brett rett inn i en JSON-fil, og notater ble lagret under brettet de tilhørte. I denne lanseringen blir derimot brettene lagret under den brukeren det tilhører. Notater blir fortsatt lagret under brettet de tilhører, slik som i lansering 1. Bruker med brett og notater blir automatisk lagret til fil hver gang man endrer på noe ved dem. Dvs. at om man lager en ny bruker, et nytt brett eller et nytt notat, samt hver gang man endrer på tittel eller beskrivelse til et brett eller tittel eller tekst til et notat, blir brukeren med tilhørende brett og notater lagret til fil. Slik oppnår vi en pålitelig applikasjon, hvor en bruker kan stole på at alle notater de tar blir lagret slik de skal.

## Brukerhistorier

[Brukerhistorie 1](/docs/release1/userStories1.md)
er fortsatt relevant, også for denne lanseringen. Alle funksjonaliteter nevnt her er nemlig fortsatt til stede. [Brukerhistorie 2](/docs/release2/userStories2.md) er en utvidelse av Brukerhistorie 1 fra første lansering, og er derfor også relevant for denne lanseringen.

[Brukerhistorie 3](/docs/release2/userStories2.md) er ikke veldig relevant for denne lanseringen. Her er nemlig en viktig funksjonalitet å kunne lage en liste med elementer, og kunne "krysse ut" elementene. Dette kan implementeres i en senere lansering.

## Prosessen bak versjon 2

I [makingRelease2.md](/docs/release2/makingRelease2.md) står det om prosessen underveis i utviklingen av versjon 2. Her nevner vi litt om hva som har fungert bra, og hva som kunne fungert bedre, så langt.

## Valg av lagringsmetode

Vi lagrer all data i rotmappa til datamaskinen. På denne måten er lagret data uavhengig av repoet. Ved senere versjoner skal det brukes en database for lagring, og da vil heller ikke data lagres i repoet. Derfor valgte vi å gjøre det på denne måten i denne versjonen.

## Designskisse

Designskissen under, altså designskissen fra innlevering 1, er fortsatt stort sett representativ for denne lanseringen. Den mangler innloggingsvinduet, men dette er så simpelt at vi ikke så på det som nødvendig å utvikle en ny designskisse for kun det. 
![Designskisse innlevering 1](/docs/release1/img/design.png)