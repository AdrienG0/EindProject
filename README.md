# EindProject – Webshop Applicatie

Dit project is een **webshop** ontwikkeld in het kader van het vak *Enterprise Application*.  
De applicatie maakt gebruik van **Spring Boot**, **Thymeleaf** en **Maven** en bevat een volledige flow van registratie tot checkout.

---

## 📌 Inhoudstafel
1. [Beschrijving](#beschrijving)
2. [Technologieën](#technologieën)
3. [Hoe te runnen](#hoe-te-runnen)
4. [Functionaliteiten](#functionaliteiten)
5. [Projectstructuur](#projectstructuur)
6. [Accounts & rollen](#accounts--rollen)
7. [Bronnen](#bronnen)

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
| **H2 Database** | In-memory database (ontwikkelomgeving) |
| **Bootstrap 5** | Basisstyling |

---

## Hoe te runnen

### ✔ Vereisten
- **Java JDK 17**
- IDE zoals IntelliJ, of enkel terminal
- Geen externe database nodig (H2 wordt automatisch geladen)

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

---
## 5. Bronnen

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

### 🤖 AI-conversaties
- ChatGPT voor:
    - uitleg over controller-service-repository structuur,
    - hulp bij debuggen,
    - verbetering van pagina-styling,
    - securityconfiguratie,
    - Maken van de README.md







