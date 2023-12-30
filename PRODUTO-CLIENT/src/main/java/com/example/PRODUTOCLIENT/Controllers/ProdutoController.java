package com.example.PRODUTOCLIENT.Controllers;

// Importações necessárias
import com.example.PRODUTOCLIENT.Configuration.ProdutoIdService;
import com.example.PRODUTOCLIENT.Exception.ProdutoNotFoundException;
import com.example.PRODUTOCLIENT.Model.Produto;
import com.example.PRODUTOCLIENT.Service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoIdService produtoIdService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        List<Produto> all = produtoService.getAll();
        LOGGER.info("OBTER TODOS OS PRODUTOS: " + all.size() + " produtos encontrados");
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Optional<Produto> produtoOptional = produtoService.getById(id);

            if (produtoOptional.isPresent()) {
                Produto produto = produtoOptional.get();
                LOGGER.info("OBTER PRODUTO POR ID: " + id);
                return ResponseEntity.ok(produto);
            } else {
                LOGGER.error("PRODUTO NÃO ENCONTRADO POR ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
            }
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error("ERRO AO OBTER PRODUTO POR ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem", "Produto não encontrado"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Produto> produtoOptional = produtoService.getById(id);

            if (produtoOptional.isPresent()) {
                Produto produto = produtoOptional.get();
                produtoService.deleteById(produto.getId());
                LOGGER.info("EXCLUIR PRODUTO POR ID: " + id);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("Mensagem", "Produto com o id " + id + " excluído com sucesso!"));
            } else {
                LOGGER.error("PRODUTO NÃO ENCONTRADO PARA EXCLUSÃO POR ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
            }
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error("ERRO AO EXCLUIR PRODUTO POR ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem", "Produto não encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        Long nextId = produtoIdService.getNextSequenceId();
        produto.setId(nextId);

        Produto produtoCriado = produtoService.create(produto);

        LOGGER.info("CRIAR NOVO PRODUTO: " + produtoCriado.getId());
        return ResponseEntity.ok(produtoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Optional<Produto> produtoOptional = produtoService.getById(id);

            if (produtoOptional.isPresent()) {
                Produto updated = produtoService.update(id, produto);
                LOGGER.info("ATUALIZAR PRODUTO POR ID: " + id);
                return ResponseEntity.ok(updated);
            } else {
                LOGGER.error("PRODUTO NÃO ENCONTRADO PARA ATUALIZAÇÃO POR ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Produto não encontrado"));
            }
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error("ERRO AO ATUALIZAR PRODUTO POR ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem", "Produto não encontrado"));
        }
    }

}