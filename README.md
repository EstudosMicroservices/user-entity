### Português <img width="13" height="15" alt="br-flag" src="https://github.com/user-attachments/assets/7f2c07c5-f18d-4aa0-8c3f-9c4ce96ae63f" />
- [Diagrama](#diagramas-da-arquitetura-e-fluxo-architecture-and-flow-diagrams)
- [Explicação](#português-)
- [Como executar local (com Docker)](#-m%C3%A9todo-1-executando-com-docker-compose-recomendado)
- [Como executar local (sem Docker)](#-m%C3%A9todo-2-executando-a-aplica%C3%A7%C3%A3o-localmente-idemaven)

### English <img width="13" height="15" alt="image" src="https://github.com/user-attachments/assets/96c27655-5a8c-436a-8a9c-94e8f2b4496c" />

- [Diagram](#diagramas-da-arquitetura-e-fluxo-architecture-and-flow-diagrams)
- [Explanation](#english--1)
- [Run project (with Docker)](#-method-1-running-with-docker-compose-recommended)
- [Run project (without Docker)](#-method-2-running-the-application-locally-idemaven)
## Diagramas da Arquitetura e Fluxo (Architecture and Flow diagrams)

![Hex_architecture_layerV3 drawio](https://github.com/user-attachments/assets/c8e8ecee-092a-47c3-8de0-43a5a146348f)
  ![hexagonal_architeture_fluxogram drawio](https://github.com/user-attachments/assets/751cc8ff-76d2-4ce5-bece-7fd7c7c24d40)


# Português <img width="20" height="20" alt="br-flag" src="https://github.com/user-attachments/assets/7f2c07c5-f18d-4aa0-8c3f-9c4ce96ae63f" />


## Entidade de Usuário em Arquitetura Hexagonal
Este projeto implementa o gerenciamento de uma entidade de usuário utilizando os princípios da Arquitetura Hexagonal. O objetivo é criar um sistema desacoplado, testável e de fácil manutenção, onde a lógica de negócio principal (domínio) é isolada das dependências externas, como banco de dados e frameworks.

## 🚀 Últimas Mudanças e Evoluções do Projeto
Este repositório reflete uma evolução contínua. As atualizações mais recentes incluem:

- **Pipeline de CI/CD Completa:** Implementação de um fluxo de Integração e Entrega Contínua com GitHub Actions. A pipeline automatiza o build, a execução de testes unitários e de integração, a análise de qualidade de código com SonarCloud e o empacotamento da aplicação.

- **Estratégia de Testes Otimizada:** Refatoração completa dos testes de integração. Agora, utilizamos um único contêiner de banco de dados (static @Container com Testcontainers) que é compartilhado entre todas as classes de teste, resultando em uma execução muito mais rápida e um uso mais eficiente de recursos.

- **Simulação Local da Pipeline:** Configuração avançada para permitir que a pipeline completa seja executada localmente através da ferramenta act. Isso inclui o gerenciamento de segredos e a complexa configuração de rede Docker necessária para a comunicação entre os testes e o Testcontainers.

- **Solidificação da Filosofia Pragmática:** O design do projeto foi refinado para solidificar o equilíbrio entre a pureza teórica da Arquitetura Hexagonal e a produtividade do ecossistema Spring Boot, notadamente na decisão de unificar o modelo de domínio e a entidade de persistência.

- **Filosofia do Projeto:** Um Equilíbrio entre Pureza e Pragmatismo
A Arquitetura Hexagonal "pura" prega um isolamento total da camada de domínio. No entanto, ao trabalhar com ecossistemas robustos como o Spring Boot, uma abordagem 100% purista pode gerar um excesso de código boilerplate (mapeadores, DTOs intermediários) que nem sempre se traduz em ganhos práticos.

Este projeto adota uma abordagem mais equilibrada. Em vez de buscar a pureza absoluta, optamos por um design pragmático que aproveita o poder do Spring Data JPA, fazendo concessões controladas para aumentar a produtividade e simplificar o código, sem sacrificar os principais benefícios do design hexagonal.

A principal mudança em relação a uma abordagem purista é a unificação do Modelo de Domínio com a Entidade de Persistência.

  
## Camada de Domínio (Mundo Interno)
O núcleo da aplicação. Esta camada contém a lógica e as regras de negócio essenciais, sendo o coração do sistema.

### Modelo de Domínio / Entidade

- **Benefício:** Esta decisão reduz drasticamente a complexidade e o código boilerplate, eliminando a necessidade de mapeadores entre o domínio e a persistência. As operações de CRUD se tornam mais diretas e a manutenção é simplificada.

- **Mudança Pragmática:** Em uma abordagem purista, teríamos um UserModel (um objeto de domínio puro) e um UserEntity (um objeto de persistência). Neste projeto, essas duas classes foram unificadas. Nosso modelo de domínio é anotado com @Entity do JPA.

- **Trade-off:** Aceitamos um acoplamento controlado com o framework de persistência (JPA) em nossa camada de domínio. Essa é uma concessão consciente em nome da produtividade.

- **Lombok:** Continuamos usando anotações do Lombok, pois elas auxiliam na redução de código repetitivo sem se acoplar às regras de negócio.

### Portas (Inbound e Outbound)

As portas são as interfaces que definem os contratos de comunicação entre o domínio e o mundo exterior. Elas são a base do isolamento, garantindo que a lógica de negócio não dependa de implementações concretas.

- **Inbound:** Pontos de entrada que interagem com a lógica central (geralmente implementados pelos Casos de Uso).

- **Outbound:** Pontos de saída que definem como a lógica central se comunica com sistemas externos (ex: um repositório de banco de dados).

## Camada de Aplicação / Camada de Portas
Esta camada atua como um intermediário, orquestrando o fluxo de dados e invocando a lógica de negócio.

- **Casos de Uso (Use Cases):** As interfaces dos Casos de Uso definem as operações de negócio que a aplicação oferece. Suas implementações (UseCaseImpl) contêm a lógica de aplicação, orquestrando as interações com o domínio para executar uma tarefa específica.

- **DTOs (Data Transfer Objects):** Objetos que carregam dados entre as camadas, especialmente entre o mundo externo (ex: requisições HTTP) e a camada de aplicação. Eles são simples e não contêm regras de negócio.

- **Mappers:** Transformam dados entre diferentes representações (ex: de UserDto para o nosso modelo de domínio User).

- **Validação:**
Implementa lógicas de validação para os dados de entrada (DTOs), garantindo que apenas dados válidos cheguem à lógica de negócio.

- **Exceptions:**
Exceções customizadas para um tratamento de erros mais claro e específico ao domínio.
#

## Camada de Infraestrutura / Camada de Adaptadores (Mundo Externo)
Responsável pela interação com tecnologias externas, como bancos de dados, APIs, filas de mensagens, etc. É aqui que as "portas" da camada de domínio são implementadas.

### Adaptadores (Inbound e Outbound)

São componentes que implementam as portas, conectando a aplicação ao mundo externo.

- **Adaptadores Inbound:** "Dirigem" a aplicação. Um exemplo clássico é um Controller REST, que recebe requisições HTTP e as traduz em chamadas para os Casos de Uso.

- **Adaptadores Outbound:** São "dirigidos" pela aplicação. O principal exemplo é o Repository do Spring Data JPA, que implementa a porta de persistência definida no domínio, interagindo com o banco de dados.

### Persistência

Contém as implementações concretas da persistência.

- ``UserRepository:`` Interface do Spring Data JPA que estende JpaRepository. O Spring cria a implementação em tempo de execução, abstraindo o acesso aos dados e simplificando drasticamente a implementação do nosso adaptador de saída.

### Configuração

- **Mudança Pragmática:** Em vez de configurar beans para injetar implementações manualmente e manter o domínio 100% agnóstico, aproveitamos a injeção de dependência nativa do Spring. Nossos UseCases recebem implementações concretas dos repositórios, o que é mais simples e direto no ecossistema Spring.

- ``GlobalExceptionHandler:``
Um handler global que captura exceções lançadas pela aplicação e as converte em respostas HTTP apropriadas, centralizando o tratamento de erros.


## Execução

### 🐋 Método 1: Executando com Docker Compose (Recomendado)
Esta é a forma mais fácil e recomendada, pois o docker-compose.yml gerencia tanto o banco de dados quanto a sua aplicação, incluindo a rede entre eles.

#### Pré-requisitos
Docker Desktop instalado.
Um arquivo Dockerfile na raiz do seu projeto para construir a imagem da aplicação (build: .).

#### Passos
Abra um terminal na pasta raiz do seu projeto.

Execute o seguinte comando:

```
docker-compose up --build up
```


- **--build:** Força a reconstrução da imagem da sua aplicação (app) caso haja alguma alteração no código.

### O que Acontece
- O Docker irá construir a imagem da sua aplicação Spring a partir do Dockerfile.
- O contêiner mysql será iniciado.
- O Docker Compose irá aguardar até que o healthcheck do MySQL passe, garantindo que o banco de dados está pronto para aceitar conexões.

Somente então, o contêiner app será iniciado.

Sua aplicação Spring, dentro do contêiner app, irá se conectar ao banco de dados usando a URL ``jdbc:mysql://mysql:3306/user``. Note que aqui ela usa o nome do serviço (mysql) como host, pois eles estão na mesma rede Docker.

Sua aplicação estará acessível no seu navegador em ``http://localhost:8081``.

### 💻 Método 2: Executando a Aplicação Localmente (IDE/Maven)
Neste método, você executa a aplicação Spring diretamente na sua máquina (via IDE ou Maven) e a conecta ao banco de dados que estará rodando em um contêiner Docker.

- **Passo 1:** Iniciar Apenas o Banco de Dados
Use o Docker Compose para iniciar somente o serviço do MySQL em segundo plano (-d).


```
docker-compose up -d mysql
```

Isso iniciará o contêiner do MySQL, e ele estará acessível na sua máquina em ``localhost:3308``, conforme o mapeamento de portas ("3308:3306").

- **Passo 2:** Configurar as Variáveis de Ambiente
Seu arquivo ``application.properties`` espera as credenciais do banco em variáveis de ambiente (``${USER_DB}`` e ``${PASS_DB}``). Você precisa defini-las no terminal antes de iniciar a aplicação.

No PowerShell (Windows):

```
$env:USER_DB="root"
$env:PASS_DB="root"
```
No Bash/Zsh (Linux/macOS):

```
export USER_DB=root
export PASS_DB=root
```

Se você estiver executando pela sua IDE (IntelliJ, Eclipse), pode configurar essas variáveis de ambiente diretamente na "Run/Debug Configuration".

Passo 3: Executar a Aplicação Spring
Agora, com o banco de dados rodando e as variáveis de ambiente configuradas, inicie sua aplicação com o Maven:

```
mvn spring-boot:run
```
#### O que Acontece
Sua aplicação Spring iniciará na sua máquina local.

Ela lerá o application.properties e usará a URL `jdbc:mysql://localhost:3308/user` para se conectar ao contêiner MySQL que está rodando.

Sua aplicação estará acessível no seu navegador em http://localhost:8080 (a porta padrão do Spring Boot).

# English <img width="20" height="20" alt="image" src="https://github.com/user-attachments/assets/96c27655-5a8c-436a-8a9c-94e8f2b4496c" />


## User Entity in Hexagonal Architecture
This project implements the management of a user entity using the principles of Hexagonal Architecture. The goal is to create a decoupled, testable, and easily maintainable system, where the core business logic (domain) is isolated from external dependencies such as databases and frameworks.

## 🚀 Latest Changes and Project Evolutions
This repository reflects continuous evolution. The most recent updates include:

- **Complete CI/CD Pipeline:** Implementation of a full Continuous Integration and Continuous Delivery flow with GitHub Actions. The pipeline automates the build, execution of unit and integration tests, code quality analysis with SonarCloud, and application packaging.

- **Optimized Testing Strategy: A complete refactoring of the integration testing strategy. We now use a single database container (static @Container with Testcontainers) that is shared across all test classes, resulting in much faster execution and more efficient resource usage.

- **Local Pipeline Simulation:** Advanced configuration to allow the entire pipeline to be run locally using the act tool. This includes secret management and the complex Docker networking required for communication between the tests and Testcontainers.

- **Solidification of the Pragmatic Philosophy:** The project's design has been refined to solidify the balance between the theoretical purity of Hexagonal Architecture and the productivity of the Spring Boot ecosystem, notably in the decision to unify the domain model and the persistence entity.

### Project Philosophy: A Balance Between Purity and Pragmatism
"Pure" Hexagonal Architecture advocates for the complete isolation of the domain layer. However, when working with robust ecosystems like Spring Boot, a 100% purist approach can lead to an excess of boilerplate code (mappers, intermediate DTOs) that doesn't always translate into practical gains.

This project adopts a more balanced approach. Instead of striving for absolute purity, we opted for a pragmatic design that leverages the power of Spring Data JPA, making controlled trade-offs to increase productivity and simplify the code, without sacrificing the main benefits of the hexagonal design.

The main change from a purist approach is the unification of the Domain Model with the Persistence Entity.

## Domain Layer (Internal World)
The core of the application. This layer contains the essential business logic and rules, being the heart of the system.

### Domain Model / Entity

- **Benefit:** This decision drastically reduces complexity and boilerplate code by eliminating the need for mappers between the domain and persistence. CRUD operations become more straightforward, and maintenance is simplified.

- **Pragmatic Change:** In a purist approach, we would have a UserModel (a pure domain object) and a UserEntity (a persistence object). In this project, these two classes have been unified. Our domain model is annotated with JPA's @Entity.

- **Trade-off:** We accept a controlled coupling with the persistence framework (JPA) in our domain layer. This is a conscious trade-off made for the sake of productivity.

- **Lombok:** We continue to use Lombok annotations as they help reduce repetitive code without coupling to the business rules.

### Ports (Inbound and Outbound)

Ports are the interfaces that define the communication contracts between the domain and the outside world. They are the foundation of isolation, ensuring that the business logic does not depend on concrete implementations.

- **Inbound:** Entry points that interact with the core logic (usually implemented by Use Cases).

- **Outbound:** Exit points that define how the core logic communicates with external systems (e.g., a database repository).

## Application Layer / Port Layer

This layer acts as an intermediary, orchestrating the data flow and invoking business logic.

- **Use Cases:** The Use Case interfaces define the business operations the application offers. Their implementations (UseCaseImpl) contain the application logic, orchestrating interactions with the domain to perform a specific task.

- **DTOs (Data Transfer Objects):** Objects that carry data between layers, especially between the external world (e.g., HTTP requests) and the application layer. They are simple and contain no business rules.

- **Mappers:** Transform data between different representations (e.g., from UserDto to our domain model User).

- **Validation:**
Implements validation logic for incoming data (DTOs), ensuring that only valid data reaches the business logic.

- **Exceptions:**
Custom exceptions for clearer and more domain-specific error handling.

## Infrastructure Layer / Adapter Layer (External World)
Responsible for interacting with external technologies, such as databases, APIs, message queues, etc. This is where the domain layer's "ports" are implemented.

### Adapters (Inbound and Outbound)

These are the components that implement the ports, connecting the application to the external world.

- **Inbound Adapters:** They "drive" the application. A classic example is a REST Controller, which receives HTTP requests and translates them into calls to the Use Cases.

- **Outbound Adapters:** They are "driven" by the application. The main example is the Spring Data JPA Repository, which implements the persistence port defined in the domain, interacting with the database.

### Persistence

Contains the concrete persistence implementations.

- ``UserRepository:`` A Spring Data JPA interface that extends JpaRepository. Spring creates the implementation at runtime, abstracting data access and drastically simplifying the implementation of our outbound adapter.

### Configuration

- **Pragmatic Change:** Instead of configuring beans to manually inject implementations and keep the domain 100% agnostic, we leverage Spring's native dependency injection. Our Use Cases receive concrete repository implementations, which is simpler and more direct within the Spring ecosystem.

- ``GlobalExceptionHandler:``
A global handler that catches exceptions thrown by the application and converts them into appropriate HTTP responses, centralizing error handling.

## Running project

### 🐋 Method 1: Running with Docker Compose (Recommended)
This is the easiest and recommended way, as the docker-compose.yml file manages both the database and your application, including the network between them.

#### Prerequisites
Docker Desktop installed.

A Dockerfile in the root of your project to build the application image (build: .).

#### Steps
Open a terminal in the project's root folder.

Run the following command:

```
docker-compose up --build
up
```

- **--build:** Forces the rebuild of your application's image (app) if there are any code changes.

#### What Happens

- Docker will build the image for your Spring application from the Dockerfile.

- The mysql container will be started.

- Docker Compose will wait for the MySQL health check to pass, ensuring the database is ready to accept connections.

Only then will the app container be started.

Your Spring application, inside the app container, will connect to the database using the URL `jdbc:mysql://mysql:3306/user`. Note that it uses the service name (mysql) as the host, as they are on the same Docker network.

Your application will be accessible in your browser at http://localhost:8081.

## 💻 Method 2: Running the Application Locally (IDE/Maven)
In this method, you run the Spring application directly on your machine (via IDE or Maven) and connect it to the database, which will be running in a Docker container.

- **Step 1:** Start the Database Only
Use Docker Compose to start only the MySQL service in the background (-d).

```
docker-compose up -d mysql
```
This will start the MySQL container, and it will be accessible on your machine at localhost:3308, according to the port mapping ("3308:3306").

- **Step 2:** Configure Environment Variables
Your application.properties file expects the database credentials in environment variables (${USER_DB} and ${PASS_DB}). You need to set them in your terminal before starting the application.

In PowerShell (Windows):

```
$env:USER_DB="root"
$env:PASS_DB="root"
```

In Bash/Zsh (Linux/macOS):

```
export USER_DB=root
export PASS_DB=root
```
If you are running from your IDE (IntelliJ, Eclipse), you can configure these environment variables directly in the "Run/Debug Configuration".

- **Step 3:** Run the Spring Application
Now, with the database running and the environment variables set, start your application with Maven:

```
mvn spring-boot:run
```
