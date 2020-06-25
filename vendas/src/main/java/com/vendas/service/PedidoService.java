package com.vendas.service;

import com.vendas.Repository.PedidoRepository;
import com.vendas.dto.ProdutoDto;
import com.vendas.dto.UsuarioDto;
import com.vendas.entity.*;
import com.vendas.entity.enums.EstadoPagamento;
import com.vendas.mapper.Mappable;
import com.vendas.service.email.EmailService;
import com.vendas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
    public class PedidoService implements Mappable {

        private final PedidoRepository pedidoRepository;
        private final UsuarioService usuarioService;
        private final ModelMapper mapper;
        private final BoletoService boletoService;
        private final PagamentoService pagamentoService;
        private final ProdutoService produtoService;
        private final ItemPedidoService itemPedidoService;
        private final EmailService emailService;

        @Autowired
        public PedidoService(PedidoRepository pedidoRepository, UsuarioService usuarioService, ModelMapper mapper, BoletoService boletoService, PagamentoService pagamentoService, ProdutoService produtoService, ItemPedidoService itemPedidoService, EmailService emailService) {
            this.pedidoRepository = pedidoRepository;
            this.usuarioService = usuarioService;
            this.mapper = mapper;
            this.boletoService = boletoService;
            this.pagamentoService = pagamentoService;
            this.produtoService = produtoService;
            this.itemPedidoService = itemPedidoService;
            this.emailService = emailService;
        }

        public Pedido buscarPorId(Long id) {
            Optional<Pedido> pedido = pedidoRepository.findById(id);
            return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado: " + id));
        }

        @Transactional
        public Pedido salvar(Pedido pedido) {
            pedido.setInstante(new Date());
            UsuarioDto usuarioDto = usuarioService.buscarPorId(pedido.getUsuario().getId());
            pedido.setUsuario(map(usuarioDto, Usuario.class));
            pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
            pedido.getPagamento().setPedido(pedido);
            if (pedido.getPagamento() instanceof PagamentoComBoleto) {
                PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
                boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
            }
            pedido = pedidoRepository.save(pedido);
            pagamentoService.salvar(pedido.getPagamento());
            for (ItemPedido item: pedido.getItens()) {
                item.setDesconto(0.0);
                ProdutoDto produtoDto = produtoService.buscarPorId(item.getProduto().getId());
                item.setProduto(map(produtoDto, Produto.class));
                item.setPreco(produtoService.buscarPreco(item.getProduto().getId()));
                item.setPedido(pedido);
            }
            itemPedidoService.salvarTodos(pedido.getItens());
            System.out.println(pedido);
            emailService.confirmacaoEmailPedido(pedido);
            return pedido;
        }

        @Override
        public ModelMapper mapper() {
            return this.mapper;
        }
    }

