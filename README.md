# Loan Document Generator API

Spring Boot REST API for creating loan document templates with placeholders and generating documents from loan data.

## Tech stack
Java 21, Spring Boot, Spring Data JPA, MySQL, JUnit 5, Maven

## Run locally
1. Start MySQL on port 3306
2. Set your database username and password in `src/main/resources/application.properties`
3. Run `./mvnw spring-boot:run`

## Endpoints
- `POST /templates` creates a template
- `GET /templates` lists templates
- `POST /documents/generate` fills a template with loan data and saves the document
- `GET /documents/{id}` fetches one generated document
- `GET /documents?customerId=` lists a customer's documents

## Tests
`./mvnw test` runs unit tests for the placeholder logic and MockMvc controller tests (success, missing placeholder, unknown template, invalid email).

## Run with Docker
docker compose up --build
Then open http://localhost:8080/swagger-ui.html

## Run on Kubernetes (Docker Desktop)
docker build -t loandocs:1.0 .
kubectl apply -f k8s/
kubectl port-forward svc/loandocs 8080:8080
