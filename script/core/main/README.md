# core-module
I core/main ligger all kjernelogikken til Script.

## Struktur
Alle hovedklasser befinner seg under `src/main/java/core/main`. `User` er et objekt som kan inneholde flere `Board` objekter. Hvert `Board` objekt kan inneholde flere `BoardElement` objekter. `BoardElement` er en abstrakt klasse som både `Note` og `Checklist` arver fra. Et `Note` objekt har en tittel og innhold, som kan endres. Et `Checklist` objekt har en tittel, og kan inneholde en eller flere `ChecklistLines`. `CheklistLine` objekter har en `boolean isChecked` atributt, som bestemmer om en sjekkboks er sjekket eller ikke. I tillegg har `ChecklistLine` en `String line` attributt, som er teksten som står ved siden av sjekkboksen.

Tester til hovedklassene ligger under `src/main/test/java/core/main`. Testene tester logikkene i alle hovedklassene.