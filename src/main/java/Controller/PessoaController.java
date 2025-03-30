package Controller;

import Model.Pessoa;
import Service.Mensagens;
import Service.PessoaService;
import Service.OptCase;

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
        OptCase tryUpdate = pessoaService.updateDB(p, opt);

        switch (tryUpdate) {
            case CASE_SUCESSO:
                return Mensagens.PESSOA_ATUALIZADA_SUCESSO;
            case CASE_PESSOA_NAO_ENCONTRADA:
                return Mensagens.PESSOA_NAO_ENCONTRADA;
            case CASE_OPCAO_INVALIDA:
                return Mensagens.OPCAO_INVALIDA;
            default:
                return "Erro desconhecido";
        }
    }



    public String get(String cpf) {
     Pessoa tryGet = pessoaService.getDB(cpf);

     if (tryGet != null) {
         return Mensagens.BUSCA_EXECUTADA + tryGet.getCpf() + tryGet.getNome();
     }
        return Mensagens.PESSOA_NAO_ENCONTRADA;
    }

    public String delete(String cpf) {
        boolean tryDelete = pessoaService.deleteDB(cpf);

        if (tryDelete) {
            return Mensagens.PESSOA_DELETADA + cpf;
        } else {
            return Mensagens.PESSOA_NAO_ENCONTRADA;
        }
    }

    public String list() {
     List<Pessoa> tryList = pessoaService.listDB();

     if (tryList != null) {

         StringBuilder lista = new StringBuilder(Mensagens.LISTAR_PESSOAS + "\n");
         for (Pessoa p : tryList) {
             lista.append(String.format("CPF: %s, Nome: %s, Endereço: %s%n", p.getCpf(), p.getNome(), p.getEndereco()));
         }
         return lista.toString();
     }
        return Mensagens.LISTA_VAZIA;
    }


}
