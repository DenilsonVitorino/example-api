package br.com.denilson.api.Endpoint;

import br.com.denilson.api.error.ResourceNotFoundException;
import br.com.denilson.api.model.Produto;
import br.com.denilson.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/produto")
public class ProdutoEndpoint {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(produtoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        verifyIfExists(id);
        return new ResponseEntity<>(produtoRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Produto produto) {
        return new ResponseEntity<>(produtoRepository.save(produto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Produto produto) {
        verifyIfExists(produto.getId());
        produtoRepository.save(produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfExists(id);
        produtoRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfExists(Long id) {
        if (produtoRepository.findOne(id) == null)
            throw new ResourceNotFoundException("Produto não encontrada para ID: " + id);
    }
}
