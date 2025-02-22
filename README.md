# User Entity in Hexagonal Architeture


## Layers and Architecture Fluxogram
![Hex_architecture_layerV3 drawio](https://github.com/user-attachments/assets/c8e8ecee-092a-47c3-8de0-43a5a146348f)
  ![hexagonal_architeture_fluxogram drawio](https://github.com/user-attachments/assets/751cc8ff-76d2-4ce5-bece-7fd7c7c24d40)


# **English :us:**


## Domain Layer (Internal World)

The Domain layer is responsible for the management of the innermost parts of the application, in this layer, usually we have domain Models, Dtos, Ports and some UserServiceImpl.

### Models
Models represent domain concepts and are generally mutable. They encapsulate business logic and ensure data consistency within the domain.

### Ports (Inbound and Outbound)
Ports act as connection points between the internal world (domain) and the external world (infrastructure/adpater). They are usually interfaces that define contracts and are implemented by external adapters.
- Note that I'm referring to 'Ports' as a characteristic of the Domain Layer, which it is, but 'Ports' also has its own distinct layer.
- **Inbound** refers to **entry points** which will interact with the core logic of the application.
- **Outbound** refers to **exit points** they define the interfaces through which the core logic of the application communicates with external system. 


### Notes

- The domain layer **should not depend** on the application layer.

## Application Layer / Port Layer (Connection of Internal and External)

The Application layer, or Port Layer, act as an intermediary between internal world (Domain layer) and external World(Insfrastructure/Adapter layer).
On this layer, you handle business logic and rules, transfer data, validation and communication with external systems.

### Data Transfer Objects
Data transfer objects, as the name suggests, they serve as *data carries* between layers of an application.
They do not encapsulate business logic; instead, they simply carry the data from one layer to another.

### Mappers
Serves to transform one type of data in another(e.g, UserDto to UserModel, UserEntity to UserModel).
Very useful, mainly in application that requires constant transformation between layers, I used a lot because I have three entity related classes.

### Services
In this application, 'UserServiceImpl' doesn't contain business logic itself.
Instead, it orchestrates multiple Use Cases implementations, which encapsulate the actual business rules.
This service act delegating operations to the appropriate 'UseCaseImpl' while keeping the layer organized and well-structured.

### Use Cases Interfaces (or Abstracts)
They define the contract for the business operations of the application.
Although it appears that the Use Cases interfaces are pure logic business, they actually represents application-layer level of concern, because they define business process, not business rules.
They don't contain any business logic themselves, but they declare which method that the corresponding Use Case implementation will implement.

### Use Cases Implementation
Responsible for implementing the specific business logic and rules.
Usually call the domain layer to operations related to fetching or saving data.
Transform data as needed (Dto to Domain or vice versa).

### Validation
Serves the purpose of defining and implementing custom validation logic for data that is passed between layers, particularly for DTOs and input data that enters the application from the outside world.

### Exceptions
Nothing special about this, just Custom exceptions extending the BaseException.

## Infrastructure Layer / Adapter Layer (External World)

The Infrastructure Layer, also known as Adapter Layer, is responsible for interacting with external system providing the necessary implementation to connect the application with databases, external APIs, messaging queues or any other external dependencies.

### Adapters (Inbound and Outbound)
Adapters are the components that connect the core application to the external world by implementing the ports.
Adapters, as well as Ports, can be classified as Inbound and Outbound.
- In my point of view, Adapters Inbound and Outbound is an extreme form of Ports Inbound and Outbound. The adapters inbound are usually controllers, which is the extreme external part of an inbound, handling user input and delegating final actions of your business logics. And an Adapter outbound, like repositories, are at the extreme external side of the outbound, interacting with databases or services.

### Configuration
Very useful if your objective is a *dependency-agnostic* domain layer, you can create beans to make the application understand the proper injection.
- A *dependency-agnostic* is a system that typically aims to avoid direct dependencies on external libraries and frameworks that could tie the core business logic to a specific implementation
- When you're doing a *dependency-agnostic* domain layer, you should avoid using JPA annotations as `@Entity`, because it'll expose your domain layer to the external part of the application, rending useless the whole purpose of a Hexagonal Architecture. 
- Although I'm using **Lombok** annotations in my domain layer, don't misunderstand, Lombok doesn't tie itself with business logic of the application, it just serves as a mean to avoid boilerplate coding.

### Persistence
This package holds the persistence related classes and interfaces, like UserEntity with the `@Entity` and UserRepository with `@Repository`.
-If you're planning to make a project with hexagonal architecture in Spring, you cannot avoid those two JPA annotations, the project doesn't even run.

### GlobalExceptionHandler
Literally does as the name suggest, handle exceptions globally across the application. 
