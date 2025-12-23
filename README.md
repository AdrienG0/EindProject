# EindProject – Uitleensysteem Applicatie

Dit project is een **uitleensysteem** ontwikkeld in het kader van het vak *Enterprise Application*.  
De applicatie is gebouwd met **Spring Boot**, **Thymeleaf** en **Maven** en bevat een volledige flow van
authenticatie tot reservatie en checkout, met **correct stockbeheer** en een **persistente database**.

---

## 📌 Inhoudstafel
1. Beschrijving
2. Technologieën
3. Hoe te runnen
4. Functionaliteiten
5. Gebruik van AI-tools
6. Bronnen

---

## Beschrijving

Deze applicatie is een **uitleensysteem voor materiaal** (zoals lampen, audio, podiumelementen, kabels, …).
Gebruikers kunnen materialen bekijken, toevoegen aan een reservatieoverzicht en hun reservatie bevestigen.

Het systeem houdt **realtime stock** bij:
- stock verlaagt bij toevoegen aan reservatie
- stock wordt teruggezet bij verwijderen of aanpassen
- stock blijft definitief aangepast na checkout

De applicatie maakt gebruik van een **H2 file-based database**, waardoor alle data
(gebruikers, producten, stock, reservaties) behouden blijft bij het herstarten van de applicatie.

---

## Technologieën

| Technologie | Gebruikt voor |
|------------|--------------|
| Java 17 | Backend logica |
| Spring Boot 3 | Main framework |
| Spring Security | Login, beveiliging en rollen |
| Spring Data JPA | Database-interactie |
| Thymeleaf | Server-side HTML rendering |
| Maven | Build & dependency management |
| H2 Database (file-based) | Persistente ontwikkel-database |
| Bootstrap 5 | Styling en responsive UI |  

---

## Hoe te runnen

### Vereisten
- Java JDK 17
- IDE zoals IntelliJ of terminal
- Geen externe database nodig
- H2 wordt file-based gebruikt zodat data behouden blijft

---

### ▶ Starten via terminal

1. Navigeer naar de projectmap (waar `pom.xml` staat).
2. Voer één van deze commando’s uit:

./mvnw spring-boot:run      # macOS/Linux
.\mvnw spring-boot:run      # Windows

Open daarna de applicatie in de browser: http://localhost:8080

### H2 Console (login als admin)
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:file:./data/eindprojectdb
- Username: sa
- Password: (leeg)

## 4. Functionaliteiten

### 👤 Authenticatie & rollen 
- Inloggen met e-mail en wachtwoord.
- Spring Security configuratie.
- Rolgebaseerde toegang: USER en ADMIN.
- Wachtwoorden worden gehasht met BCryptPasswordEncoder.
- Geen plain-text wachtwoorden in de code.

### Standaardgebruikers
Bij het opstarten worden standaardaccounts aangemaakt indien ze nog niet bestaan:

- Admin account: `admin@ehb.be`
- Gebruikersrol: `ADMIN`

- default user account: `adrien@student.ehb.be`
- Gebruikersrol: `USER`

Deze accounts worden aangemaakt via DataInitializer.

### Profielpagina & accountbeheer

Elke ingelogde gebruiker heeft toegang tot een persoonlijke profielpagina.
Mogelijkheden:
- Bekijken van accountgegevens (naam, e-mail, rol).
- Aanpassen van naam.
- Aanpassen van e-mailadres (met validatie).
- Wijzigen van wachtwoord met bevestiging.

Beveiliging en gedrag:
- E-mailadressen worden gevalideerd op EHB-formaat (@ehb.be).
- Bij wijziging van het e-mailadres wordt de gebruiker automatisch uitgelogd.
- De sessie en security context worden correct geïnvalideerd.
- De gebruiker wordt doorgestuurd naar de loginpagina met een duidelijke bevestigingsmelding.
- Wachtwoorden blijven altijd gehasht opgeslagen.


