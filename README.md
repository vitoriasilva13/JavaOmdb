Este projeto faz uso da API OMDB para buscar informações sobre séries e episódios.

## Pré-requisitos

- Java 11 ou superior
- Uma chave de API da OMDB

## Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/vitoriasilva13/JavaMavenOmdb.git
    cd seu-repositorio
    ```

2. Gere uma chave de API na página [OMDB API](https://www.omdbapi.com/apikey.aspx).

3. Adicione a chave de API no arquivo `Main.java`:
    ```java
    private final String API_KEY = "&apikey={sua_api_key}";
    ```
4. Execute o arquivo `Main.java`.
