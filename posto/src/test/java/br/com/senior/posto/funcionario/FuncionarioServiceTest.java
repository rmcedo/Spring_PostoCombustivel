package br.com.senior.posto.funcionario;

import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.enums.Cargo;
import br.com.senior.posto.funcionario.DadosCadastroFuncionario;
import br.com.senior.posto.funcionario.Funcionario;
import br.com.senior.posto.funcionario.FuncionarioRepository;
import br.com.senior.posto.funcionario.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;
    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void deveriaCadastrarFuncionario() {
        DadosCadastroFuncionario dadosFuncionario = this.gerarDadosCadastroFuncionario();
        Funcionario funcionario = new Funcionario(dadosFuncionario);

        when(funcionarioService.cadastrar(dadosFuncionario)).thenReturn(funcionario);

        Funcionario funcionarioComparacao = funcionarioService.cadastrar(dadosFuncionario);

        verify(funcionarioRepository).save(funcionario);
        assertEquals(funcionario.getNome(), funcionarioComparacao.getNome());
    }

    @Test
    public void deveriaTrazerInformaçoesDoFuncionarioPeloId() {
        when(funcionarioRepository.getReferenceById(1L)).thenReturn(this.funcionario);

        DadosVisualizacaoFuncionario detalhesFuncionario = funcionarioService.detalharFuncionario(1L);

        verify(funcionarioRepository).getReferenceById(1L);
        assertEquals(detalhesFuncionario.id(), this.funcionario.getId());
        assertEquals(detalhesFuncionario.nome(), this.funcionario.getNome());
        assertEquals(detalhesFuncionario.cpf(), this.funcionario.getCpf());
    }

    @Test
    public void deveriaListarOsFuncionariosAtivos() {
        Pageable paginacao = Pageable.ofSize(5);
        List<Funcionario> listaFuncionarios = gerarListaFuncionariosAtivos();
        when(funcionarioRepository.findAllByAtivoTrue(paginacao)).thenReturn(gerarPageFuncionarios());

        Page<DadosListagemFuncionario> funcionarios = funcionarioService.listar(paginacao);

        verify(funcionarioRepository).findAllByAtivoTrue(paginacao);
        assertEquals(funcionarios.getSize(), listaFuncionarios.size());
    }

    @Test
    public void deveriaDeletarFuncionario() {
        when(funcionarioRepository.getReferenceById(1L)).thenReturn(this.funcionario);
        funcionarioService.deletar(1L);

        assertEquals(this.funcionario.getAtivo(), false);
    }

    @Test
    public void deveriaAtualizarFuncionario() {
        DadosAtualizacaoFuncionario dados = gerarDadosAtualizacao();

        when(funcionarioRepository.getReferenceById(1L)).thenReturn(this.funcionario);

        Funcionario funcionarioAtualizado = funcionarioService.atualizar(dados);

        verify(funcionario).atualizarInformacoes(gerarDadosAtualizacao());
        assertEquals(funcionario.getId(), funcionarioAtualizado.getId());
        assertEquals(funcionario.getNome(), funcionarioAtualizado.getNome());
        assertEquals(funcionario.getTelefone(), funcionarioAtualizado.getTelefone());
    }

    @Test
    public void deveriaLançarExceçaoAoCadastrarCPFJaCadastrado() {
        DadosCadastroFuncionario dados = gerarDadosCadastroFuncionario();

        when(funcionarioService.cadastrar(dados)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> funcionarioService.cadastrar(dados));
    }

    @Test
    public void deveriaLançarExceoaoAoProcurarIdInexistente() {
        DadosCadastroFuncionario dados = gerarDadosCadastroFuncionario();

        when(funcionarioRepository.findById(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> funcionarioService.detalharFuncionario(1L));
    }

    private DadosCadastroFuncionario gerarDadosCadastroFuncionario() {
        DadosCadastroFuncionario dados =
                new DadosCadastroFuncionario("Marcos",
                        LocalDate.parse("1997-07-21"),
                        "33333333",
                        null,
                        "09866625931",
                        Cargo.FRENTISTA);

        return dados;
    }

    private DadosEndereco gerarDadosEndereco() {
        DadosEndereco dados =
                new DadosEndereco("Carlos Alves",
                        "Boa Vista",
                        "098652323",
                        "Blumenau",
                        "SC",
                        "97",
                        "Apartamento, Bloco B");

        return dados;
    }

    private DadosAtualizacaoFuncionario gerarDadosAtualizacao() {
        DadosAtualizacaoFuncionario dados = new DadosAtualizacaoFuncionario(1L, "Josias",
                LocalDate.parse("2023-01-01"), "44444444", gerarDadosEndereco(), "66666666666", Cargo.CAIXA);

        return dados;
    }


    @Mock
    private Funcionario funcionario = Funcionario.builder()
                .id(1L)
                .ativo(true)
                .cpf("098906505")
                .cargo(Cargo.FRENTISTA)
                .nome("Marcos")
                .endereco(new Endereco(gerarDadosEndereco()))
                .dataContratacao(LocalDate.parse("2022-07-21"))
                .dataNascimento(LocalDate.parse("1997-08-25"))
                .telefone("45649878")
                .build();

    private Funcionario funcionario2 = Funcionario.builder()
            .id(1L)
            .ativo(true)
            .cpf("098906505")
            .cargo(Cargo.FRENTISTA)
            .nome("Joao")
            .dataContratacao(LocalDate.parse("2022-07-21"))
            .dataNascimento(LocalDate.parse("1997-08-25"))
            .telefone("45649878")
            .build();

    private Funcionario funcionario3 = Funcionario.builder()
            .id(1L)
            .ativo(false)
            .cpf("098906505")
            .cargo(Cargo.FRENTISTA)
            .nome("Joao")
            .dataContratacao(LocalDate.parse("2022-07-21"))
            .dataNascimento(LocalDate.parse("1997-08-25"))
            .telefone("45649878")
            .build();

    private List<Funcionario> gerarListaFuncionariosAtivos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.stream().filter(funcionario -> funcionario.getAtivo() == true);

        return funcionarios;
    }

    private Page<Funcionario> gerarPageFuncionarios() {
        Page<Funcionario> funcionarios = new PageImpl<>(gerarListaFuncionariosAtivos());

        return funcionarios;
    }


}
