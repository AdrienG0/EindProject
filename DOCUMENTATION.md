# Technical Documentation – Uitleensysteem Applicatie

## 1. Doel van deze documentatie

Deze technische documentatie beschrijft de **architectuur**, **structuur** en **kerncomponenten**
van de Uitleensysteem Applicatie.

Ze is bedoeld voor **ontwikkelaars** die:
- de code willen begrijpen
- onderhoud willen uitvoeren
- functionaliteit willen uitbreiden

De focus ligt op:
- lagenarchitectuur
- package-indeling
- verantwoordelijkheden van kernklassen

Eenvoudige getters/setters en standaard JPA-mappings worden bewust niet besproken.

---

## 2. Architectuur Overzicht

De applicatie volgt een **klassieke Spring Boot lagenarchitectuur**:

Controller → Service → Repository → Database


### Verantwoordelijkheid per laag
- **Controller**  
  Verwerkt HTTP-verzoeken en stuurt views of responses terug.
- **Service**  
  Bevat businesslogica en transactionele operaties.
- **Repository**  
  Verzorgt database-interactie via Spring Data JPA.
- **Model**  
  Bevat JPA-entiteiten die de database representeren.
- **Config**  
  Bevat securityconfiguratie en initiële data.

Deze scheiding zorgt voor **duidelijke verantwoordelijkheden**, betere testbaarheid
en een onderhoudbare codebase.

---

## 3. Package Structuur

### `config`
Bevat configuratieklassen die de applicatie bij opstart correct instellen.

**Belangrijkste klassen**
- **`SecurityConfig`**  
  Configureert Spring Security, login/logout, CSRF-beveiliging en rolgebaseerde toegang.
- **`DataInitializer`**  
  Initialiseert standaardgebruikers, categorieën en producten bij opstart
  indien deze nog niet bestaan.

---

### `model`
Bevat alle **JPA-entiteiten** die overeenkomen met databanktabellen.

**Belangrijkste entiteiten**
- **`User`** – gebruikersaccounts en rollen
- **`Product`** – uitleenbaar materiaal
- **`Category`** – productcategorieën
- **`CartItem`** – items in een lopende reservatie
- **`Order`** – bevestigde reservaties (historiek)

Relaties tussen entiteiten zijn expliciet gedefinieerd via JPA-annotaties.

---

### `repository`
Bevat Spring Data JPA repository-interfaces.

Deze repositories voorzien standaard CRUD-functionaliteit zonder extra implementatie.

**Voorbeelden**
- `UserRepository`
- `ProductRepository`
- `OrderRepository`
- `CartItemRepository`

---

### `service`
Bevat alle **businesslogica** en transactionele verwerking.

#### `CartService`
Centrale service voor reservaties en stockbeheer.

**Verantwoordelijkheden**
- toevoegen en verwijderen van items
- aanpassen van aantallen
- berekenen van totaalprijs
- correcte stockverwerking bij elke actie

Alle stocklogica gebeurt **transactioneel** om inconsistenties te vermijden
(bijvoorbeeld bij gelijktijdige acties).

---

### `controller`
Controllers verzorgen de navigatie en gebruikersinteractie.

#### `AuthController`
- Login en registratie
- Validatie van gebruikersgegevens
- Wachtwoord hashing met BCrypt

#### `CatalogController`
- Overzicht van beschikbare materialen
- Filteren op categorie en zoekterm
- Sorteren en live stockweergave

#### `CartController`
- Beheer van reservaties (cart)
- Checkout flow
- Omzetten van reservatie naar order
- AJAX-endpoints voor toevoegen zonder pagina reload

#### `HomeController`
- Startpagina
- Basisnavigatie
- Reservatiegeschiedenis per gebruiker

---

## 4. Security Overzicht

- Spring Security met rolgebaseerde toegang (`USER` / `ADMIN`)
- Wachtwoorden gehasht met `BCryptPasswordEncoder`
- CSRF-bescherming op formulieren
- Beveiligde routes voor adminfunctionaliteit en H2-console
- Geen plain-text wachtwoorden in de code

Gevoelige configuratie (zoals default wachtwoorden) wordt geladen via
`application.properties` en is uitgesloten via `.gitignore`.

---

## 5. Database & Persistentie

- H2 **file-based** database
- Data blijft behouden bij herstart van de applicatie
- JPA + Hibernate voor ORM
- Relationele koppelingen tussen gebruikers, reservaties en orders

Deze setup is geschikt voor ontwikkel- en evaluatiedoeleinden.

---

## 6. Uitbreidbaarheid

De applicatie is ontworpen met uitbreidbaarheid in gedachten:

- eenvoudig toevoegen van nieuwe categorieën of producten
- uitbreiden van rollen en securityregels
- toevoegen van extra validaties of businessregels in services
- uitbreiden van reservatie- of orderfunctionaliteit

Door de duidelijke lagenstructuur blijven wijzigingen lokaal en overzichtelijk.

---

## 7. Broncode

De volledige broncode is beschikbaar via GitHub:  
https://github.com/AdrienG0/EindProject.git
