package br.com.senior.posto.controller;

import br.com.senior.posto.enums.Cargo;
import br.com.senior.posto.funcionario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService,
                                 FuncionarioRepository funcionarioRepository) {
        this.funcionarioService = funcionarioService;
        this.funcionarioRepository = funcionarioRepository;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroFuncionario dadosCadastroFuncionario, UriComponentsBuilder uriBuilder) {
        Funcionario funcionario = funcionarioService.cadastrar(dadosCadastroFuncionario);
        var uri = uriBuilder.path("/funcionario/{id}").buildAndExpand(funcionario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosVisualizacaoFuncionario(funcionario));
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity detalhar(@PathVariable Long idFuncionario) {

        return ResponseEntity.ok(funcionarioService.detalharFuncionario(idFuncionario));
    }

    @DeleteMapping("/{idFuncionario}")
    @Transactional
    public ResponseEntity deletar(@PathVariable long idFuncionario) {
        funcionarioService.deletar(idFuncionario);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoFuncionario dadosAtualizacaoFuncionario) {
        Funcionario funcionario = funcionarioService.atualizar(dadosAtualizacaoFuncionario);

        return ResponseEntity.ok(new DadosVisualizacaoFuncionario(funcionario));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<Page<DadosListagemFuncionario>> listar(@PageableDefault(size = 5, sort = {"dataContratacao"})Pageable paginacao) {
        Page page = funcionarioService.listar(paginacao);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/busca-por-nome/{nome}")
    public ResponseEntity<Page<DadosListagemFuncionario>> buscarPorNome(@PathVariable String nome, @PageableDefault(size = 5, sort = {"nome"})Pageable paginacao) {
        Page page = funcionarioService.buscarPorNome(nome, paginacao).map(DadosListagemFuncionario::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("busca-data-contratacao/{dataContratacao}")
    public ResponseEntity<Page<DadosListagemFuncionario>> buscarPorDataDeContratacao(@PathVariable String dataContratacao, @PageableDefault(size = 5, sort = {"dataContratacao"})Pageable paginacao) {
        Page page = funcionarioService.buscarPorDataContratacao(dataContratacao, paginacao).map(DadosListagemFuncionario::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("busca-a-partir-da-data-contratacao/{dataContratacao}")
    public ResponseEntity<Page<DadosListagemFuncionario>> buscarDataContratacaoEmDiante(@PathVariable String dataContratacao, @PageableDefault(size = 5, sort = {"data_contratacao"})Pageable paginacao) {
        Page page = funcionarioService.buscarPorDataContratacaoEmDiante(dataContratacao, paginacao).map(DadosListagemFuncionario::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("buscar-por-cpf/{cpf}")
    public ResponseEntity buscarPorCpf(@PathVariable String cpf) {

        return ResponseEntity.ok(funcionarioService.buscarPorCpf(cpf));
    }

    @GetMapping("buscar-por-cargo/{cargo}")
    public ResponseEntity<Page<DadosVisualizacaoFuncionario>> buscarPorCargo(@PathVariable String cargo, @PageableDefault(size = 5, sort = {"data_contratacao"})Pageable paginacao) {
        cargo.toUpperCase();
        Page page = funcionarioService.buscarPorCargo(cargo, paginacao).map(DadosVisualizacaoFuncionario::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("mais-registros")
    public ResponseEntity buscarFuncionarioComMaisRegistros() {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioComMaiorNumeroDeRegistros());
    }

}
