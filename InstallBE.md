# Installazione Backend – Microservizi JHipster

Questa guida descrive l’installazione e l’avvio dei microservizi backend generati con **JHipster 7.9.3** fino all’esecuzione in ambiente Docker.

L'applicazione fa parte di un’architettura a microservizi e dipende da **JHipster Registry** per Service Discovery e Configuration.

---



## Prerequisiti

| Software       | Versione consigliata                   |


| Java           | 11+                                   |

| Node.js        | LTS (>=16)                            |

| Maven          | 3.8+ (oppure usare `./mvnw`)         |

| Docker         | Ultima versione                       |

| Docker Compose | Ultima versione                       |


---

##  Installazione del DB
Nella cartella DB sono presenti:
- Scriptdtabase.sql: script di creazione del DB, dell'utente, degli schemi e delle tabelle
- populate table.sql: script per popolare le tabelle con i dati minimi iniziali


---

##  Clonazione progetto

```bash
git clone <REPO_URL>
cd dmifonBE
```

---

##  Avvio servizi infrastrutturali

### JHipster Registry
```bash
docker-compose -f src/main/docker/jhipster-registry.yml up -d
```

UI: http://localhost:8761

### Database
```bash
docker-compose -f src/main/docker/postgresql.yml up -d
```

### Keycloak (OIDC)
```bash
docker-compose -f src/main/docker/keycloak.yml up -d
```

---

##  Avvio in sviluppo
```bash
./mvnw
```

---

##  Build produzione
```bash
./mvnw -Pprod clean verify
```

---

##  Build Docker
```bash
npm run java:docker
```

ARM:
```bash
npm run java:docker:arm64
```

---

##  Avvio con Docker
```bash
docker-compose -f src/main/docker/app.yml up -d
```

---

##  Stop
```bash
docker-compose -f src/main/docker/app.yml down
```
