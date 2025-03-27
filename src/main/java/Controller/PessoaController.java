package Controller;

import Model.Pessoa;
import Service.Mensagens;
import Service.PessoaService;
import Service.UpdateCase;

import java.util.ArrayList;
import java.util.List;

public class PessoaController {

 private final PessoaService pessoaService;


 public PessoaController(PessoaService pessoaService) {
     this.pessoaService = pessoaService;
 }


    public String insertPessoa(Pessoa p) {
        boolean tryInsert = pessoaService.insertDB(p);

        if (tryInsert) {
            return Mensagens.PESSOA_INSERIDA_SUCESSO;
        } else {
            return Mensagens.PESSOA_JA_EXISTE;
        }
    }


    public String updatePessoa(Pessoa p, int reg) {
        UpdateCase tryUpdate = pessoaService.updateDB(p, reg);

        switch (tryUpdate) {
            case SUCESSO:
                return Mensagens.PESSOA_ATUALIZADA_SUCESSO;
            case PESSOA_NAO_ENCONTRADA:
                return Mensagens.PESSOA_NAO_ENCONTRADA;
            case OPCAO_INVALIDA:
                return Mensagens.OPCAO_INVALIDA;
            default:
                return "Erro desconhecido";
        }
    }

    public static Pessoa Get(String cpf) {
        return buscarPorCpf(cpf);
    }

    public static String Delete(String cpf) {
        Pessoa pessoa = buscarPorCpf(cpf);
        if (pessoa == null) {
            return "Erro: Pessoa não encontrada";
        }
        pessoas.remove(pessoa);
        return "Pessoa removida com sucesso";
    }

    public static List<Pessoa> List() {
        return new ArrayList<>(pessoas);
    }


}
