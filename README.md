# Pós-Tech-FIAP/ALURA-Fase03

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Criar um sistema de reserva e avaliação de restaurantes.

## Requisitos:
1. Cadastro de restaurantes:  Os restaurantes podem se cadastrar no sistema, fornecendo informações como nome, localização, tipo de cozinha, horários de funcionamento e capacidade.
2. Reserva de mesas:  Os usuários podem fazer reservas para datas e horários específicos.
3. Avaliações e comentários:  Após a visita, os usuários podem avaliar o restaurante e deixar comentários sobre sua experiência.
4. Busca de restaurantes:  Os usuários podem buscar restaurantes por nome, localização ou tipo de cozinha.
5. Gerenciamento de reservas:  Os restaurantes podem gerenciar as reservas, visualizando e atualizando o status das mesas.

## Entregáveis:
1. Link do Github com o código fonte dos serviços desenvolvidos.
2. Documentação técnica (pode ser em JavaDoc, Swagger, etc).
3. Um relatório técnico descrevendo as tecnologias e ferramentas utilizadas, os desafios encontrados durante o desenvolvimento e as soluções implementadas para resolvê-las.

# Tecnologias utilizadas
1. Java 17
2. Spring Boot 3.2.2
3. Spring Web MVC 
4. Spring Data JPA 
5. Spring Bean Validation 
6. Spring Doc Open API 2.3.0
7. Lombok 
8. Postgres 15.1
9. Flyway 
10. JUnit 5
11. Mockito
12. Rest Assured
13. TestContainers
14. Docker

# Setup do Projeto

Para realizar o setup do projeto é necessário possuir o Java 17, docker 24 e docker-compose 1.29 instalado em sua máquina.
Faca o download do projeto (https://github.com/EvolutionTeamFiapAluraPostech/fiapAluraTechChallengeFase03) e atualize suas dependências com o gradle.
Antes de iniciar o projeto é necessário criar o banco de dados. O banco de dados está programado para ser criado em um container. 
Para criar o container, execute o docker-compose.
Acesse a pasta raiz do projeto, no mesmo local onde encontra-se o arquivo docker-compose.yml. Para executá-lo, execute o comando docker-compose up -d (para rodar detached e não prender o terminal).
Para iniciar o projeto, basta executar o Spring Boot Run no IntelliJ.
Após a inicialização do projeto, será necessário se autenticar, pois o Spring Security está habilitado. Para tanto, utilize o Postman (ou outra aplicação de sua preferência), crie um endpoint para realizar a autenticação, com a seguinte url **localhost:8080/authenticate**. No body, inclua um json contendo o atributo “email” com o valor “thomas.anderson@itcompany.com” e outro atributo “password” com o valor “@Bcd1234”. Realize a requisição para este endpoint para se obter o token JWT que deverá ser utilizado para consumir os demais endpoints do projeto.
Segue abaixo instruções do endpoint para se autenticar na aplicação.

POST /authenticate HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 76

{
"email": "thomas.anderson@itcompany.com",
"password": "@Bcd1234"
}

# Collection do Postman
* Marcelo-RM350802-Fiap-Alura-Arq-Dev-Java-Tech-Challenge-Fase-03.postman_collection.json
* Esta collection está salva na raiz do projeto.

# Environments do Postman
* Cloud
    * Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase03.postman_cloud_environment.json
* Local dev
    * Marcelo-RM350802-Fiap-Alura-Tech Challenge-Fase03.postman_dev_environment
* Estas environments estão salvas na raiz do projeto.

# Documentação da API
* Cloud
    * https://fiaprestaurant-tc03.onrender.com/swagger-ui/index.html#/
* Local dev
  * http://localhost:8080/swagger-ui/index.html

# Documentação do PROJETO
* PDF

