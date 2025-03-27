package Service;

import Model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaService {

    private static List<Pessoa> pessoas = new ArrayList<>();


    public boolean insertDB(Pessoa p) {
        if (buscarPorCpf(p.getCpf()) != null) {
            return false; // CPF já cadastrado
        }
        pessoas.add(p);
        return true; // Retorna true se a pessoa for adicionada com sucesso
    }

    public UpdateCase updateDB(Pessoa p, int reg) {
        // Verifica se a pessoa já existe pelo CPF
        Pessoa pessoaCadastrada = buscarPorCpf(p.getCpf());
        if (pessoaCadastrada == null) {
            return UpdateCase.PESSOA_NAO_ENCONTRADA;  // Pessoa não encontrada
        }

        // Realiza a atualização com base na opção 'reg'
        switch (reg) {
            case 1:
                pessoaCadastrada.setNome(p.getNome());
                break;
            case 2:
                pessoaCadastrada.setEndereco(p.getEndereco());
                break;
            default:
                return UpdateCase.OPCAO_INVALIDA;  // Opção inválida
        }

        return UpdateCase.SUCESSO;  // Atualização bem-sucedida
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

    public static List<Pessoa> list() {
        return new ArrayList<>(pessoas);
    }

    private Pessoa buscarPorCpf(String cpf) {
        for (Pessoa p : pessoas) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }











}
