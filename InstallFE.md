# Installazione Frontend / Gateway – JHipster

Guida all’avvio del progetto Gateway/Frontend che espone la UI e instrada le chiamate ai microservizi backend.

---

## 🧰 Prerequisiti

| Software | Versione |

|----------|----------|

| Node.js  | LTS      |

| Java     | 11+      |

| Maven    | 3.8+     |

| Docker   | Ultima   |

---

##  Clonazione progetto
```bash
git clone <REPO_URL>
cd dmifonFE
```

---

##  Installazione dipendenze
```bash
npm install
```

---

##  Avvio sviluppo
```bash
npm start
```

Oppure solo gateway backend:
```bash
./mvnw
```

---

##  Build produzione
```bash
./mvnw -Pprod clean verify
```

---

## Build Docker
```bash
npm run java:docker
```

ARM:
```bash
npm run java:docker:arm64
```

---

##  Avvio completo Docker
```bash
docker-compose -f src/main/docker/app.yml up -d
```

---

## Stop
```bash
docker-compose -f src/main/docker/app.yml down
```
