package com.vendas.Repository;

import com.vendas.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("select p.preco from Produto p where p.id = ?1")
    Double buscaPreco(Long id);
}
