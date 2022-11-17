# Data-module
_The data-module stores data aswell as handle file write/read with a custom json-serializer_

## Serializers and de-serializers
Klassene under `core` som må kunne skrives og leses til har sine egne serializers and de-serializers klasser under `/data/src/main/java/data/`. Disse klassene parser data i json format til `/data/src/main/resources/` så de kan bli lagret i minnet og hentet tilbake igjen i etterkant.


## Kompilering

Prosjektet har configrurasjonen for å lage shippable prudukt med jpackage og jlink. For å kompilere programmet til en executable fil bruk følgende kommando:

`mvn clean compile javafx:jlink jpackage:jpackage`