package com.vendas.service;

import com.vendas.Repository.ProdutoRepository;
import com.vendas.dto.ProdutoDto;
import com.vendas.entity.Produto;
import com.vendas.mapper.Mappable;
import com.vendas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService implements Mappable {
    private final ProdutoRepository produtoRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ModelMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    public ProdutoDto buscarPorId(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        Produto prod = produto.orElseThrow(()-> new ObjectNotFoundException("Produto Não Encontrado:" +id));
       return map(prod, ProdutoDto.class);
    }



    public Double buscarPreco(Long id) {
        Double preco = produtoRepository.buscaPreco(id);
        if (preco == null) {
            throw new ObjectNotFoundException("Produto sem preço: " + id);
        }
        return preco;
    }

    public List<ProdutoDto> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(m -> map(m, ProdutoDto.class)).collect(Collectors.toList());
    }

    public void salvar(ProdutoDto produtoDto) {
        Produto produto = map(produtoDto, Produto.class);
        produtoRepository.save(produto);
    }

    public void atualizar(ProdutoDto produtoDto, Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produtoDto.setId(id);
            Produto produtoMapper = map(produtoDto, Produto.class);
            produtoRepository.save(produtoMapper);
        }
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public ModelMapper mapper() {
        return this.mapper;
    }


}
