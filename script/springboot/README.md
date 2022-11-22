# REST-API server med springboot

Denne modulen brukes som back-end'en i prosjektet som kjører en nettjener med API-endepunkter som gjør det mulig å kunne lagre data til og hente data fra disse API'ene, i motsetning til Delivery1 og Delivery2 da vi lagret data til en resource mappe lokalt.

Backenden systemet er laget med Spring Boot. `ApiService`-klassen initialiserer springboot, `SecurityConfig`-klassen konfigurerer sikkerhetslaget som kommer med springboot og ApiController oppretter endepunkter slik at klienten kan kommunisere med backend systemet. Alt av serialisering skjer ved hjelp av JackonFasterFXML, og på denne måten kan `core`-klasser brukes i requests.

Se [API.md](/docs/release3/API.md) for mer informasjon.

I tillegg benytter backenden seg av `data`-modulen til å lese/skrive data. I de fleste tilfeller ville man brukt en database, men valgte denne metoden siden oppgaven spurte eksplisitt om å lagre i json.
