package com.vendas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto implements Serializable {
    private static final long serialVersionUID = 6954540263079800140L;
    private Long id;
    private String nome;

    @JsonIgnore
    private List<ProdutoDto>produtos = new ArrayList<>();
}
