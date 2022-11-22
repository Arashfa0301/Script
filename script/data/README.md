# Data-module
_Datamodulen lagrer data i tillegg til å håndtere filskriving/lesing med en tilpasset json-serializer_
Data-module blir brukt i spring-boot modulen for å lagre server data til, og lese fra fil

## Gson (Serializer and de-serializer)
Klassene under `core` som må kunne skrives og leses til blir serializet og de-serializet med Gson. Gson er et Java-bibliotek som blir brukt til å konvertere Java-Objekter til JSON-format.
