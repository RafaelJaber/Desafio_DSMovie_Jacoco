[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white

<h1 align="center" style="font-weight: bold;">DSMovie 🎬💻</h1>


<div style="text-align: center;">

![java][JAVA_BADGE]
![spring][SPRING_BADGE]

</div>


<p align="center">
 <a href="#tech">Tecnologias</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">Endpoints da API</a> •
 <a href="#colab">Colaboradores</a> •
</p>

<p align="center">
    <b>Projeto criado durante o curso Spring Expert da instituição DevSuperior do professor Nélio Alves.</b>
    <br/>
    <span>Desafio Modulo 5</span>
</p>

<h2 id="tech">💻 Tecnologias</h2>

Este projeto utiliza as seguintes tecnologias e frameworks:

- **Java 21**: Linguagem de programação para o desenvolvimento backend.
- **Spring Framework**: Framework para criação de aplicações Java robustas e escaláveis.
- **Spring Security**: Framework para gerenciamento de segurança.
- **Spring Data JPA**: Abstração de persistência de dados baseada no JPA.
- **H2 MemoryDatabase**: Banco de dados relacional utilizado na aplicação.
- **JUnit**: Ferramenta para gerenciamento de testes.
- **Jacoco**: Ferramenta para monitorar a cobertura dos testes.

<h2 id="started">🚀 Getting started</h2>

<h3>Pré-requisitos</h3>

Antes de começar, você precisará ter os seguintes softwares instalados em sua máquina:

- [Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Git](https://git-scm.com/)

<h3>Clone</h3>

Para clonar o repositório do projeto, execute o seguinte comando no terminal:

```bash
git clone https://github.com/RafaelJaber/Desafio_DSMovie_Jacoco.git
```

<h3>Iniciando o Projeto</h3>


<h3>Rodando a Aplicação</h3>

Para iniciar a aplicação, você pode utilizar uma IDE ou seguir os passos abaixo para compilar e rodar via Maven:

```bash
./mvnw deploy
```

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

<h2 id="routes">📍 Endpoints da API</h2>

| Rota                             | Descrição                                           |
|----------------------------------|-----------------------------------------------------|
| <kbd>POST /oauth2/token</kbd>    | Autenticar usuário                                  |
| <kbd>GET /movies</kbd>           | Buscar filmes - Sem autenticação                    |
| <kbd>GET /movies/{movieId}</kbd> | Buscar filme por id - Sem autenticação              |
| <kbd>POST /movies</kbd>          | Cadastrar filmes - possuir ROLE_ADMIN               |
| <kbd>PUT /movies/{movieId}</kbd> | Atualizar filmes passando o id - possuir ROLE_ADMIN |
| <kbd>DEL /movies/{movieId}</kbd> | Deletar filmes passando o id - possuir ROLE_ADMIN   |
| <kbd>PUT /scores</kbd>           | Adicionar o score do filme - estar autenticado      |


<h2 id="colab">🤝 Colaboradores</h2>

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://github.com/rafaeljaber.png" width="100px;" alt="Rafael Jáber Profile Picture"/><br>
        <sub>
          <b>Rafael Jáber</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://github.com/acenelio.png" width="100px;" alt="Foto Nélio Alves"/><br>
        <sub>
          <b>Nélio Alves</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://github.com/devsuperior.png" width="100px;" alt="Foto DevSuperior"/><br>
        <sub>
          <b>DevSuperior</b>
        </sub>
      </a>
    </td>
  </tr>
</table>