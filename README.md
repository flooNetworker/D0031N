# Registrera studieresultat (D0031N)

En webbapplikation för att registrera studieresultat i Ladok. Byggd med Spring Boot och fyra separata databaser.

## Vad gör applikationen?

Detta är ett system där lärare kan:
1. Välja en kurs och uppgift
2. Se vilka studenter som är registrerade (från Canvas)
3. Registrera betyg i Ladok med personnummer och datum

Systemet kopplar ihop information från olika källor - precis som det fungerar i verkligheten. Vi har använt oss av Github Copilot som hjälp med lärandet av uppgiften.

## Systemets delar

Applikationen använder fyra olika databaser som simulerar de olika systemen:

*   **Canvas** (`canvasdb`): Kursinformation och vilka studenter som är registrerade på varje kurs/modul
*   **Epok** (`epokdb`): Officiella kurskoder och moduler (t.ex. "004 - inlämningsuppgifter")
*   **StudentITS** (`studentdb`): Studentuppgifter (namn, användarnamn, personnummer)
*   **Ladok** (`ladokdb`): Där betyg registreras officiellt med datum och personnummer

Alla databaser är H2-databaser som körs i minnet - det betyder att all data försvinner när du stänger av programmet

## Inspektera databaserna

Du kan se vad som finns i databaserna via H2-konsolen:

1.  Starta applikationen
2.  Gå till [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
3.  Använd användarnamn `sa` (inget lösenord)
4.  Välj vilken databas du vill se genom att ändra **JDBC URL**:

    *   Canvas: `jdbc:h2:mem:canvasdb`
    *   Epok: `jdbc:h2:mem:epokdb`
    *   StudentITS: `jdbc:h2:mem:studentdb`
    *   Ladok: `jdbc:h2:mem:ladokdb`

5.  Klicka "Connect" och kör SQL!

## Testdata

När applikationen startar fylls databaserna automatiskt med testdata. Det finns färdiga kurser, studenter och betyg att arbeta med direkt. Detta sker i klasser som heter `*DataInitializer.java`.

## Kom igång

### Förutsättningar

*   Java 17 eller nyare
*   Maven 3.6+

### Starta projektet

1.  **Bygg projektet**
    ````bash
    mvn clean install
    ````

2.  **Starta servern**
    ````bash
    mvn spring-boot:run
    ````
    Vänta tills du ser meddelandet `Started Application in X seconds`.

3.  **Öppna webbgränssnittet**
    Gå till [http://localhost:8080](http://localhost:8080) i din webbläsare

## CANVAS API INTEGRATION

Canvas API-token läggs i application.properties innan du startar applikationen.

Du kan även välja vilken kalenderhändelse som ska uppdateras i Canvas genom att ändra värdet på `canvas.calendar.context` i application.properties


