# 💼 Sistema de Gestão de Recursos Humanos (HRMS) - Backend

Este é o serviço de backend do Sistema de RH, desenvolvido para gerenciar funcionários, folhas de pagamento (holerites), cargos, departamentos e escalas de trabalho. O projeto foca em segurança (OAuth2/JWT), cálculos automáticos de tributos (INSS/IRRF 2026) e arquitetura escalável.

## 🚀 Tecnologias Utilizadas

* **Java 21**: Utilizando Records, Pattern Matching e Virtual Threads.
* **Spring Boot 4.0.1**: Framework base para a construção da API REST.
* **Spring Security & OAuth2 Resource Server**: Autenticação e autorização via JWT (RSA).
* **PostgreSQL & Flyway**: Banco de dados relacional com controle de versionamento.
* **Docker & Docker Compose**: Orquestração de containers para banco e aplicação.
* **SpringDoc OpenAPI (Swagger)**: Documentação interativa e testes de endpoint.

## 🏗️ Arquitetura e Funcionalidades

O sistema foi desenhado seguindo princípios de Clean Code e arquitetura em camadas (Controller -> Service -> Repository):

* **Gestão de Funcionários**: CRUD completo e processo de "Promoção" para vincular colaborador a um usuário de sistema.
* **Motor de Holerite**: Cálculo automatizado baseado na legislação brasileira (Projeção 2026):
    * **INSS**: Cálculo progressivo (Teto R$ 8.475,55).
    * **IRRF**: Dedução por faixa salarial e isenção.
    * **DSR**: Cálculo proporcional sobre horas extras.
* **Registro de Ponto**: Sistema de Clock-in/out com validação de jornada.

## 🛠️ Configuração Inicial do Banco (data.sql)

Ao iniciar a aplicação pela primeira vez, os seguintes registros mestre são inseridos automaticamente para garantir o funcionamento das regras de negócio:

### Perfis de Acesso (Roles)
| ID | Nome | Descrição |
| :--- | :--- | :--- |
| 1 | `ROLE_ADMIN` | Acesso total administrativo |
| 2 | `ROLE_MANAGER` | Gestão de equipes e escalas |
| 3 | `ROLE_EMPLOYEE` | Acesso pessoal e registro de ponto |

### Status do Funcionário
| ID | Nome | Significado |
| :--- | :--- | :--- |
| 1 | `ACTIVE` | Funcionário em exercício |
| 2 | `AWAY` | Afastado (Férias, Licença, etc) |
| 3 | `DISMISSED` | Desligado da empresa |

---

## 🛣️ Guia de Endpoints (API Reference)

A documentação completa e interativa pode ser acessada em: `http://localhost:8080/swagger-ui/index.html`

### 🔐 Autenticação
* **POST** `/login`: Autentica o usuário e retorna o JWT com as roles.

### 👥 Funcionários (Employees)
* **GET** `/api/employees`: Lista todos os funcionários.
* **POST** `/api/employees`: Cadastra um novo funcionário.
* **GET** `/api/employees/me`: Retorna os dados do funcionário logado.
* **POST** `/api/employees/{id}/promote`: Cria um usuário de acesso para um funcionário existente.

### 💰 Folha de Pagamento (Payrolls)
* **POST** `/api/payrolls`: Gera folha de pagamento padrão.
* **POST** `/api/payrolls/generate`: Gera folha customizada (por data/ID).
* **GET** `/api/payrolls/me`: Histórico de holerites do usuário logado.

### ⏱️ Registro de Ponto (Time Records)
* **POST** `/api/records/employee`: Realiza o "Clock-in/out" (Batida de ponto).
* **GET** `/api/records/me`: Visualiza o espelho de ponto do mês.

### 🏢 Estrutura Organizacional
* **Endpoints para**: `/api/departments`, `/api/positions` e `/api/scales` (CRUD completo para gestão de departamentos, cargos e escalas de trabalho).

---

## 📊 Estrutura de Dados

O banco de dados PostgreSQL segue um modelo relacional otimizado para consultas de histórico e auditoria.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java 21 instalado.
* Docker e Docker Compose instalados.
* Maven 3.9+.

### Passo a Passo

1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/Fariasartuur/backend-recursos-humanos.git](https://github.com/Fariasartuur/backend-recursos-humanos.git)
    cd backend-recursos-humanos
    ```

2.  **Subir o banco de dados via Docker:**
    ```bash
    docker-compose up -d
    ```

3.  **Executar a aplicação:**
    ```bash
    mvn spring-boot:run
    ```

4.  **Acessar a documentação (Swagger):**
    Acesse: `http://localhost:8080/swagger-ui/index.html` para testar os endpoints.
