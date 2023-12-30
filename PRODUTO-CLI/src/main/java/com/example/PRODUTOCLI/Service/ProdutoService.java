package com.example.PRODUTOCLI.Service;


import com.example.PRODUTOCLI.Model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProdutoService {

    List<Produto> getAll();

    Produto create(Produto produto);

    void deleteById(Long id);

    Produto update(Long id, Produto produto);

    Optional<Produto> getById(Long id);
}
