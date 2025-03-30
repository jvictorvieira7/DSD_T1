package Service;

import Model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaService {

    public PessoaService() {
    }

    private static List<Pessoa> pessoaList = new ArrayList<>();


    public boolean insertDB(Pessoa p) {
        if (buscarPorCpf(p.getCpf()) != null) {
            return false; //CPF já cadastrado
        }
        pessoaList.add(p);
        return true; //Retorna true se a pessoa for adicionada com sucesso
    }

    public OptCase updateDB(Pessoa p, int opt) {
        //Verifica se a pessoa já existe pelo CPF
        Pessoa pessoaCadastrada = buscarPorCpf(p.getCpf());
        if (pessoaCadastrada == null) {
            return OptCase.CASE_PESSOA_NAO_ENCONTRADA;  //Pessoa não encontrada
        }

        //Realiza a atualização com base na opção 'opt'
        switch (opt) {
            case 1:
                pessoaCadastrada.setNome(p.getNome());
                break;
            case 2:
                pessoaCadastrada.setEndereco(p.getEndereco());
                break;
            default:
                return OptCase.CASE_OPCAO_INVALIDA;  // Opção inválida
        }
        return OptCase.CASE_SUCESSO;  //Atualização bem-sucedida
    }


    public Pessoa getDB(String cpf) {
        Pessoa pessoaCadastrada = buscarPorCpf(cpf);
        if (pessoaCadastrada == null) {
            return null;  //Pessoa não encontrada
        }
        return pessoaCadastrada;
    }

    public boolean deleteDB(String cpf) {
        Pessoa pessoaCadastrada = buscarPorCpf(cpf);
        if (pessoaCadastrada == null) {
            return false;
        }
        pessoaList.remove(pessoaCadastrada);
        return true;
    }

    public List<Pessoa> listDB() {
        if (pessoaList.isEmpty()) {
            return null;
        }
        return new ArrayList<>(pessoaList);
    }

    private Pessoa buscarPorCpf(String cpf) {
        for (Pessoa p : pessoaList) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }











}
