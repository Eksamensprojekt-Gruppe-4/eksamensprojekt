# Projektkalkulationsværktøj

Dette er et projektkalkulationsværktøj som en webapplikation. Man kan på applikationen oprette projekter og ud fra underopgaver kalkulere  en estimeret tid for et større projekt. Efter et afsluttet projekt kan man tilføje den reele tid brugt på delopgaver og arkivere projektet, så det kan tilgås senere hen.

## Features
- Logge ind
- Lave et projekt
- Lave delopgaver og tildele tid
- Angive reele estimeret tid brug og arkivere projekt.

## Teknologier
- Java 21 & Spring Boot
- Thymeleaf
- JDBC
- MySql
- H2Database

## Installation
1. Klon projektet fra dette link https://github.com/Eksamensprojekt-Gruppe-4/eksamensprojekt.git.
2. Tilføj enviromental variables til din Idea ved brug af application-dev.properties placeholders.
3. Gå ind på projektet og kør applikationen.
4. Åben din webbrowser og tilgå https://localhost:8080


# Projektkalkulationsværktøj

Et webbaseret projektkalkulationsværktøj til estimering og opfølgning på tidsforbrug i projekter.
Applikationen gør det muligt at oprette projekter, opdele dem i delopgaver og sammenligne estimeret tid med faktisk forbrug.

---

## 🚀 Features

* Brugerlogin
* Oprettelse af projekter
* Oprettelse af delopgaver med tidsestimering
* Registrering af faktisk tidsforbrug
* Arkivering af afsluttede projekter

---

## 🛠️ Teknologier

* Java 21
* Spring Boot
* Thymeleaf
* JDBC
* MySQL
* H2 Database (til udvikling/test)

---

## ⚙️ Installation

1. Klon projektet:

```bash
git clone https://github.com/Eksamensprojekt-Gruppe-4/eksamensprojekt.git
cd eksamensprojekt
```

2. Konfigurer miljøvariabler
   Tilføj nødvendige environment variables i din IDE (fx IntelliJ) baseret på `application-dev.properties`.

3. Start applikationen:

```bash
mvn spring-boot:run
```

4. Åbn i browser:

```
http://localhost:8080
```

---

## ▶️ Brug

1. Opret en bruger / log ind
2. Opret et nyt projekt
3. Tilføj delopgaver med estimeret tid
4. Opdater med faktisk tidsforbrug når opgaver er færdige
5. Arkivér projektet

---

## 📁 Projektstruktur (eksempel)

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
```

---

## 🤝 Bidrag

Se `CONTRIBUTING.md` for retningslinjer.

---

## 📌 Roadmap (valgfri)

* [ ] Brugerroller (admin/user)
* [ ] Dashboard med statistik
* [ ] Export til CSV/PDF

---

## 📄 License

Dette projekt er lavet som eksamensprojekt og er ikke nødvendigvis tiltænkt produktion.
