# EindProject – Webshop Applicatie

Dit project is een **webshop** ontwikkeld in het kader van het vak *Enterprise Application*.  
De applicatie maakt gebruik van **Spring Boot**, **Thymeleaf** en **Maven** en bevat een volledige flow van registratie tot checkout.

---

## 📌 Inhoudstafel
1. [Beschrijving](#beschrijving)
2. [Technologieën](#technologieën)
3. [Hoe te runnen](#hoe-te-runnen)
4. [Functionaliteiten](#functionaliteiten)
5. [Gebruik van AI-tools](#Gebruik van AI-tools)
6. [Bronnen](#bronnen)

---

## Beschrijving

Deze applicatie is een eenvoudige webshop waarin gebruikers producten kunnen bekijken, een winkelmandje kunnen vullen en een bestelling kunnen plaatsen.  
Het project bevat gebruikersauthenticatie, een beveiligde checkout flow en een beheerdersrol.

---

## Technologieën

| Technologie | Gebruikt voor |
|------------|----------------|
| **Java 17** | Backend logica |
| **Spring Boot 3** | Main framework |
| **Spring Security** | Login, registratie en beveiliging |
| **Thymeleaf** | HTML rendering |
| **Maven** | Build & dependencies |
| **H2 Database** | File-based database (ontwikkelomgeving, persistent) |
| **Bootstrap 5** | Basisstyling |

---

## Hoe te runnen

### ✔ Vereisten
- **Java JDK 17**
- IDE zoals IntelliJ, of enkel terminal
- Geen externe database nodig (H2 wordt automatisch geladen)
- De H2-database wordt file-based gebruikt zodat gebruikers, winkelmandjes en orders
  bewaard blijven bij het herstarten van de applicatie.

---

### ▶ Starten via terminal

1. Navigeer naar de projectmap (waar `pom.xml` staat).
2. Voer één van deze commando’s uit:

./mvnw spring-boot:run      # macOS/Linux
.\mvnw spring-boot:run      # Windows

## 4. Functionaliteiten

### 👤 Authenticatie
- Registreren van nieuwe gebruikers.
- Inloggen met e-mail en wachtwoord.
- Spring Security configuratie.
- Rolgebaseerde restricties (`USER`, `ADMIN`).

### Standaardgebruikers
Bij het opstarten worden standaardaccounts aangemaakt indien ze nog niet bestaan:

- Admin account: `admin@ehb.be`
- Gebruikersrol: `ADMIN`

- default user account: `adrien@student.ehb.be`
- Gebruikersrol: `USER`

Deze accounts worden enkel aangemaakt indien ze nog niet in de database aanwezig zijn.


### 🛒 Producten & winkelmandje
- Productoverzichtpagina.
- Productdetailpagina.
- Producten toevoegen aan winkelmandje.
- Items verwijderen of aantallen aanpassen.
- Automatische totaalprijsberekening.

### 💳 Checkout & Orders
- Checkoutpagina met orderoverzicht.
- Order wordt gekoppeld aan de ingelogde gebruiker.
- Opslag van orders via JPA in de H2-database.
- Bevestigingspagina na succesvolle bestelling.

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








