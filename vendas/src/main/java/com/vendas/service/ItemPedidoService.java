package com.vendas.service;

import com.vendas.Repository.ItemPedidoRepository;
import com.vendas.entity.ItemPedido;
import com.vendas.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }
    public ItemPedido buscarPorId(Long id) {
        Optional<ItemPedido> pedido = itemPedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado: " + id));
    }

    public void salvarTodos(Set<ItemPedido> itemPedido) {
        itemPedidoRepository.saveAll(itemPedido);
    }


}
