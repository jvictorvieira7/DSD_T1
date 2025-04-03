package Service;

public class Mensagens {

    public static final String PESSOA_ATUALIZADA_SUCESSO = "Pessoa atualizada com sucesso.";

    public static final String PESSOA_NAO_ENCONTRADA = "Erro: Pessoa não encontrada.";

    public static final String OPCAO_INVALIDA = "Erro: Opção inválida.";

    public static final String PESSOA_JA_EXISTE = "Erro: Pessoa com este CPF já existe.";

    public static final String PESSOA_INSERIDA_SUCESSO = "Pessoa inserida com sucesso.";

    public static final String BUSCA_EXECUTADA = "Pessoa encontrada: ";

    public static final String PESSOA_DELETADA = "Pessoa deletada: ";

    public static final String LISTAR_PESSOAS = "Quantidade de pessoas cadastradas: ";

    public static final String LISTA_VAZIA = "Nenhuma pessoa cadastrada no sistema.";

    public static final String TIMEOUT = "Tempo limite excedido. Encerrando conexão.";

    public static final String CONEXAO_CLIENTE_CLOSE = "Conexão com o servidor encerrada.";

    public static final String SERVER_STARTED = "Servidor iniciado na porta: ";
    
    public static final String AULA_AGENDADA_SUCESSO = "Aula agendada com sucesso!";
    
    public static final String PROFESSOR_HORARIO_OCUPADO = "Erro: Professor já tem aula neste horário.";
    
    public static final String AULA_NAO_ENCONTRADA = "Aula não encontrada.";
    
    public static final String AULA_CANCELADA_SUCESSO = "Aula cancelada com sucesso.";
    
    public static final String LISTA_AULAS_VAZIA = "Nenhuma aula agendada.";
    
    public static final String BUSCA_AULA_EXECUTADA = "Aula encontrada: ";
    
    public static final String LISTAR_AULAS = "Aulas agendadas: ";

    public static final String SINTAXE = """
            ===== SINTAXE DE COMANDOS =====
            
            PESSOAS:
              INSERT;PESSOA;CPF;NOME;ENDERECO
              GET;PESSOA;CPF
              UPDATE;PESSOA;CPF;NOVO_NOME;NOVO_ENDERECO
              UPDATE;ENDERECO;CPF;NOVO_ENDERECO
              DELETE;PESSOA;CPF
              LIST;PESSOA
            
            
            AULAS:
              INSERT;AULA;DATA(dd/MM/yyyy HH:mm);VALOR;CPF_PROFESSOR;CPF_ALUNO;LOCAL
              GET;AULA;DATA;CPF_PROFESSOR
              DELETE;AULA;DATA;CPF_PROFESSOR
              LIST;AULA
            
            Digite SAIR para encerrar
            ============================
            """;


}


/*
/PROFESSORES:
              INSERT;PROFESSOR;CPF;NOME;ENDERECO;ESPECIALIZACAO
              GET;PROFESSOR;CPF
              DELETE;PROFESSOR;CPF
              LIST;PROFESSOR

ALUNOS:
              INSERT;ALUNO;CPF;NOME;ENDERECO;MATRICULA
              GET;ALUNO;CPF
              DELETE;ALUNO;CPF
              LIST;ALUNO
 */