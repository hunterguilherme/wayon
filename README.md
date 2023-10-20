
# Transferencia-API
Api  de controle de transferencias de um banco. A aplicação é versionada com jdk11.0.8, usando db h2. 
Para fins de Teste o arquivo de migration vem com  clientes cadastrados, já que é necessario uma conta valida para efetuar transferencia.
## API 
- [x] Cadastra Transferencia
- [x] Busca Extrato
- [x] Front em Vue(https://github.com/hunterguilherme/wayonFront)
- [x] Testes unitarios

A aplicação 
## Instalação
Rode o comando abaixo para instalar as dependecnias

``` mvn clean package install ```

Rode o comando abaixo para rodar os testes

``` mvn verify ``` 

execute o comando abaixo no terminal na raiz do projeto

```java -jar target/transferencia-api.jar```

## Documentação
Acesse o link abaixo para verificar a documentação
http://localhost:8080/swagger-ui.html#/