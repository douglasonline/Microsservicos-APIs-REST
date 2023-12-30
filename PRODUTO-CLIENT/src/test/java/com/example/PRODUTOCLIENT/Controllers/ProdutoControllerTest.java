package com.example.PRODUTOCLIENT.Controllers;

import com.example.PRODUTOCLIENT.Configuration.ProdutoIdService;
import com.example.PRODUTOCLIENT.Model.Produto;
import com.example.PRODUTOCLIENT.Service.ProdutoService;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @Autowired
    private MongoTemplate mongoTemplate;

    private long idCounter = 0L;

    @Mock
    private ProdutoIdService produtoIdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurando o MongoTemplate
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://localhost:27018"), "testDB");
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

        // Configurando o comportamento do serviço mockado
        when(produtoService.getAll()).thenReturn(produtos);

        // Chamando o método do controlador
        ResponseEntity<List<Produto>> responseEntity = produtoController.getAll();

        // Verificação dos resultados
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Produto> produtosRetornados = responseEntity.getBody();
        assertEquals(produtos.size(), produtosRetornados.size());

        // Exibindo detalhes dos produtos retornados
        System.out.println("Produtos retornados:");
        for (Produto produto : produtosRetornados) {
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Valor: " + produto.getValor());
            System.out.println();
        }
    }

    @Test
    void getById() {
        // Simulando um ID existente
        Long id = 1L;

        // Criando um produto simulado para o ID
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(99.99));
        produto.setId(id);

        // Configurando o comportamento do serviço mockado
        when(produtoService.getById(id)).thenReturn(Optional.of(produto));

        // Chamando o método do controlador para buscar o produto pelo ID
        ResponseEntity<?> responseEntity = produtoController.getById(id);

        // Verificando se a resposta está correta
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Produto produtoRetornado = (Produto) responseEntity.getBody();
        assertEquals(id, produtoRetornado.getId());
        assertEquals("Produto Teste", produtoRetornado.getNome());
        assertEquals(BigDecimal.valueOf(99.99), produtoRetornado.getValor());

        // Exibindo os detalhes do produto retornado
        System.out.println("Detalhes do Produto retornado:");
        System.out.println("ID: " + produtoRetornado.getId());
        System.out.println("Nome: " + produtoRetornado.getNome());
        System.out.println("Valor: " + produtoRetornado.getValor());
    }

    @Test
    void deleteById() {
        Long id = 1L;

        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(99.99));
        produto.setId(id);

        when(produtoService.getById(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoService).deleteById(id);

        ResponseEntity<?> responseEntity = produtoController.delete(id);

        System.out.println("Status da resposta: " + responseEntity.getStatusCode());
        System.out.println("Mensagem da resposta: " + responseEntity.getBody());

        verify(produtoService, times(1)).deleteById(id);
    }

    @Test
    void create() {
        Produto produto = new Produto("Produto Teste", BigDecimal.valueOf(99.99));

        Long nextId = 1L;
        produto.setId(nextId);

        when(produtoIdService.getNextSequenceId()).thenReturn(nextId);
        when(produtoService.create(produto)).thenReturn(produto);

        ResponseEntity<Produto> responseEntity = produtoController.create(produto);

        System.out.println("Status da resposta: " + responseEntity.getStatusCode());
        System.out.println("Produto criado: " + responseEntity.getBody());

        verify(produtoService, times(1)).create(produto);
    }

    @Test
    void update() {
        Long id = 1L;
        Produto produto = new Produto("Produto", BigDecimal.valueOf(200));
        produto.setId(id);

        // Configurando o comportamento do serviço mockado
        when(produtoService.getById(id)).thenReturn(Optional.of(produto));

        // Dados antes da atualização
        System.out.println("Dados antes da atualização:");
        ResponseEntity<?> responseBeforeUpdate = produtoController.getById(id);
        System.out.println(responseBeforeUpdate.getBody()); // Mostrar os dados antes da atualização

        // Simulando a resposta do serviço ao buscar o produto pelo ID
        Produto produto2 = new Produto("Produto Atualizado", BigDecimal.valueOf(100));
        produto2.setId(id);
        Optional<Produto> produtoOptional = Optional.of(produto2);
        when(produtoService.getById(id)).thenReturn(produtoOptional);

        // Chamando o método de atualização
        ResponseEntity<?> responseEntity = produtoController.update(id, produto);

        // Dados depois da atualização
        System.out.println("\nDados depois da atualização:");
        ResponseEntity<?> responseAfterUpdate = produtoController.getById(id);
        System.out.println(responseAfterUpdate.getBody()); // Mostrar os dados depois da atualização

        // Verificar se a resposta é OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}