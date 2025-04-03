package Controller;

import Model.AulaParticular;
import Service.AulaParticularService;
import Service.Mensagens;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class AulaParticularController {
	private final AulaParticularService aulaService;
    private final PessoaController pessoaController;


    public AulaParticularController(AulaParticularService aulaService, PessoaController pessoaController) {
        this.aulaService = aulaService;
        this.pessoaController = pessoaController;
    }


    public String agendarAula(AulaParticular aula) {  // Recebe o objeto direto
        return aulaService.insert(aula); // Chama o service
    }

    public String buscarAula(LocalDateTime dataHora, String cpfProfessor) {
        AulaParticular aula = aulaService.get(dataHora, cpfProfessor);
        if (aula != null) {
            return Mensagens.BUSCA_AULA_EXECUTADA +
                    String.format("Data: %s, Valor: R$ %.2f, Local: %s",
                            aula.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                            aula.getValor(), aula.getLocal());
        }
        return Mensagens.AULA_NAO_ENCONTRADA;
    }

    public String cancelarAula(LocalDateTime dataHora, String cpfProfessor) {
        return aulaService.delete(dataHora, cpfProfessor);
    }

    public String removerPorPessoa(String cpf) {
        boolean tryRemover = aulaService.removerAulasPorPessoa(pessoaController.buscaPorCPF(cpf));

        if (tryRemover) {
            return Mensagens.AULA_CANCELADA_SUCESSO;
        }
        return Mensagens.PESSOA_NAO_ENCONTRADA;
    }

    public String listarAulas() {
        List<AulaParticular> tryList = aulaService.list();

        if (tryList != null) {
            StringBuilder lista = new StringBuilder(Mensagens.LISTAR_AULAS + " ");
            for (AulaParticular a : tryList) {
                lista.append(String.format("[ Data: %s, CPF do Professor: %s, CPF do Aluno: %s ]",
                        a.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), a.getCpfProfessor(), a.getCpfAluno()));
                lista.append(", ");
            }
            lista.deleteCharAt(lista.length() - 2);
            return lista.toString();
        }
        return Mensagens.LISTA_AULAS_VAZIA;
    }
}