### 🛒 Catalogus
- Overzicht van alle materialen.
- Filteren op categorie en zoekterm.
- Sorteren op naam en prijs.
- Live stockweergave.
- Producten met stock 0 worden als Uitverkocht weergegeven.

### 🛒 Reservaties (Cart)
- Materialen toevoegen aan reservatie
- Aantal aanpassen
- Items verwijderen
- Automatische prijsberekening: prijs_per_dag × aantal_dagen × aantal

Elke gebruiker heeft een eigen reservatieoverzicht, gekoppeld aan zijn account.

### 🛒 Stockbeheer
- Stock wordt onmiddellijk aangepast bij elke actie.
- Gedrag:
  - Toevoegen → stock verlaagt.
  - Aantal verhogen → extra stockcontrole.
  - Aantal verlagen → stock wordt teruggezet.
  - Item verwijderen → stock volledig teruggezet.
- Alle stocklogica gebeurt binnen transactionele services.


### 💳 Checkout & Orders
De applicatie gebruikt Spring Security om gebruikerssessies te beheren.
Elke ingelogde gebruiker heeft een eigen winkelmandje dat gekoppeld is
aan zijn account in de database.

Tijdens het checkoutproces wordt de inhoud van het winkelmandje opgehaald
op basis van de actieve gebruiker en verwerkt tot een order.
Na bevestiging wordt de bestelling opgeslagen en wordt het winkelmandje leeggemaakt.

### 🔐 Security & sessies
- Spring Security login, logout en bescherming van routes.
- Sessies voor winkelmandje.
- Rolgebaseerde toegang tot adminpagina’s.
- Wachtwoorden worden gehashed met `BCryptPasswordEncoder`.
- Wachtwoorden worden nooit in plain text opgeslagen.
- Registratie bevat server-side validatie en wachtwoordbevestiging.


## 5. Gebruik van AI-tools
Tijdens de ontwikkeling van dit project werd ChatGPT gebruikt als ondersteunende tool.
De AI werd ingezet voor:

- Het verduidelijken van Spring Boot-concepten (MVC-structuur, repositories, services) : https://chatgpt.com/share/693c177c-1460-8012-9b54-ce5fabaf5846 
- Het opstellen van basisstructuren voor controllers en entity-relaties : https://chatgpt.com/share/693c1888-86b0-8012-9147-e1e9c87decc4 
- Het helpen begrijpen van foutmeldingen en stacktraces : https://chatgpt.com/share/693c18e8-0578-8012-a177-e2ee33d79b76 
- Het herstructureren en vereenvoudigen van bestaande code : https://chatgpt.com/share/693c18e8-0578-8012-a177-e2ee33d79b76

### Voorbeeld van gebruikte prompts
- “Hoe structureer ik een Spring Boot e-commerce applicatie met MVC?”
- “Hoe werkt Spring Security met rollen USER en ADMIN?”
- “Waarom krijg ik deze Hibernate error en hoe los ik die op?”
- “Hoe implementeer ik een winkelmandje met een ManyToOne-relatie?”
- “Hoe kan ik mijn login- en registerpagina visueel verbeteren?”

De gegenereerde code werd **altijd nagekeken, aangepast en geïntegreerd** in het project.
Alle finale beslissingen, logica en integratie zijn door mij uitgevoerd.

---
## 6. Bronnen

### 📘 Documentatie
- Spring Boot officiële documentatie
- Spring Security referentiehandleiding
- Thymeleaf User Guide
- Maven documentatie

### 🎓 Lesmateriaal
- PPT’s, oefeningen en demo’s van het vak *Enterprise Application*

### 🌍 Tutorials & artikels
- Spring Boot + Thymeleaf webshop voorbeelden
- Artikels over beveiliging en MVC-lagen
- Blogs over Spring-architectuur

### 📂 Broncode  
De volledige broncode van dit project is beschikbaar via GitHub:  
https://github.com/AdrienG0/EindProject.git








