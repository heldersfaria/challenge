
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <img src="https://scontent.fvag3-1.fna.fbcdn.net/v/t1.6435-9/229406445_4067196366683460_6795799921763648102_n.png?_nc_cat=103&ccb=1-5&_nc_sid=973b4a&_nc_ohc=QdTBsGTTI-IAX-RcqzW&_nc_ht=scontent.fvag3-1.fna&oh=2bb4195662ceccddc0665fc5c756198e&oe=6142019A" alt="Logo" width="auto" height="80">

  <h1 align="center">Desafio Potter Api</h1>

  <p align="center">
      Bem vindo ao desafio "Faça a magia"
  </p>

  <p align="center">
      Este serviço foi desenvolvido para gerenciar personagens do filme Harry Potter.
  </p>

### Tecnologias Utilizadas

* [Java](https://www.java.com/)
* [Maven](https://maven.apache.org/)
* [Spring Framework](https://spring.io/)
* [Springboot](https://spring.io/projects/spring-boot)
* [MongDB](https://www.mongodb.com/)

### Documentação 

- <a href="https://miro.medium.com/max/1400/1*mwXHpdt6CTQHxH78dwc6NA.jpeg"><strong>Swagger</strong></a>

- <a href="https://www.getpostman.com/collections/799c1a99cd73b9e1ee56"><strong>Collection Postman<strong></a>


<!-- ABOUT THE PROJECT -->


## Build Local

### Pré-requisitos

- Java 11 SDK - (https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html)
- Maven 3.0.0

### Collection Postman


### Montar Ambiente Local

1. Clone o repositório seguir.
   ```sh
   git clone git@github.com:heldersfaria/challenge.git
   ```

2. Comandos Maven

- Montar dependencias:

`mvn clean package`

- Executar testes:

`./mvnw verify`

3. Containers Docker:

   <a href="https://docs.docker.com/get-docker/">Guia Instalação Docker</a>

- Crie um container MongoDB:
  ```
  docker run --name mongodb -p 27017:27017 mongo
  ```



