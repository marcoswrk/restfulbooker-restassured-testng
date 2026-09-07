# Automação RestAssured com TestNG

Este projeto contém testes automatizados para a API pública de reservas [Restful Booker](https://restful-booker.herokuapp.com/apidoc/index.html), implementados em Java com RestAssured e TestNG.

## Objetivo

Validar o comportamento da API de autenticação e gerenciamento de reservas, cobrindo cenários como:

- health check do servidor (`/ping`)
- autenticação (`/auth`)
- criação, consulta, atualização, remoção e listagem de reservas (`/booking`)
- filtros por nome, datas e casos inválidos

## Stack

- Java 17
- Maven
- TestNG
- RestAssured
- Jackson
- Lombok
- DataFaker

## Estrutura

```text
restfulbooker-restassured-testng/
├── pom.xml
├── README.md
├── src/
│   ├── main/java/com/rest/project/Main.java
│   └── test/java/
│       ├── api/
│       │   ├── AuthApi.java
│       │   └── BookingApi.java
│       ├── base/
│       │   └── BaseTest.java
│       ├── model/
│       │   └── BookingModel.java
│       ├── tests/
│       │   ├── AuthTest.java
│       │   ├── BookingTest.java
│       │   └── PingTest.java
│       └── utils/
│           └── TestData.java
└── target/ (gerado pela execução dos testes)
```

## Casos de teste cobertos

### PingTest

- `getPing()`
  - valida que o endpoint `/ping` responde corretamente

### AuthTest

- `putAuth()`
  - autenticação com credenciais válidas
- `putAuthInvalid()`
  - autenticação com senha inválida e validação da mensagem de erro

### BookingTest

- `postBooking()`
  - cria uma reserva
- `getCreatedBooking()`
  - consulta uma reserva criada
- `putBooking()`
  - atualização completa de uma reserva
- `patchBooking()`
  - atualização parcial
- `deleteBookingAndGetDeletionConfirmation()`
  - exclusão e validação de 404 após remoção
- `getAllBookings()`
  - consulta todas as reservas
- `getBookingByNameAndLastName()`
  - filtro por nome e sobrenome
- `getBookingByCheckInAndCheckOut()`
  - filtro por datas
- `getInvalidDateBooking()`
  - cenário de data inválida

## Requisitos

- JDK 17+
- Maven 3.8+
