package com.example.PRODUTOCLI.Repository;

import com.example.PRODUTOCLI.Model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProdutoRepository extends MongoRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);

}
