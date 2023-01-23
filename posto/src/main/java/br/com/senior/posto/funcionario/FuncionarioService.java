package br.com.senior.posto.funcionario;

import br.com.senior.posto.enums.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario cadastrar(DadosCadastroFuncionario dadosCadastroFuncionario) {
        if (funcionarioRepository.existsByCpf(dadosCadastroFuncionario.cpf())) {
            throw new IllegalArgumentException("CPF ja cadastrado!");
        }
        Funcionario funcionario = new Funcionario(dadosCadastroFuncionario);
        funcionarioRepository.save(funcionario);

        return funcionario;
    }

    public DadosVisualizacaoFuncionario detalharFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario não encontrado"));

        return new DadosVisualizacaoFuncionario(funcionario);
    }

    public void deletar(Long id) {
        Funcionario funcionario = funcionarioRepository.getReferenceById(id);
        funcionario.excluir();
    }

    public Page<DadosListagemFuncionario> listar(@PageableDefault Pageable paginacao) {
        return funcionarioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemFuncionario::new);
    }

    public Funcionario atualizar(DadosAtualizacaoFuncionario dados) {
        Funcionario funcionario = funcionarioRepository.getReferenceById(dados.id());
        funcionario.atualizarInformacoes(dados);

        return funcionario;
    }

    public Page<Funcionario> buscarPorNome(String nome, Pageable paginacao) {

        return funcionarioRepository.findByNome(nome, paginacao);
    }

    public Page<Funcionario> buscarPorDataContratacao(String dataContratacao, Pageable paginacao) {
        LocalDate data = LocalDate.parse(dataContratacao);
        
        return funcionarioRepository.findByDataContratacao(data, paginacao);
    }

    public Page<Funcionario> buscarPorDataContratacaoEmDiante(String dataContratacao, Pageable paginacao) {
        LocalDate data = LocalDate.parse(dataContratacao);

        return funcionarioRepository.findByDataContratacaoEmDiante(data, paginacao);
    }

    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }

    public Page<Funcionario> buscarPorCargo(String cargo, Pageable paginacao) {
        cargo = cargo.toUpperCase();

        return funcionarioRepository.findByCargo(cargo, paginacao);
    }

    public Funcionario buscarFuncionarioComMaiorNumeroDeRegistros() {

        return funcionarioRepository.findFuncionarioComMaiorNumeroDeRegistros();
    }
}
