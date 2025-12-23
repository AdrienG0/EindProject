# Technical Documentation – Uitleensysteem Applicatie

## 1. Doel van deze documentatie

Deze documentatie beschrijft de **structuur**, **architectuur** en **belangrijkste klassen**
van het Uitleensysteem.  
Ze is bedoeld voor **ontwikkelaars** die de code willen begrijpen, onderhouden of uitbreiden.

De focus ligt op:
- architectuur en lagen
- package-indeling
- kernklassen en hun verantwoordelijkheid

Getters, setters en eenvoudige mappings worden bewust niet besproken.

---

## 2. Architectuur Overzicht

De applicatie volgt de **klassieke Spring Boot lagenarchitectuur**:

Controller → Service → Repository → Database 


Elke laag heeft één duidelijke verantwoordelijkheid:
- **Controller**: verwerken van HTTP-verzoeken
- **Service**: businesslogica en transacties
- **Repository**: database-interactie (JPA)
- **Model**: JPA-entiteiten
- **Config**: security en initialisatie

Deze scheiding zorgt voor onderhoudbare en uitbreidbare code.

---

## 3. Package Structuur

### `config`
Bevat configuratieklassen.

**Belangrijkste klassen**
- `SecurityConfig`  
  Configureert Spring Security, login, logout en roltoegang.
- `DataInitializer`  
  Initialiseert standaardgebruikers, categorieën en producten bij opstart.

---

### `model`
Bevat alle **JPA-entiteiten** die overeenkomen met databanktabellen.

**Belangrijkste entiteiten**
- `User` – gebruikers en rollen
- `Product` – uitleenbaar materiaal
- `Category` – productcategorieën
- `CartItem` – items in een reservatie
- `Order` – bevestigde reservaties

---

### `repository`
Spring Data JPA repositories.

Deze interfaces leveren standaard CRUD-functionaliteit zonder extra code.

Voorbeelden:
- `UserRepository`
- `ProductRepository`
- `OrderRepository`
- `CartItemRepository`

---

### `service`
Bevat alle **businesslogica**.

#### `CartService`
Verantwoordelijk voor reservaties en stockbeheer.

**Belangrijkste verantwoordelijkheden**
- toevoegen en verwijderen van items
- aanpassen van aantallen
- berekenen van totaalprijs
- correcte stockverwerking bij elke actie

Alle stocklogica gebeurt transactioneel om inconsistenties te vermijden.

---

### `controller`
Controllers die instaan voor navigatie en gebruikersinteractie.

#### `AuthController`
- Login en registratie
- Validatie van gebruikersgegevens
- Wachtwoord hashing via BCrypt

#### `CatalogController`
- Overzicht van materialen
- Filteren op categorie en zoekterm
- Live stockweergave

#### `CartController`
- Beheer van reservaties
- Checkout flow
- Omzetten van reservatie naar order

#### `HomeController`
- Basisnavigatie
- Startpagina

---

## 4. Security Overzicht

- Spring Security met rolgebaseerde toegang (USER / ADMIN)
- BCrypt hashing voor wachtwoorden
- CSRF-bescherming op formulieren
- Beveiligde routes voor adminfunctionaliteit
- Geen plain-text wachtwoorden in de code

Gevoelige configuratie (zoals default wachtwoorden) wordt via
`application.properties` geladen en is uitgesloten via `.gitignore`.

---

## 5. Database & Persistentie

- H2 file-based database
- Data blijft behouden bij herstart
- JPA + Hibernate voor ORM
- Entiteiten zijn genormaliseerd en relationeel gekoppeld

---

## 6. Uitbreidbaarheid

De applicatie is eenvoudig uitbreidbaar door:
- nieuwe categorieën of producten toe te voegen
- extra rollen te definiëren
- bijkomende validaties of businessregels in services te implementeren

---

## 7. Broncode

De volledige broncode is beschikbaar via GitHub:  
https://github.com/AdrienG0/EindProject.git


