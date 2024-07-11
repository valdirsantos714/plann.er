# Plann.er

## Descrição

Plann.er é uma simples Api que permite o planejamento e gerenciamento de viagens de forma colaborativa. Usuários podem cadastrar viagens, confirmar presença, adicionar atividades e links importantes, e convidar participantes para a viagem.

## Funcionalidades
- Cadastro de viagem com detalhes do destino, datas, e-mails dos convidados, e informações do criador.
- Confirmação e cancelamento de viagens.
- Confirmação e cancelamento de presença dos participantes.
- Adição de links importantes e atividades relacionadas à viagem.
- Consulta de detalhes da viagem, participantes, atividades, e links.

## Requisitos

- Java 17
- Spring Boot
- Maven

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- Lombok
- Validation
- Spring Boot DevTools
- Flyway
- Maven
- PostgreSQL

## Como Executar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/valdirsantos714/plann.er.git
   cd Plann.er
   ```

2. **Configure o banco de dados PostgreSQL**:
    - Crie um banco de dados no PostgreSQL.
    - Atualize o arquivo `application.properties` com as configurações do seu banco de dados.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=org.hibernate.dialect.PostgreSQLDialect
   server.error.include-stacktrace=never
   ```

3. **Compile e execute a aplicação**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Acesse a aplicação**:
    - A aplicação estará disponível em `http://localhost:8080`.


## Endpoints

### 1. Cadastro de Viagem

**Endpoint**: `POST /trips`

**Descrição**: Cadastra uma nova viagem informando o local de destino, data de início, data de término, e-mails dos convidados, nome completo e endereço de e-mail do criador.

**Request Body**:
```json
{
  "destination": "Rio grande do norte",
  "starts_at": "2026-09-29T14:51:06",
  "ends_at": "2027-07-19T22:51:06",
  "owner_name": "Vitor",
  "owner_email": "Vitor@gmail.com",
  "participants": [
    {
      "email": "Valdir@gmail.com"
    }
  ]
}
```

**Response**:
```json
{
  "id": "uuid",
  "destination": "Rio grande do norte",
  "starts_at": "2026-09-29T14:51:06",
  "ends_at": "2027-07-19T22:51:06",
  "is_confirmed": false,
  "owner_name": "Vitor",
  "owner_email": "Vitor@gmail.com",
  "participants": [
    {
      "id": "uuid",
      "email": "Valdir@gmail.com",
      "is_confirmed": false
    }
  ]
}
```

### 2. Consulta de Viagem

**Endpoint**: `GET /trips/{tripId}`

**Descrição**: Retorna os detalhes de uma viagem específica.

**Response**:
```json
{
  "id": "uuid",
  "destination": "Rio grande do norte",
  "starts_at": "2026-09-29T14:51:06",
  "ends_at": "2027-07-19T22:51:06",
  "is_confirmed": true,
  "owner_name": "Vitor",
  "owner_email": "Vitor@gmail.com",
  "participants": [
    {
      "id": "uuid",
      "name": "Valdir",
      "email": "Valdir@gmail.com",
      "is_confirmed": true
    }
  ],
  "activities": [
    {
      "id": "uuid",
      "title": "Visita ao Parque",
      "occurs_at": "2026-10-01T10:00:00"
    }
  ],
  "links": [
    {
      "id": "uuid",
      "url": "http://airbnb.com/reserva123",
      "description": "Reserva do AirBnB"
    }
  ]
}
```

### 3. Atualização de Viagem

**Endpoint**: `PUT /trips/{tripId}`

**Descrição**: Atualiza os detalhes de uma viagem específica.

**Request Body**:
```json
{
  "destination": "São Paulo",
  "starts_at": "2026-10-01T14:51:06",
  "ends_at": "2027-08-19T22:51:06",
  "is_confirmed": true,
  "owner_name": "Vitor",
  "owner_email": "Vitor@gmail.com"
}
```

**Response**:
```json
{
  "id": "uuid",
  "destination": "São Paulo",
  "starts_at": "2026-10-01T14:51:06",
  "ends_at": "2027-08-19T22:51:06",
  "is_confirmed": true,
  "owner_name": "Vitor",
  "owner_email": "Vitor@gmail.com"
}
```

### 4. Confirmação de Viagem

**Endpoint**: `GET /trips/{tripId}/confirm`

**Descrição**: Confirma a viagem e notifica os participantes.

**Response**:
```json
{
  "message": "Viagem confirmada com sucesso."
}
```

### 5. Confirmação de Participante

**Endpoint**: `POST /participants/{participantId}/confirm`

**Descrição**: Confirma a presença de um participante na viagem.

**Request Body**:
```json
{
  "name": "Valdir"
}
```

**Response**:
```json
{
  "id": "uuid",
  "email": "valdir@gmail.com",
  "is_confirmed": true
}
```

### 6. Convidar Participante

**Endpoint**: `POST /trips/{tripId}/invites`

**Descrição**: Convida um novo participante para a viagem.

**Request Body**:
```json
{
  "email": "novo_participante@gmail.com"
}
```

**Response**:
```json
{
  "id": "uuid",
  "email": "novo_participante@gmail.com",
  "is_confirmed": false
}
```

### 7. Consultar Participantes

**Endpoint**: `GET /trips/{tripId}/participants`

**Descrição**: Retorna a lista de participantes de uma viagem específica.

**Response**:
```json
{
  "participants": [
    {
      "id": "uuid",
      "name": "Valdir",
      "email": "Valdir@gmail.com",
      "is_confirmed": true
    }
  ]
}
```

### 8. Cadastro de Atividade

**Endpoint**: `POST /trips/{tripId}/activities`

**Descrição**: Adiciona uma nova atividade à viagem.

**Request Body**:
```json
{
  "title": "Visita ao Parque",
  "occurs_at": "2026-10-01T10:00:00"
}
```

**Response**:
```json
{
  "id": "uuid",
  "title": "Visita ao Parque",
  "occurs_at": "2026-10-01T10:00:00"
}
```

### 9. Consultar Atividades de uma Viagem

**Endpoint**: `GET /trips/{tripId}/activities`

**Descrição**: Retorna a lista de atividades de uma viagem específica.

**Response**:
```json
{
  "activities": [
    {
      "id": "uuid",
      "title": "Visita ao Parque",
      "occurs_at": "2026-10-01T10:00:00"
    }
  ]
}
```

### 10. Criação de Link

**Endpoint**: `POST /trips/{tripId}/links`

**Descrição**: Adiciona um novo link importante à viagem.

**Request Body**:
```json
{
  "url": "http://airbnb.com/reserva123",
  "title": "Reserva do AirBnB"
}
```

**Response**:
```json
{
  "id": "uuid",
  "url": "http://airbnb.com/reserva123",
  "title": "Reserva do AirBnB"
}
```

### 11. Consultar Links de uma Viagem

**Endpoint**: `GET /trips/{tripId}/links`

**Descrição**: Retorna a lista de links importantes de uma viagem específica.

**Response**:
```json
{
  "links": [
    {
      "id": "uuid",
      "url": "http://airbnb.com/reserva123",
      "title": "Reserva do AirBnB"
    }
  ]
}
```
