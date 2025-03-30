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

    public String updatePessoa(Pessoa p, int opt) {
        UpdateCase tryUpdate = pessoaService.updateDB(p, opt);

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



    public String Get(String cpf) {
     Pessoa pessoa = pessoaService.getDB(cpf);

     if (pessoa != null) {
         return Mensagens.BUSCA_EXECUTADA + pessoa.toString();
     }
        return Mensagens.PESSOA_NAO_ENCONTRADA;
    }

    public String Delete(Pessoa p) {
    	boolean tryDelete = pessoaService.DeleteDB(p);

        if (tryDelete) {
            return Mensagens.PESSOA_REMOVIDA_SUCESSO;
        } else {
            return Mensagens.PESSOA_NAO_ENCONTRADA;
        }
    }

    public List<Pessoa> List() {
        return pessoaService.listDB();
    }


}
