package com.vendas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="TB_produto")
public class Produto implements Serializable {

    private static final long serialVersionUID= 8008965968767240529L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;

   @ManyToOne
   @JoinColumn(name="fabricante")
    private Fabricante fabricante;

   @ManyToMany
   @JoinTable(name="TB_produto_categoria",
           joinColumns = @JoinColumn(name="produto"),
           inverseJoinColumns = @JoinColumn(name ="categoria")
   )
    private List<Categoria> categorias = new ArrayList<>();

   @OneToMany(mappedBy = "id.produto")
    private Set<ItemPedido> itens = new HashSet<>();

}
