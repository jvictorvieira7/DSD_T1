package Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Model.AulaParticular;
import Model.Pessoa;

public class AulaParticularService {
	  private final PessoaService pessoaService;

	    private static List<AulaParticular> aulas = new ArrayList<>();


	    public AulaParticularService(PessoaService pessoaService) {
	        this.pessoaService = pessoaService;
	    }

    public String insert(AulaParticular aula) {
        // 1. Valida se professor existe
        if (pessoaService.getDB(aula.getCpfProfessor()) == null) {
            return "Erro: Professor não cadastrado";
        }

        // 2. Valida se aluno existe
        if (pessoaService.getDB(aula.getCpfAluno()) == null) {
            return "Erro: Aluno não cadastrado";
        }

        // 3. Verifica conflito de horário
        if (professorJaTemAulaNoHorario(aula.getCpfProfessor(), aula.getDataHora())) {
            return Mensagens.PROFESSOR_HORARIO_OCUPADO;
        }

        aulas.add(aula);
        return Mensagens.AULA_AGENDADA_SUCESSO;
    }

    public AulaParticular get(LocalDateTime dataHora, String cpfProfessor) {
        for (AulaParticular a : aulas) {
            if (a.getDataHora().equals(dataHora) && 
                a.getCpfProfessor().equals(cpfProfessor)) {
                return a;
            }
        }
        return null;
    }

    public String delete(LocalDateTime dataHora, String cpfProfessor) {
        AulaParticular aula = get(dataHora, cpfProfessor);
        if (aula == null) {
            return Mensagens.AULA_NAO_ENCONTRADA;
        }
        aulas.remove(aula);
        return Mensagens.AULA_CANCELADA_SUCESSO;
    }

    public boolean removerAulasPorPessoa(Pessoa pessoa) {
        if (pessoa == null) {
            return false;
        } else {
            aulas.removeIf(aula -> aula.getCpfAluno().equals(pessoa.getCpf()) || aula.getCpfProfessor().equals(pessoa.getCpf()));
            return true;
        }
    }


    public List<AulaParticular> list() {
        if (aulas.isEmpty()) {
            return null;
        }
        return new ArrayList<>(aulas);
    }

    private boolean professorJaTemAulaNoHorario(String cpfProfessor, LocalDateTime dataHora) {
        for (AulaParticular a : aulas) {
            if (a.getCpfProfessor().equals(cpfProfessor) && a.getDataHora().equals(dataHora)) {
                return true;
            }
        }
        return false;
    }
}