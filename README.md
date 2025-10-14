# <div align="center">ConsultorHub</div>

## O que é?
A aplicação **ConsultorHub** é uma solução que visa centralizar o gerenciamento de apólices e seguradoras para consultores independentes, facilitando a administração de seus respectivos dados.

## Dependências:
O **ConsultorHub** possui as dependências listadas à seguir:

### Swagger

* Para acessar o Swagger e visualizar todas as rotas da API, insira a URL à seguir no seu navegador: <a href="http://127.0.0.1:8080/swagger-ui/index.html">`http://127.0.0.1:8080/swagger-ui/index.html`</a> 

## Como executar o projeto?

### 1. Configuração do Banco de Dados
Para configurar o banco de dados(PostgreSQL), vá até o módulo `src/main/resources/` e insira os seguintes parâmetros no arquivo `application.properties`:

````
spring.datasource.username=<username_do_banco_de_dados> #OBS: Pode-se deixar esse valor igual à postgres
spring.datasource.password=<senha_do_postgreSQL> # Essa senha é a que você criou quando instalou o PostgreSQL na sua máquina.
````

### 2. Ligar a API:
Para executar a API, vá até o módulo `src/main/java/com/consultorhub/backend` e execute a classe `BackendApplication.java`.