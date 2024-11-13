Este projeto faz uso da API OMDB para buscar informações sobre séries e episódios.

## Pré-requisitos

- Java 17
- PostgreSQL
- Uma chave de API da OMDB
- Uma chave da API da OpenAI (opcional)

## Variaveis de ambiente necessárias

- `OMDB_API_KEY`: Sua chave de API da OMDB.
- `OPENAI_API_KEY`: Sua chave de API do OpenAI. (opcional)
- `DB_HOST`: Host do banco de dados.
- `DB_PORT`: Porta do banco de dados.
- `DB_USER`: Usuário do banco de dados.
- `DB_PASSWORD`: Senha do banco de dados.
- `DB_NAME`: Nome do banco de dados.

## Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/vitoriasilva13/JavaMavenOmdb.git
    cd JavaMavenOmdb
    ```

2. Crie um banco de dados no PostgreSQL:
    ```sql
   //Lembre-se de definir a variável de ambiente 'DB_NAME' com o mesmo nome
    CREATE DATABASE db_name;
    ```

3. Gere as chaves de API
   - Gere uma chave de API na página [OMDB API](https://www.omdbapi.com/apikey.aspx) (API necessária para o funcionamento do programa).
   - Gere uma chave de API na página [OpenAI API](https://platform.openai.com/settings/) (API opcional para tradução de sinopses).


4. Execute o arquivo `Main.java`.
