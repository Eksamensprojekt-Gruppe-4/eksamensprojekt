# Projektkalkulationsværktøj

Et webbaseret projektkalkulationsværktøj til estimering og opfølgning på tidsforbrug i projekter.
Applikationen gør det muligt at oprette projekter, opdele dem i delopgaver og sammenligne estimeret tid med faktisk forbrug.

## Features

* Brugerlogin
* Oprettelse af projekter
* Oprettelse af delopgaver med tidsestimering
* Registrering af faktisk tidsforbrug
* Arkivering af afsluttede projekter

## Teknologier

* Java 21
* Spring Boot
* Thymeleaf
* JDBC
* MySQL
* H2 Database (til udvikling/test)

## Installation

1. Klon projektet

```bash
git clone https://github.com/Eksamensprojekt-Gruppe-4/eksamensprojekt.git
cd eksamensprojekt
```

2. Konfigurer miljøvariabler
   Tilføj nødvendige environment variables i din IDE (fx IntelliJ) baseret på `application-dev.properties`.
   Kør SQL scripts fra sql mappen i din database der er tilknyttet.

4. Start applikationen

```bash
mvn spring-boot:run
```

4. Åbn i browser

```
http://localhost:8080
```


Dette projekt er lavet som eksamensprojekt og er ikke nødvendigvis tiltænkt produktion.
