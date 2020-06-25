package com.vendas.controller;

import com.vendas.entity.Pedido;
import com.vendas.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "API REST - Pedido")
@CrossOrigin
@RestController
@RequestMapping("/v1")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @ApiOperation(value = "Busca pedido por id", authorizations = {@Authorization(value = "jwtToken")})

    @GetMapping("/pedido/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Pedido buscaPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @ApiOperation(value = "Salvar pedido", authorizations = { @Authorization(value = "jwtToken")})

    @PostMapping("/pedido")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido salvar(@RequestBody Pedido pedido) {
        return pedidoService.salvar(pedido);
    }

}