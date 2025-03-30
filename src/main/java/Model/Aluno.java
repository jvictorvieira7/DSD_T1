package Model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private String matricula;
    private static List<Aluno> alunos = new ArrayList<>();

    public Aluno(String cpf, String nome, String endereco, String matricula) {
        super(cpf, nome, endereco);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    // CRUD específico para Aluno
    public static String Insert(Aluno a) {
        if (buscarPorCpf(a.getCpf()) != null) {
            return "Erro: Aluno com este CPF já existe";
        }
        alunos.add(a);
        //Pessoa.Insert(a); // Também insere como Pessoa
        return "Aluno inserido com sucesso";
    }

    public static Aluno Get(String cpf) {
        return buscarPorCpf(cpf);
    }

   /* public static List<Aluno> List() {
        return new ArrayList<>(alunos);
    }*/

    private static Aluno buscarPorCpf(String cpf) {
        for (Aluno a : alunos) {
            if (a.getCpf().equals(cpf)) {
                return a;
            }
        }
        return null;
    }
}