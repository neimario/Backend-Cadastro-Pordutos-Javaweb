package com.vendas.controller;

import com.vendas.dto.ProdutoDto;
import com.vendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/v1")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping("/produto/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDto buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }


    @GetMapping("/produtos")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDto> listarProdutos() {
        return produtoService.listar();
    }


    @PostMapping("/produto")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarProdutos(@RequestBody ProdutoDto produtoDto) {
        produtoService.salvar(produtoDto);
    }


    @PutMapping("/alterar-produto/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void alterarProduto(@RequestBody ProdutoDto produtoDto, @PathVariable Long id) {
        produtoService.atualizar(produtoDto, id);
    }


    @DeleteMapping("/deletar-produto/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletar(id);
    }

}