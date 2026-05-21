\# Brokerage Tax Processing Platform



\## Overview



The Brokerage Tax Processing Platform is a full-stack application designed to process brokerage trade transactions, calculate realized gains/losses, compute tax liability, and support CSV-based trade ingestion.



The application includes a Spring Boot backend, Angular frontend, PostgreSQL database integration, GitHub Actions CI workflows, and optional Docker/Kubernetes deployment artifacts.



\## Features



\- Create and retrieve brokerage trades

\- Search trades by customer ID, symbol, and trade type

\- Upload trade transactions using CSV files

\- Calculate short-term and long-term capital gains

\- Calculate dividend income and tax liability

\- Generate customer-level tax summaries

\- Generate annual tax reports

\- View data through an Angular dashboard

\- Persist trade data in PostgreSQL

\- Automated CI builds using GitHub Actions



\## Tech Stack



\### Backend

\- Java 17

\- Spring Boot

\- Spring Web

\- Spring Data JPA

\- PostgreSQL

\- Maven



\### Frontend

\- Angular

\- TypeScript

\- SCSS

\- Angular HttpClient

\- Angular Routing



\### Database

\- PostgreSQL



\### DevOps

\- GitHub Actions

\- Docker

\- Docker Compose

\- Kubernetes manifests

\- AWS deployment-ready architecture



\## Project Structure



```text

brokerage-tax-platform

├── backend

│   ├── src

│   ├── pom.xml

│   └── Dockerfile

├── frontend

│   ├── src

│   ├── package.json

│   ├── Dockerfile

│   └── nginx

├── .github

│   └── workflows

│       ├── backend-ci.yml

│       └── frontend-ci.yml

└── README.md

