package com.vendas.service;

import com.vendas.Repository.PagamentoRepository;
import com.vendas.entity.Pagamento;
import com.vendas.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }
    public Pagamento buscarPorId(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return pagamento.orElseThrow(() -> new ObjectNotFoundException("Pagamento não encontrado: " + id));
    }

    public void salvar(Pagamento pagamento) {
        pagamentoRepository.save(pagamento);
    }


}
