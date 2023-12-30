# Microsserviços APIs REST

# Sobre o projeto

 Esse projeto spring contém microsserviços que são APIs REST e nelas são realizados TESTES UNITÁRIOS JUnit    
 

# Tecnologias utilizadas
## Back end
- Java
- Maven 
- Spring Boot

## Front end
- Eureka Server
- Swagger

## Banco de Dados Utilizado
- MongoDB

## Banco de dados utilizado para os Testes Unitários
- EmbeddedMongo

# Como executar o projeto

## Back end
Pré-requisitos: Java, MongoDB

```bash
# Clonar repositório

# Executar primeiro o Eureka Server que está na pasta EUREKA-SERVER

# Executar um microsserviço por exemplo o que está na pasta PRODUTO-CLIENT 

# Executar o outro microsserviço que está na pasta PRODUTO-CLI

# Executar o balanceador que está na pasta api-gateway

```

# Como acessar o Swagger nos microsserviços
## Para acessar o Swagger do microsserviço que está na pasta PRODUTO-CLIENT após executar o projeto acesse o endereço 
- http://localhost:8091/swagger-ui/index.html#/
## Para acessar o Swagger do microsserviço que está na pasta PRODUTO-CLI após executar o projeto acesse o endereço
- http://localhost:8092/swagger-ui/index.html#/


## Com o Swagger podemos ver as requisições que podemos realizar 

![Layout Geral](https://github.com/douglasonline/Imagens/blob/master/Layout_Geral.png) 


## Demonstração dos Testes Unitários

- Testes Unitários da classe ProdutoController

- Método create
![Metodo Create](https://github.com/douglasonline/Imagens/blob/master/Metodo_Create.png)

- Método getAll
![Metodo GetAll](https://github.com/douglasonline/Imagens/blob/master/Metodo_GetAll.png)

- Método update
![Metodo Update](https://github.com/douglasonline/Imagens/blob/master/Metodo_Update.png)

- Método getById
![Metodo GetById](https://github.com/douglasonline/Imagens/blob/master/Metodo_GetById.png)

- Método deleteById
![Metodo DeleteById](https://github.com/douglasonline/Imagens/blob/master/Metodo_DeleteById.png)
  

## Como consumir o projeto

Estou utilizando o Postman para consumir a aplicação

- Para cadastra um produto utilizamos o balanceador
Para acessar o balanceador utilizamos o endereço
- http://localhost:9090/api/produto

![Cadastrar Produto](https://github.com/douglasonline/Imagens/blob/master/Cadastrar_Produto.png)

- Para buscar os produtos também utilizamos o balanceador

![Buscar Produto](https://github.com/douglasonline/Imagens/blob/master/Buscar_Produto.png)

- Para buscar o produto pelo id também utilizamos o balanceador   

![Buscar Por Id](https://github.com/douglasonline/Imagens/blob/master/Buscar_Por_Id.png)

- Para atualizar o produto também utilizamos o balanceador     

![Atualizar Produto](https://github.com/douglasonline/Imagens/blob/master/Atualizar_Produto.png)

- Para deleta o produto também utilizamos o balanceador

![Deletar Produto](https://github.com/douglasonline/Imagens/blob/master/Deletar_Produto.png)

# Autor

Douglas

https://www.linkedin.com/in/douglas-j-b2194a232/

