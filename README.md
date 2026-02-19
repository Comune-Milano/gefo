# Gestionale Fondi

Sistema applicativo per la **gestione, il monitoraggio e la rendicontazione** dei progetti finanziati tramite:

- **PN** – Programmi Operativi Nazionali
- **PNRR** – Piano Nazionale di Ripresa e Resilienza  
- **PNC** – Piano Nazionale Complementare  
- Altri fondi (es. REACT-EU, PON METRO, ecc.)

La soluzione supporta le Pubbliche Amministrazioni nel governo dell’intero ciclo di vita dei progetti finanziati.

---

##  Obiettivo del sistema

Fornire uno strumento unico per:

- censire i progetti finanziati  
- gestire aspetti finanziari e amministrativi  
- monitorare avanzamento e milestone  
- integrare i dati con i sistemi di contabilità  
- supportare la predisposizione delle domande di rimborso  

---

##  Architettura funzionale (moduli principali)

Il sistema è organizzato in moduli che coprono l’intero ciclo di vita progettuale:

1. **Anagrafica progetti**
2. **Integrazione con la contabilità**
3. **Monitoraggio avanzamento**
4. **Gestione rimborsi**

---

##  1. Anagrafica dei Progetti

Il **progetto** è l’unità informativa centrale del sistema.

Per ogni progetto è possibile gestire:

- Dati identificativi e descrittivi
- Tipologia di finanziamento
- Associazione a bandi
- Stato:
  - finanziario
  - amministrativo
- Responsabili del progetto
- Risorse finanziarie:
  - risorse finanziate
  - risorse dell’Ente
  - altre fonti
  - importi “di cui” (es. aumenti prezzi)

Le informazioni sono aggiornabili durante tutto il ciclo di vita del progetto.

---

##  2. Integrazione con la Contabilità

Il sistema si integra con la contabilità dell’Ente per collegare i dati economico-finanziari ai progetti.

Funzionalità principali:

- Associazione al progetto di:
  - **Impegni**
  - **Accertamenti**
  - **Capitoli di entrata/uscita**
  - Eventuali **crono-programmi** presenti in contabilità
- Recupero automatico di informazioni contabili (es. mandati e reversali)
- Visualizzazione aggiornata dei dati contabili collegati al progetto

---

##  3. Monitoraggio dei Progetti

Permette il controllo continuo dell’avanzamento.

### Gestione delle fasi

Per ogni progetto possono essere definite fasi personalizzabili, ad esempio:

- Studio di fattibilità  
- PFTE  
- Progettazione preliminare / definitiva / esecutiva  
- Gara  
- Aggiudicazione  
- Esecuzione lavori  
- Collaudo  

Per ogni fase sono gestiti:

- Data inizio prevista  
- Data fine prevista  
- Data inizio effettiva  
- Data fine effettiva  

Il sistema evidenzia eventuali ritardi tramite indicatori visivi (semafori).

### Milestone e scadenze

- Definizione di milestone per ogni fase
- Monitoraggio:
  - scadenze imminenti
  - milestone scadute
  - fasi non avviate o non concluse

---

##  4. Gestione dei Rimborsi

Supporto alla rendicontazione e alle domande di rimborso.

Funzionalità:

- Analisi dei **mandati** associati a un progetto in un periodo
- Predisposizione delle **domande di rimborso**
- Associazione elenco mandati a ciascuna domanda
- Associazione elenco delle reversali a ciascuna domanda
- Aggregazione di più domande di rimborso
- Consultazione storica delle domande per progetto

---

##  Utenti coinvolti

Il sistema è pensato per diversi attori dell’Ente:

- Responsabili di progetto  
- Uffici finanziari / contabili  
- Strutture di monitoraggio e controllo  

---

## Ambito di utilizzo

La piattaforma è utilizzabile per:

- Progetti PN
- Progetti PNRR  
- Progetti PNC  
- Altri programmi di finanziamento nazionali o comunitari  

---

##  Valore per l’Ente

Il sistema consente di:

- Centralizzare la gestione dei progetti finanziati  
- Migliorare il controllo su tempi, costi e avanzamento  
- Allineare dati progettuali e contabili  
- Facilitare attività di reporting e rendicontazione  