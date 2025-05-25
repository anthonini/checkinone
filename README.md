# 🏨 CheckInOne

**CheckInOne** é um sistema web completo para gestão de reservas de hotel. Desenvolvido com Spring Boot, ele oferece funcionalidades robustas para o gerenciamento de quartos, hóspedes e reservas, com controle de acesso por perfis de usuário e uma API REST documentada via Swagger.

## 🚀 Funcionalidades

- Autenticação e autorização com Token JWT (Spring Security)
- Perfis de usuário: Funcionário e Administrador
- Cadastro e gerenciamento de hotéis
- Cadastro e gerenciamento de quartos
- Cadastro e gerenciamento de hópedes
- Cadastro de funcionários
- Verificação da disponibilidade de quartos para o período solicitado.
- Criação, alteração e cancelamento de reservas
- Visualização de histórico de reservas de um hóspede
- Cadastro e status de pagamento
- Geração de faturas
- API REST com documentação OpenAPI
- Suporte a múltiplos ambientes (`dev`, `prod`)

## 🧱 Arquitetura

- Java 17+
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- API RESTful + Swagger (springdoc-openapi)
- Frontend: Thymeleaf

## 🗃️ Entidades Principais

- `Hotel`
- `Quarto`
- `Hóspede`
- `Reserva`
- `Pagamento`
- `Funcionario`
- `Permissao`
- `Usuario`: controla autenticação e perfis (Funcionário ou Admin)

## 🔐 Segurança

- Autenticação via token JWT
- Controle de acesso por perfil de usuário (admin/funcionario)
- Proteção de endpoints REST por roles

## 📦 Como executar

### Requisitos

- Java 17+
- Maven
- PostgreSQL

### Execução (modo desenvolvimento)

```bash
./mvnw spring-boot:run
```

Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🔄 Perfis de ambiente

O sistema possui perfis de configuração:

- `dev`: Banco PostgreSQL + configurações de desenvolvimento
- `prod`: Banco PostgreSQL + configurações de produção

Configure o perfil ativo em:

```yaml
# application.yml
spring:
  profiles:
    active: dev
```

## 📁 Estrutura de Pacotes

```
com.checkinone
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── config
└── exception
```

## 📄 Documentação da API

Acesse a interface Swagger em:

```
http://localhost:8080/swagger-ui.html
```

## ✅ Status do Projeto

🚧 Em desenvolvimento  
📅 Última atualização: maio/2025

---

**CheckInOne** — uma solução moderna para gestão de reservas hoteleiras.
