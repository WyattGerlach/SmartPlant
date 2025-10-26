# README

## Getting Started

This repo uses Maven and Java 17

### Installing Java

1. Go to Eclipse Adoptium or another JDK provider. <https://adoptium.net/temurin/releases/?version=17&os=any&arch=any>
2. Download Java 17 (LTS) JDK for Windows.
3. Run the installer and follow the default options. Then:
   - Set JAVA_HOME environment variable:
   - Open Environment Variables → New System Variable
   - Name: JAVA_HOME
   - Value: Path to your JDK folder (e.g., C:\Program Files\Eclipse Adoptium\jdk-17orSomething)
   - Add %JAVA_HOME%\bin to the Path system variable.
4. Test installation:
   - bash: java -version
   - You should see the Java version printed

### Installing Maven

1. Download Maven from <https://maven.apache.org/download.cgi>
2. Use the binary zip (e.g., apache-maven-3.9.9-bin.zip)
3. Extract to a permanent location, e.g.: C:\Program Files\Maven\apache-maven-3.9.9
4. Set up environment variables:
   - Add Maven to PATH:
   - Open Environment Variables → edit Path → add: C:\Program Files\Maven\apache-maven-3.9.9\bin
   - (Optional) Create a system variable MAVEN_HOME pointing to Maven folder.
5. Test installation:
   - bash: mvn -v
   - You should see Maven version and Java version printed.

### Install VS Code extension packs

1. Java Extension Pack
2. Spring Boot Extension Pack

## Local MongoDB for SmartPlant

This repo uses MongoDB for the backend. If MongoDB isn’t installed locally, the quickest way to get going is via Docker.

### Option 1: Docker (recommended)

1. Start MongoDB:
   - `docker compose up -d` (or `docker-compose up -d`) from project root
2. Point Spring Boot to it (either method works):
   - Env var for current shell:
     - PowerShell: `$env:SPRING_DATA_MONGODB_URI = "mongodb://localhost:27017/smartplant"`
     - Bash: `export SPRING_DATA_MONGODB_URI="mongodb://localhost:27017/smartplant"`
   - Or set in `backend/src/main/resources/application.properties`:
     - `spring.data.mongodb.uri=mongodb://localhost:27017/smartplant`
3. Run the backend as usual. To do this, change directory to \backend and then run: ./mvnw spring-boot:run

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

## Testing

- Add an entry to plants using the web app (port 8080)
- run command `curl http://localhost:8080/api/plants` in powershell (or your equivalent for your terminal)
