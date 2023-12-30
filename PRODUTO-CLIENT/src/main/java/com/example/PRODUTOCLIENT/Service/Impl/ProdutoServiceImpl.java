package com.example.PRODUTOCLIENT.Service.Impl;

import com.example.PRODUTOCLIENT.Exception.ProdutoNotFoundException;
import com.example.PRODUTOCLIENT.Model.Produto;
import com.example.PRODUTOCLIENT.Repository.ProdutoRepository;
import com.example.PRODUTOCLIENT.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> getAll() {

        return produtoRepository.findAll();

    }

    @Override
    public Produto create(Produto produto) {

        return produtoRepository.save(produto);

    }

    @Override
    public void deleteById(Long id) {

        produtoRepository.deleteById(id);

    }

    @Override
    public Produto update(Long id, Produto produto) {
        Produto existingProduct = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        if (produto != null){

            existingProduct.setNome(produto.getNome());
            existingProduct.setValor(produto.getValor());

        }

        return produtoRepository.save(existingProduct);
    }

    @Override
    public Optional<Produto> getById(Long id) {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
            return Optional.of(produto);
    }

}