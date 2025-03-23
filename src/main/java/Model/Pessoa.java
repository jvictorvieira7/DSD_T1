package Model;

public class Pessoa {

    private String cpf;
    private String nome;
    private String endereco;


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


    public void Insert(Pessoa p) {
        Pessoa novaPessoa = new Pessoa(p.getCpf(), p.getNome(), p.getEndereco());
    }

    public void Update(Pessoa p, int reg) {
        if (p.getCpf().equals(cpf)) {
            switch (reg) {
                case 1:
                    nome = p.getNome();
                    break;
                case 2:
                    endereco = p.getEndereco();
                    break;
                default:
                    //TODO mensagem
            }
        } else {
            //TODO mensagem
        }
    }

    public void Get(String cpf) {
        //TODO
    }

    public void Delete(String cpf) {
        //TODO
    }

    public void List(){
        //TODO
    }



}
