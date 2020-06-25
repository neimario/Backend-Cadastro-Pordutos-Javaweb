package com.vendas.service;

import com.vendas.Repository.UsuarioRepository;
import com.vendas.dto.UsuarioDto;
import com.vendas.entity.Usuario;
import com.vendas.mapper.Mappable;
import com.vendas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements Mappable {

  //  private final BCryptPasswordEncoder encoder;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;
    private final EnderecoService enderecoService;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoService enderecoService, ModelMapper mapper) {
      //  this.encoder = encoder;
        this.usuarioRepository = usuarioRepository;
        this.enderecoService = enderecoService;
        this.mapper = mapper;
    }

    public UsuarioDto buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        Usuario user = usuario.orElseThrow(() -> new ObjectNotFoundException("Nenhum usuario encontrada: " + id));
        return map(user, UsuarioDto.class);
    }
    public List<UsuarioDto> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(m -> map(m, UsuarioDto.class)).collect(Collectors.toList());
    }

    public Usuario buscaPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void salvar(UsuarioDto usuarioDto) {
       // String senhaCriptografada = encoder.encode(usuarioDto.getSenha());
      //  usuarioDto.setSenha(senhaCriptografada);
        Usuario usuario = map(usuarioDto, Usuario.class);
        usuario.setEnderecos(null);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        UsuarioDto usuarioMapeado = map(usuarioSalvo, UsuarioDto.class);
        enderecoService.salvar(usuarioDto.getEnderecos(), usuarioMapeado);
    }


    public void alterar(UsuarioDto usuarioDto, Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioDto.setId(id);
            usuarioRepository.save(map(usuarioDto, Usuario.class));
        }
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }


    @Override
    public ModelMapper mapper() {
        return this.mapper;
    }


}
