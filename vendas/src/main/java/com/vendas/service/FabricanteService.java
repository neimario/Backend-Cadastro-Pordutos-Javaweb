package com.vendas.service;

import com.vendas.Repository.FabricanteRepository;
import com.vendas.dto.FabricanteDto;
import com.vendas.entity.Fabricante;
import com.vendas.mapper.Mappable;
import com.vendas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FabricanteService implements Mappable {

    private final FabricanteRepository fabricanteRepository;
    private final ModelMapper mapper;

    public FabricanteService(FabricanteRepository fabricanteRepository, ModelMapper mapper) {
        this.fabricanteRepository = fabricanteRepository;
        this.mapper = mapper;
    }
    public FabricanteDto buscarPorId(Long id) {
        Optional<Fabricante> fabricante = fabricanteRepository.findById(id);
        Fabricante fab = fabricante.orElseThrow(() -> new ObjectNotFoundException("Nenhum fabricante encontrada: " + id));
        return map(fabricante, FabricanteDto.class);
    }
    public List<FabricanteDto> listar() {
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        return fabricantes.stream().map(m -> map(m, FabricanteDto.class)).collect(Collectors.toList());
    }

    public void salvar(FabricanteDto fabricanteDto) {
        Fabricante fabricante = map(fabricanteDto, Fabricante.class);
        fabricanteRepository.save(fabricante);
    }

    public void alterar(FabricanteDto fabricanteDto, Long id) {
        Optional<Fabricante> fabricante = fabricanteRepository.findById(id);
        if (fabricante.isPresent()) {
            fabricanteDto.setId(id);
            fabricanteRepository.save(map(fabricanteDto, Fabricante.class));
        }
    }

    public void deletar(Long id) {
        fabricanteRepository.deleteById(id);
    }

    @Override
    public ModelMapper mapper() {
        return this.mapper;
    }


}
