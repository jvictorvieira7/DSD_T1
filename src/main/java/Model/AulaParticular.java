package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AulaParticular {
    private LocalDateTime dataHora;
    private double valor;
    private String local;
    private Professor professor;
    private Aluno aluno;
    private static List<AulaParticular> aulas = new ArrayList<>();

    public AulaParticular(LocalDateTime dataHora, double valor, String local, 
                         Professor professor, Aluno aluno) {
        this.dataHora = dataHora;
        this.valor = valor;
        this.local = local;
        this.professor = professor;
        this.aluno = aluno;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getValor() {
        return valor;
    }

    public String getLocal() {
        return local;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    // CRUD
    public static String Insert(AulaParticular aula) {
        if (professorJaTemAulaNoHorario(aula.getProfessor(), aula.getDataHora())) {
            return "Erro: Professor já tem aula neste horário";
        }
        aulas.add(aula);
        return "Aula agendada com sucesso";
    }

    public static AulaParticular Get(LocalDateTime dataHora, String cpfProfessor) {
        for (AulaParticular a : aulas) {
            if (a.getDataHora().equals(dataHora) && 
                a.getProfessor().getCpf().equals(cpfProfessor)) {
                return a;
            }
        }
        return null;
    }

    public static String Delete(LocalDateTime dataHora, String cpfProfessor) {
        AulaParticular aula = Get(dataHora, cpfProfessor);
        if (aula == null) {
            return "Erro: Aula não encontrada";
        }
        aulas.remove(aula);
        return "Aula cancelada com sucesso";
    }

    public static List<AulaParticular> List() {
        return new ArrayList<>(aulas);
    }

    private static boolean professorJaTemAulaNoHorario(Professor professor, LocalDateTime dataHora) {
        for (AulaParticular a : aulas) {
            if (a.getProfessor().equals(professor) && a.getDataHora().equals(dataHora)) {
                return true;
            }
        }
        return false;
    }
}