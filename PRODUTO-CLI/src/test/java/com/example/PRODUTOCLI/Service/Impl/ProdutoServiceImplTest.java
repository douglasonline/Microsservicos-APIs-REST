package com.example.PRODUTOCLI.Service.Impl;

import com.example.PRODUTOCLI.Model.Produto;
import com.example.PRODUTOCLI.Repository.ProdutoRepository;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private long idCounter = 0L;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurando o MongoTemplate
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://localhost:27019"), "testDB");
    }

    @AfterEach
    void tearDown() {
        // Pode ser vazio ou removido, já que o Spring cuidará do fechamento do MongoTemplate
    }

    @Test
    void getAll() {

        // Criando uma lista simulada de produtos
        List<Produto> produtos = new ArrayList<>();
        Produto produto1 = new Produto("Produto 1", BigDecimal.valueOf(50));
        produto1.setId(++idCounter); // Simulando a atribuição de ID sequencial
        produtos.add(produto1);

        Produto produto2 = new Produto("Produto 2", BigDecimal.valueOf(100));
        produto2.setId(++idCounter); // Simulando a atribuição de ID sequencial
        produtos.add(produto2);

        when(produtoRepository.findAll()).thenReturn(produtos);

        // Criar uma instância de ProdutoServiceImpl
        ProdutoServiceImpl produtoServiceImpl = new ProdutoServiceImpl(produtoRepository);

        List<Produto> produtosRetornados = produtoServiceImpl.getAll();
        System.out.println("Produtos retornados:");
        produtosRetornados.forEach(produto -> {
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Valor: " + produto.getValor());
            System.out.println();
        });

        assertEquals(2, produtosRetornados.size());
    }



    @Test
    void create() {
        Produto produto = new Produto("Produto Novo", BigDecimal.valueOf(75));

        // Simulando a atribuição de ID sequencial
        produto.setId(++idCounter);

        when(produtoRepository.save(any())).thenAnswer(invocation -> {
            Produto produtoSalvo = invocation.getArgument(0);
            produtoSalvo.setId(idCounter);
            return produtoSalvo;
        });

        // Criar uma instância de ProdutoServiceImpl
        ProdutoServiceImpl produtoServiceImpl = new ProdutoServiceImpl(produtoRepository);

        Produto produtoCriado = produtoServiceImpl.create(produto);

        assertNotNull(produtoCriado.getId());
        assertEquals(idCounter, produtoCriado.getId());

        System.out.println("Produto criado:");
        System.out.println("ID: " + produtoCriado.getId());
        System.out.println("Nome: " + produtoCriado.getNome());
        System.out.println("Valor: " + produtoCriado.getValor());
    }



    @Test
    void deleteById() {
        Long id = 1L;
        Produto produto = new Produto("Produto", BigDecimal.valueOf(150));

        // Configuração do comportamento do método create para salvar um produto e retorná-lo com ID definido
        when(produtoRepository.save(any())).thenAnswer(invocation -> {
            Produto produtoSalvo = invocation.getArgument(0);
            produtoSalvo.setId(id);
            return produtoSalvo;
        });

        // Criar uma instância de ProdutoServiceImpl
        ProdutoServiceImpl produtoServiceImpl = new ProdutoServiceImpl(produtoRepository);

        // Salvando um produto simulado
        Produto produtoCriado = produtoServiceImpl.create(produto);

        // Simular o retorno do findById com um Optional contendo o produto criado
        when(produtoRepository.findById(produtoCriado.getId())).thenReturn(Optional.of(produtoCriado));

        // Exibir os dados do produto antes da exclusão
        Optional<Produto> produtoAntesExclusao = produtoServiceImpl.getById(produtoCriado.getId());
        if (produtoAntesExclusao.isPresent()) {
            Produto produtoParaExcluir = produtoAntesExclusao.get();
            System.out.println("Produto antes da exclusão:");
            System.out.println("ID: " + produtoParaExcluir.getId());
            System.out.println("Nome: " + produtoParaExcluir.getNome());
            System.out.println("Valor: " + produtoParaExcluir.getValor());
        }

        // Excluir o produto
        produtoServiceImpl.deleteById(produtoCriado.getId());


        System.out.println("\nProduto com ID " + produtoCriado.getId() + " foi excluído com sucesso.");


    }

    @Test
    void update() {
        Long id = 1L;
        Produto produto = new Produto("Produto", BigDecimal.valueOf(150));

        // Configuração do comportamento do método create para salvar um produto e retorná-lo com ID definido
        when(produtoRepository.save(any())).thenAnswer(invocation -> {
            Produto produtoSalvo = invocation.getArgument(0);
            produtoSalvo.setId(id);
            return produtoSalvo;
        });

        // Criar uma instância de ProdutoServiceImpl
        ProdutoServiceImpl produtoServiceImpl = new ProdutoServiceImpl(produtoRepository);

        // Salvando um produto simulado
        Produto produtoCriado = produtoServiceImpl.create(produto);

        // Simular comportamento de getById
        when(produtoRepository.findById(produtoCriado.getId())).thenReturn(Optional.of(produtoCriado));

        // Exibindo informações do produto antes de atualizar
        System.out.println("Produto antes de ser atualizado:");
        System.out.println("ID: " + produtoCriado.getId());
        System.out.println("Nome: " + produtoCriado.getNome());
        System.out.println("Valor: " + produtoCriado.getValor());

        // Atualizando o produto existente
        Produto produtoAtualizar = new Produto("Produto Atualizado", BigDecimal.valueOf(100));

        // Configurar comportamento do método update
        when(produtoServiceImpl.update(produtoCriado.getId(), any(Produto.class))).thenAnswer(invocation -> {
            Produto produtoAtualizado = invocation.getArgument(0);
            produtoAtualizado.setId(produtoCriado.getId()); // Mantém o mesmo ID
            return produtoAtualizado;
        });

        // Chamando o método update
        Produto produtoAtualizado = produtoServiceImpl.update(produtoCriado.getId(), produtoAtualizar);

        // Exibindo informações do produto após a atualização
        System.out.println("\nProduto atualizado:");
        System.out.println("ID: " + produtoAtualizado.getId());
        System.out.println("Nome: " + produtoAtualizado.getNome());
        System.out.println("Valor: " + produtoAtualizado.getValor());

        assertEquals(produtoAtualizar.getNome(), produtoAtualizado.getNome());
        assertEquals(produtoAtualizar.getValor(), produtoAtualizado.getValor());
    }

    @Test
    void getById() {

        Long id = 1L;
        Produto produto = new Produto("Produto 1", BigDecimal.valueOf(50));
        produto.setId(id); // Definindo o ID do produto

        // Simular o retorno do findById com um Optional contendo o produto
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        // Criar uma instância de ProdutoServiceImpl
        ProdutoServiceImpl produtoServiceImpl = new ProdutoServiceImpl(produtoRepository);

        // Testar quando a verificação está habilitada
        Optional<Produto> produtoRetornado = produtoServiceImpl.getById(id);
        assertTrue(produtoRetornado.isPresent());
        assertEquals(produto, produtoRetornado.get());
        System.out.println("Produto retornado (verificação habilitada):");
        System.out.println("ID: " + produtoRetornado.get().getId());
        System.out.println("Nome: " + produtoRetornado.get().getNome());
        System.out.println("Valor: " + produtoRetornado.get().getValor());

        // Testar quando a verificação está desabilitada
        produtoRetornado = produtoServiceImpl.getById(id);
        assertTrue(produtoRetornado.isPresent());
        assertEquals(produto, produtoRetornado.get());
        System.out.println("\nProduto retornado (verificação desabilitada):");
        System.out.println("ID: " + produtoRetornado.get().getId());
        System.out.println("Nome: " + produtoRetornado.get().getNome());
        System.out.println("Valor: " + produtoRetornado.get().getValor());

    }

}