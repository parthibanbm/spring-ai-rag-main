# Spring AI RAG with Ollama, Qdrant, and MySQL

A local Retrieval-Augmented Generation (RAG) application built with Spring Boot and Spring AI. It loads a PDF employee handbook into Qdrant, retrieves the most relevant content for a question, and uses a local Ollama model to generate an answer.

## Architecture

```text
PDF handbook → Ollama embeddings → Qdrant vector store

User question → Qdrant similarity search → relevant PDF content
                                            ↓
                                  Ollama chat model → answer
```

## Tech stack

- Java 21 and Spring Boot
- Spring AI
- Ollama
  - `llama3.2` for chat responses
  - `nomic-embed-text` for embeddings
- Qdrant vector database
- MySQL for chat-memory persistence
- Docker Compose

## Prerequisites

Install the following before running the project:

- Java 21
- Maven (or use the Maven Wrapper if included)
- Docker Desktop
- MySQL running locally
- [Ollama](https://ollama.com/download)

## 1. Create the MySQL database

Start MySQL and create the database used by the application:

```sql
CREATE DATABASE javatechie;
```

The default configuration is:

```yaml
username: root
password: password
```

Update `src/main/resources/application.yaml` if your local MySQL credentials differ.

## 2. Start Qdrant

From the project root:

```bash
docker compose up -d
```

Qdrant is exposed on:

- REST/UI: `http://localhost:6333/dashboard/`
- gRPC: `localhost:6334`

To stop Qdrant:

```bash
docker compose down
```

## 3. Install and prepare Ollama

Install Ollama for your operating system, then pull the required local models:

```bash
ollama pull llama3.2
ollama pull nomic-embed-text
```

Confirm that Ollama is available:

```bash
ollama list
```

Ollama normally runs at `http://localhost:11434`. If it is not running, start it in a separate terminal:

```bash
ollama serve
```

## 4. Configure the application

Use this Ollama configuration in `src/main/resources/application.yaml`:

```yaml
spring:
  ai:
    model:
      chat: ollama
      embedding: ollama
    ollama:
      chat:
        model: llama3.2
      embedding:
        model: nomic-embed-text
    vectorstore:
      qdrant:
        initialize-schema: true
        host: localhost
        port: 6334
        collection-name: javatechie
```

The project does not need an OpenAI API key when using Ollama. Remove the `spring-ai-starter-model-openai` dependency from `pom.xml` and use `spring-ai-starter-model-ollama` instead.

## 5. Run the application

From the project root:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

The application starts on `http://localhost:9292`.

On startup, the application reads `javatechie_employee_handbook_rag_demo.pdf`, splits it into chunks, creates embeddings through Ollama, and stores them in Qdrant.

## Test the RAG endpoint

### Endpoint

```text
GET /rag/connect
```

### Required parameters

| Input | Description |
| --- | --- |
| `prompt` query parameter | Question to ask about the PDF handbook. |
| `username` header | Identifier used for chat-memory history. |

### Example using curl

```bash
curl --get "http://localhost:9292/rag/connect" \
  --data-urlencode "prompt=What is the leave policy?" \
  -H "username: parthi"
```

### Example URL

```text
http://localhost:9292/rag/connect?prompt=What%20is%20the%20leave%20policy%3F
```

Include this request header:

```text
username: parthi
```

## API documentation

When the application is running, open Swagger UI:

```text
http://localhost:9292/swagger-ui/index.html
```

## Troubleshooting

| Problem | Resolution |
| --- | --- |
| `ollama: command not found` | Install Ollama, then restart the terminal so its PATH update is loaded. |
| Cannot connect to Ollama | Run `ollama serve` and check `http://localhost:11434`. |
| Cannot connect to Qdrant | Run `docker compose up -d` and check `docker ps`. |
| MySQL authentication/connection error | Confirm MySQL is running, database `javatechie` exists, and the datasource credentials are correct. |
| Qdrant collection dimension error | Delete/recreate the development collection after changing embedding models; different embedding models can produce different vector sizes. |

## Notes

- The first run can take longer because the PDF is embedded and stored in Qdrant.
- Ollama models consume local disk space and RAM/CPU (or GPU if available).
- This configuration is designed for local development and learning; no cloud API key is required.
