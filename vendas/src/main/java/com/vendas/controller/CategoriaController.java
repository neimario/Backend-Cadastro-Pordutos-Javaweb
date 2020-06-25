package com.vendas.controller;

import com.vendas.dto.CategoriaDto;
import com.vendas.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(value = "API REST Categorias")
@RestController
@RequestMapping("/v1")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ApiOperation(value = "Lista uma categoria", authorizations = { @Authorization(value = "jwtToken")})
    @GetMapping("/categoria/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CategoriaDto buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @ApiOperation(value = "Lista todas categoria", authorizations = { @Authorization(value = "jwtToken")})
    @GetMapping("/categorias")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaDto> listar() {
        return categoriaService.listar();
    }

    @ApiOperation(value = "Salvar categoria", authorizations = { @Authorization(value = "jwtToken")})
    @PostMapping("/categoria")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody CategoriaDto categoriaDto) {
        categoriaService.salvar(categoriaDto);
    }

    @ApiOperation(value = "Alterar categoria", authorizations = { @Authorization(value = "jwtToken")})
    @PutMapping("/categoria/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void alterar(@RequestBody CategoriaDto categoriaDto, @PathVariable Long id) {
        categoriaService.alterar(categoriaDto, id);
    }

    @ApiOperation(value = "Deletar categoria", authorizations = { @Authorization(value = "jwtToken")})
    @DeleteMapping("/categoria/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(Long id) {
        categoriaService.deletar(id);
    }

}