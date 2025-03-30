package Model;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa{
	private String especializacao;
	private static List<Professor> professores = new ArrayList<>();

	public Professor(String cpf, String nome, String endereco, String especializacao) {
		super(cpf, nome, endereco);
		this.especializacao = especializacao;
	}

	public String getEspecializacao() {
		return especializacao;
	}

	public void setEspecializacao(String especializacao) {
		this.especializacao = especializacao;
	}

	
	public static String Insert(Professor p) {
		if (buscarPorCpf(p.getCpf()) != null) {
			return "Erro: Professor com este CPF já existe";
		}
		professores.add(p);
		//Pessoa.Insert(p); //  insere em pessoa
		return "Professor inserido com sucesso";
	}

	public static Professor Get(String cpf) {
		return buscarPorCpf(cpf);
	}

/*ublic static List<Professor> List() {
		return new ArrayList<>(professores);  
	}*/

	private static Professor buscarPorCpf(String cpf) {
		for (Professor p : professores) {
			if (p.getCpf().equals(cpf)) {
				return p;
			}
		}
		return null;
	}
}
