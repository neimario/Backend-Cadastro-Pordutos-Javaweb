package com.vendas.service.email;

import com.vendas.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void confirmacaoEmailPedido(Pedido pedido);
    void enviarEmail(SimpleMailMessage simpleMailMessage);

}