package br.com.fiap.techbridge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.techbridge.models.Avaliacao;
import br.com.fiap.techbridge.repository.AvaliacaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("techbridge/api/avaliacao")
public class AvaliacaoController {
    
    @Autowired
    AvaliacaoRepository repository;

    // CRIAR
    @PostMapping()
    public ResponseEntity<Avaliacao> create(@RequestBody @Valid Avaliacao avaliacao, BindingResult result){
        /* preciso impedir alguem de criar duas avaliações da mesma empresa */
        repository.save(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }

    // DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Avaliacao> delete(@PathVariable Long id){
        var avaliacaoEncontrada = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Erro ao apagar. Avaliação não encontrada"));
        repository.delete(avaliacaoEncontrada);
        return ResponseEntity.noContent().build();
    }

    // EDITAR
    @PutMapping("{id}")
    public ResponseEntity<Avaliacao> update(@PathVariable Long id, @RequestBody @Valid Avaliacao avaliacao){
        var contaEncontrada = repository.findById(id);
        if (contaEncontrada.isEmpty())
            return ResponseEntity.notFound().build();
        avaliacao.setJulgamento(contaEncontrada.get().getJulgamento());
        avaliacao.setConta(contaEncontrada.get().getConta());
        avaliacao.setId(id);
        repository.save(avaliacao);
        return ResponseEntity.ok(avaliacao);
    }

    // DETALHES
    @GetMapping("{id}")
    public ResponseEntity<?> details(@PathVariable Long id){
        var avaliacaoEncontrada = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "avaliacao não encontrada"));
        return ResponseEntity.ok(avaliacaoEncontrada);
    }
    
    //JULGAR
    @PutMapping("{id}/{contaId}")
    public String julgar(){
        return "ainda não implementado";
    }
}
