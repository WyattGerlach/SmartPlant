## Local MongoDB for SmartPlant

This repo uses MongoDB for the backend. If MongoDB isn’t installed locally, the quickest way to get going is via Docker.

### Option 1: Docker (recommended)

1. Start MongoDB:
   - `docker compose up -d` (or `docker-compose up -d`)
2. Point Spring Boot to it (either method works):
   - Env var for current shell:
     - PowerShell: `$env:SPRING_DATA_MONGODB_URI = "mongodb://localhost:27017/smartplant"`
     - Bash: `export SPRING_DATA_MONGODB_URI="mongodb://localhost:27017/smartplant"`
   - Or set in `backend/src/main/resources/application.properties`:
     - `spring.data.mongodb.uri=mongodb://localhost:27017/smartplant`
3. Run the backend as usual.

To verify MongoDB is reachable: `Test-NetConnection -ComputerName localhost -Port 27017` (PowerShell) or `nc -zv localhost 27017`.

### Option 2: Install MongoDB locally

- Download MongoDB Community Server from mongodb.com and start the service, or run `mongod --dbpath C:\data\db`.
- Use the same connection URI as above.

### Notes

- The compose file exposes MongoDB on `localhost:27017` with a named volume `mongo_data` for persistence.
- No auth is enabled for local development convenience. Do not use this in production.

## One-command dev runner (Windows/PowerShell)

Use the helper script to start MongoDB in Docker and run the backend:

- PowerShell: `./scripts/dev.ps1`

What it does:
- Checks Docker availability and brings up `mongo` from `docker-compose.yml`.
- Waits until `localhost:27017` is reachable.
- Starts the Spring Boot app using Maven Wrapper (`backend/mvnw.cmd`).

Prereqs:
- Docker Desktop installed and running.
- Java 17+ (the project targets Java 17).

