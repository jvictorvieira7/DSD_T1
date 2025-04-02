package Controller;

import Model.AulaParticular;
import Model.Pessoa;
import Service.AulaParticularService;
import Service.Mensagens;
import java.time.LocalDateTime;
import java.util.List;

public class AulaParticularController {
	private final AulaParticularService aulaService;

    public AulaParticularController(AulaParticularService aulaService) {
        this.aulaService = aulaService;
    }

    public String agendarAula(AulaParticular aula) {  // Recebe o objeto direto
        return aulaService.insert(aula); // Chama o service
    }

    public String buscarAula(LocalDateTime dataHora, String cpfProfessor) {
        AulaParticular aula = aulaService.get(dataHora, cpfProfessor);
        if (aula != null) {
            return Mensagens.BUSCA_AULA_EXECUTADA + 
                   String.format("Data: %s, Valor: R$%.2f, Local: %s",
                   aula.getDataHora(), aula.getValor(), aula.getLocal());
        }
        return Mensagens.AULA_NAO_ENCONTRADA;
    }

    public String cancelarAula(LocalDateTime dataHora, String cpfProfessor) {
        return aulaService.delete(dataHora, cpfProfessor);
    }

    public String listarAulas() {
        List<AulaParticular> tryList = aulaService.list();

        if (tryList != null) {
            StringBuilder lista = new StringBuilder(Mensagens.LISTAR_AULAS + " ");
            for (AulaParticular a : tryList) {
                lista.append(String.format("[ Data: %s, CPF do Professor: %s, CPF do Aluno: %s ]", 
                        a.getDataHora(), a.getCpfProfessor(), a.getCpfAluno()));
                lista.append(", ");
            }
            lista.deleteCharAt(lista.length() - 2);
            return lista.toString();
        }
        return Mensagens.LISTA_AULAS_VAZIA;
    }
}