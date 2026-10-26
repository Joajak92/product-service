Product-service


Applikationen körs på port 8080

Applikationen startas med:
Windows: ./mvnw spring-boot:run
macOS: ./mvnw spring-boot:run


Miljövariabler:
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_KEY_ID
JWT_PRIVATE_KEY
JWT_PUBLIC_KEY

Applikationen product-service ansvarar för webshoppens produkter (lagerhållning) som sparas i en databas på neon.
I databas tabellen product sparas varje produkt med kolumnerna: id, description, name, price, stock, category och img_url.
Product-service samspelar med projekt-order-service med hjälp av service metoden decreaseStock. När en order läggs så skickas det till product-service och product-service ansvarar för att minska stock motsvarande antal produkter som beställts via order.

Teknikstack:
Java 17
Spring Boot 4.0.6
Spring MVC/Web
Spring Data JPA
PostgreSQL
H2
Maven
Lombok
MapStruct
Spring Security OAuth2 Resource Server med JWT
JWT-verifiering via JWKS från den separata mikroservicen auth-service
Springdoc OpenAPI / Swagger UI
Docker
GitHub Actions

Repositories:
Auth-service: https://github.com/katttiger/auth-service
Projekt-order-service: https://github.com/klasen98/projekt-order-service
Email-service: https://github.com/joel-schou/email-service
Gateway-service: https://github.com/joel-schou/gateway-service
Webshop-frontend: https://github.com/Joajak92/webshop-frontend
