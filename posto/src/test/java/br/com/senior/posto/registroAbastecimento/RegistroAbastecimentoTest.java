package br.com.senior.posto.registroAbastecimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.senior.posto.bomba.Bomba;
import br.com.senior.posto.cliente.Cliente;
import br.com.senior.posto.endereco.DadosEndereco;
import br.com.senior.posto.endereco.Endereco;
import br.com.senior.posto.enums.Cargo;
import br.com.senior.posto.enums.FormaPagamento;
import br.com.senior.posto.enums.TipoCombustivel;
import br.com.senior.posto.fornecedor.Fornecedor;
import br.com.senior.posto.funcionario.Funcionario;
import br.com.senior.posto.reservatorio.Reservatorio;

@ExtendWith(MockitoExtension.class)
public class RegistroAbastecimentoTest {

    @Mock
    private RegistroAbastecimentoRepository repository;
    @InjectMocks
    private RegistroAbastecimentoService service;

    @Test
    void deveriaCalcularOValorDaGasolinaNormalComDesconto() {

        var valor = criarRegistro1().calcularValor();

        assertEquals(new BigDecimal("45.0"), valor);
    }

    @Test
    void deveriaCalcularOValorDaGasolinaNormalSemDesconto() {

        var valor = criarRegistro3().calcularValor();

        assertEquals(new BigDecimal("50"), valor);
    }

    @Test
    void deveriaCalcularOValorDaGasolinaditivadaSemDesconto() {

        var valor = criarRegistro4().calcularValor();

        assertEquals(new BigDecimal("51.50"), valor);
    }

    @Test
    void deveriaCalcularOValorDaGasolinaditivadaComDesconto() {

        var valor = criarRegistro2().calcularValor();

        assertEquals(new BigDecimal("46.350"), valor);
    }

    @Test
    void deveriaRetornarONumeroDeRegistrosNoSistema() {
        List<RegistroAbastecimento> registros = new ArrayList<>();
        registros.add(criarRegistro1());
        registros.add(criarRegistro2());
        registros.add(criarRegistro3());
        registros.add(criarRegistro4());
        when(repository.countRegistroAbastecimentos()).thenReturn(registros.size());
        int resultado = service.contarTodosOsRegistros();
        assertEquals(registros.size(), resultado);
        verify(repository, times(1)).countRegistroAbastecimentos();
    }
    
    @Test
    void deveriaRetornarValorTotalDasVendas() {
        when(repository.countValorTotalDosAbastecimentos()).thenReturn(192.85);
        var resultado = service.contarValorTotalDosAbastecimentos();
        assertEquals(192.85, resultado);
    }
    

    public RegistroAbastecimento criarRegistro1() {
        return RegistroAbastecimento.builder().funcionario(gerarFuncionario).cliente(criarCliente()).bomba(gerarBomba1).dataHora(LocalDateTime.parse("2023-01-20T10:21:00")).litrosAbastecidos(BigDecimal.valueOf(10)).formaPagamento(FormaPagamento.PIX).idAtendimento(1L).build();
    }

    public RegistroAbastecimento criarRegistro2() {
        return RegistroAbastecimento.builder().funcionario(gerarFuncionario).cliente(criarCliente()).bomba(gerarBomba2).dataHora(LocalDateTime.parse("2023-01-21T18:21:00")).litrosAbastecidos(BigDecimal.valueOf(10)).formaPagamento(FormaPagamento.CARTAO).idAtendimento(2L).build();
    }

    public RegistroAbastecimento criarRegistro3() {
        return RegistroAbastecimento.builder().funcionario(gerarFuncionario).cliente(null).bomba(gerarBomba1).dataHora(LocalDateTime.parse("2023-01-21T18:21:00")).litrosAbastecidos(BigDecimal.valueOf(10)).formaPagamento(FormaPagamento.CARTAO).idAtendimento(2L).build();
    }

    public RegistroAbastecimento criarRegistro4() {
        return RegistroAbastecimento.builder().funcionario(gerarFuncionario).cliente(null).bomba(gerarBomba2).dataHora(LocalDateTime.parse("2023-01-21T18:21:00")).litrosAbastecidos(BigDecimal.valueOf(10)).formaPagamento(FormaPagamento.CARTAO).idAtendimento(2L).build();
    }


    private final Bomba gerarBomba1 = Bomba.builder().id(1L).ativo(true).idReservatorio(criarReservatorio1()).tipoCombustivel(criarReservatorio1().getTipoCombustivel()).build();

    private final Bomba gerarBomba2 = Bomba.builder().id(2L).ativo(true).idReservatorio(criarReservatorio2()).tipoCombustivel(criarReservatorio2().getTipoCombustivel()).build();

    private DadosEndereco gerarDadosEndereco() {

        return new DadosEndereco("Carlos Alves", "Boa Vista", "098652323", "Blumenau", "SC", "97", "Apartamento, Bloco B");
    }

    @Mock
    private Funcionario gerarFuncionario = Funcionario.builder().id(1L).ativo(true).cpf("098906505").cargo(Cargo.FRENTISTA).nome("Marcos").endereco(new Endereco(gerarDadosEndereco())).dataContratacao(LocalDate.parse("2022-07-21")).dataNascimento(LocalDate.parse("1997-08-25")).telefone("45649878").build();

    private Reservatorio criarReservatorio1() {
        Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");
        Fornecedor fornecedor = Fornecedor.builder().idFornecedor(1L).nome("Petrobras").contato("Josias").telefone("47999884415").cnpj("00900800715").endereco(endereco).build();

        return Reservatorio.builder().id(1L).ativo(true).capacidade(new BigDecimal("1000")).qtdCombustivel(new BigDecimal("500")).idFornecedor(fornecedor).tipoCombustivel(TipoCombustivel.GASOLINA).build();

    }

    private Reservatorio criarReservatorio2() {
        Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");
        Fornecedor fornecedor = Fornecedor.builder().idFornecedor(1L).nome("Petrobras").contato("Josias").telefone("47999884415").cnpj("00900800715").endereco(endereco).build();

        return Reservatorio.builder().id(2L).ativo(true).capacidade(new BigDecimal("1000")).qtdCombustivel(new BigDecimal("500")).idFornecedor(fornecedor).tipoCombustivel(TipoCombustivel.GASOLINA_ADITIVADA).build();

    }

    private Cliente criarCliente() {
        Endereco endereco = new Endereco("Rua abc", "bairro", "cep", "uf", "cidade", "num", "complemento");

        return Cliente.builder().id(1L).nome("Eduardo").telefone("47999884415").documento("00900800715").endereco(endereco).build();

    }
}