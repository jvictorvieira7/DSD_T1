package Model;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {

	private String cpf;
	private String nome;
	private String endereco;
	private static List<Pessoa> pessoas = new ArrayList<>();

	public Pessoa(String cpf, String nome, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	 public static String Insert(Pessoa p) {
	        if (buscarPorCpf(p.getCpf()) != null) {
	            return "Erro: Pessoa com este CPF já existe";
	        }
	        pessoas.add(p);
	        return "Pessoa inserida com sucesso";
	    }

	    public static String Update(Pessoa p, int reg) {
	        Pessoa pessoaExistente = buscarPorCpf(p.getCpf());
	        if (pessoaExistente == null) {
	            return "Erro: Pessoa não encontrada"; 
	        }

	        switch (reg) {
	            case 1:
	                pessoaExistente.setNome(p.getNome());
	                break; 
	            case 2:
	                pessoaExistente.setEndereco(p.getEndereco());
	                break;
	            default:
	                return "Erro: Opção inválida";
	        }
	        return "Pessoa atualizada com sucesso";
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

	    private static Pessoa buscarPorCpf(String cpf) {
	        for (Pessoa p : pessoas) {
	            if (p.getCpf().equals(cpf)) {
	                return p;
	            }
	        }
	        return null;
	    }
	}
