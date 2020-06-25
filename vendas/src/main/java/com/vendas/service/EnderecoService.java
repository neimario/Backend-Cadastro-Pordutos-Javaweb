package com.vendas.service;

import com.vendas.Repository.EnderecoRepository;
import com.vendas.dto.EnderecoDto;
import com.vendas.dto.UsuarioDto;
import com.vendas.entity.Endereco;
import com.vendas.entity.Usuario;
import com.vendas.mapper.Mappable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class EnderecoService implements Mappable {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper mapper;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, ModelMapper mapper) {
        this.enderecoRepository = enderecoRepository;
        this.mapper = mapper;
    }

    public void salvar(List<EnderecoDto> enderecoDto, UsuarioDto usuarioDto) {
        for (EnderecoDto e : enderecoDto) {
            Endereco endereco = map(e, Endereco.class);
            Usuario usuario = map(usuarioDto, Usuario.class);
            endereco.setUsuario(usuario);
            enderecoRepository.save(endereco);
        }
    }

    @Override
    public ModelMapper mapper() {
        return this.mapper;
    }

}