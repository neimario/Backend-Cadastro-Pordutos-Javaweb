package com.vendas.service;

import com.vendas.Repository.CategoriaRepository;
import com.vendas.dto.CategoriaDto;
import com.vendas.entity.Categoria;
import com.vendas.mapper.Mappable;
import com.vendas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements Mappable {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    public CategoriaDto buscarPorId(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        Categoria category = categoria.orElseThrow(() -> new ObjectNotFoundException("Nenhuma Categoria Encontrda" +id));
        return map(category, CategoriaDto.class);
    }
    public List<CategoriaDto> listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(m -> map(m, CategoriaDto.class)).collect(Collectors.toList());

    }

    public void salvar(CategoriaDto categoriaDto) {
        Categoria categoria = map(categoriaDto, Categoria.class);
        categoriaRepository.save(categoria);
    }

    public void alterar(CategoriaDto categoriaDto, Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoriaDto.setId(id);
            categoriaRepository.save(map(categoriaDto, Categoria.class));

        }
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public ModelMapper mapper() {
        return this.mapper;
    }
}
