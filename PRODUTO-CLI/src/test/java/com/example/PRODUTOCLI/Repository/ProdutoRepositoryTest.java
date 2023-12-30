package com.example.PRODUTOCLI.Repository;

import com.example.PRODUTOCLI.Model.Produto;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProdutoRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Long idCounter = 0L; // Contador para simular IDs sequenciais

    @BeforeEach
    void setUp() {
        // Configurando o MongoTemplate
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://localhost:27019"), "testDB");
    }

    @AfterEach
    void tearDown() {
        // Pode ser vazio ou removido, já que o Spring cuidará do fechamento do MongoTemplate
    }

    @Test
    @DisplayName("Deve obter o Produto com sucesso do DB")
    void findByNomeCase1() {
        // Meu código de teste usando o MongoDB

        Produto produto = new Produto("Blusa", BigDecimal.valueOf(100)); // Criar o Produto

        Produto savedProduto = createProduto(produto); // Salvar o Produto

        assertNotNull(savedProduto.getId()); // Verificar se o ID foi gerado

        Produto retrievedProduto = mongoTemplate.findById(savedProduto.getId(), Produto.class); // Recuperar o Produto salvo
        assertNotNull(retrievedProduto); // Verificar se o Produto foi salvo corretamente

        // Mostrar detalhes do Produto recuperado do banco de dados
        System.out.println("Produto recuperado:");
        System.out.println("ID: " + retrievedProduto.getId());
        System.out.println("Nome: " + retrievedProduto.getNome());
        System.out.println("Valor: " + retrievedProduto.getValor());
    }

    @Test
    @DisplayName("Não deverá obter Produto do DB quando o produto não existe")
    void findByNomeCase2() {
        // Criar um identificador que não existe no banco de dados
        Long nonExistentId = 999L;

        // Tentar encontrar um produto com o identificador não existente
        Produto retrievedProduto = mongoTemplate.findById(nonExistentId, Produto.class);

        // Verificar se o produto retornado é nulo e exibir a mensagem
        assertNull(retrievedProduto, "O produto não deve existir no banco de dados");
        System.out.println("O produto não deve existir no banco de dados");
    }

    private Produto createProduto(Produto produto) {
        // Simular a atribuição de ID sequencial
        produto.setId(++idCounter);
        return mongoTemplate.save(produto); // Salvar e retornar o Produto
    }

}