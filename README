# Sistema de Agendamento de Aulas Particulares

Este projeto consiste em um sistema cliente-servidor para cadastro e gerenciamento de aulas particulares. Aplicando os conceitos de socket estudados em aula.

## Estrutura do Projeto

```
.
├── Controller     # Controladores que gerenciam a lógica entre cliente e servidor
│   ├── AulaParticularController.java  # Controla operações relacionadas a aulas particulares
│   ├── PessoaController.java          # Controla operações relacionadas a pessoas
│
├── Model          # Classes que representam as entidades do sistema
│   ├── Aluno.java        # Representa um aluno (//TODO)
│   ├── Professor.java    # Representa um professor (//TODO)
│   ├── Pessoa.java       # Classe base para Aluno e Professor
│   ├── AulaParticular.java # Representa uma aula particular
│
├── Service        # Regras de negócio e manipulação de dados
│   ├── AulaParticularService.java  # Gerencia a lógica das aulas particulares
│   ├── PessoaService.java          # Gerencia operações relacionadas a pessoas
│   ├── Mensagens.java              # Armazena mensagens padronizadas
│   ├── OptCase.java                # Classe auxiliar para operações
│
└── Sockets        # Implementação da comunicação Cliente-Servidor via Sockets
    ├── Cliente.java  # Implementação do cliente que se conecta ao servidor
    ├── Servidor.java # Implementação do servidor que processa as requisições

```

## Requisitos

- Java JDK 19
- Maven

## Instalação

Clone o repositório:
```
git clone https://github.com/jvictorvieira7/DSD_T1.git
cd DSD_T1
```

## Execução

### 01. Compilar o Projeto
```
cd DSD_T1
mvn clean package
```

### 02. Iniciar a aplicação Servidor - Java

```
cd DSD_T1
java -jar target/servidor-jar-with-dependencies.jar
```

### 02. Iniciar a aplicação do Cliente - Java

```
cd DSD_T1
java -jar target/cliente-jar-with-dependencies.jar
```
Após rodar o cliente, siga as instruções no prompt. Caso esteja rodando localmente, utilize "localhost" quando solicitado o IP do servidor.
