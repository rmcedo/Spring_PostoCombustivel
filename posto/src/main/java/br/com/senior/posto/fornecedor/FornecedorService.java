package br.com.senior.posto.fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.senior.posto.cliente.Cliente;
import br.com.senior.posto.cliente.DadosAtualizacaoCliente;
import br.com.senior.posto.funcionario.DadosListagemFuncionario;
import br.com.senior.posto.funcionario.DadosVisualizacaoFuncionario;
import br.com.senior.posto.funcionario.Funcionario;
import jakarta.validation.Valid;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository repository;

	
	public FornecedorService(FornecedorRepository repository) {
		this.repository = repository;
	}

	public Fornecedor cadastrar(DadosCadastroFornecedor dadosCadastroFornecedor) {
	    if (repository.existsByCnpj(dadosCadastroFornecedor.cnpj())) {
            throw new IllegalArgumentException("Cnpj ja cadastrado!");
        }
		Fornecedor fornecedor = new Fornecedor(dadosCadastroFornecedor);
		repository.save(fornecedor);
		return fornecedor;
	}	
		
	 public Page<DadosListagemFornecedor> listar(@PageableDefault Pageable paginacao) {
	        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemFornecedor::new);
	}

	public Fornecedor atualizar(DadosAtualizacaoFornecedor dados) {
		Fornecedor fornecedor = repository.getReferenceById(dados.id());
		fornecedor.atualizarInformacoes(dados);
		return fornecedor;
		
		
	}	
	
    public void excluir(@PathVariable Long id) {
        var fornecedor = repository.getReferenceById(id);
        fornecedor.excluir();
    }
    
    public Page<Fornecedor> buscarPorNome(String nome, Pageable paginacao) {
        return repository.findByNome(nome, paginacao);
    }    

    public Fornecedor getById(Long id) {
    	return repository.getReferenceById(id);
    }
    
    public DadosVisualizacaoFornecedor detalharFornecedor(Long id) {
        return new DadosVisualizacaoFornecedor(repository.getReferenceById(id));
    }
    
    public Fornecedor buscarPorCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }
    
	
}
