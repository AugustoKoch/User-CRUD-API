package com.augusto.usuarios.controller;

import com.augusto.usuarios.dto.UsuarioDTO;
import com.augusto.usuarios.dto.UsuarioRespostaDTO;
import com.augusto.usuarios.model.Usuario;
import com.augusto.usuarios.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    private UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos(){
        return usuarioService.buscarTodos();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Usuario>> buscarTodosPage(Pageable pageable){
        return usuarioService.getUsuarios(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable long id){
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/nomes/{nomeUsuario}")
    public ResponseEntity<List<Usuario>> buscarPorNome(@PathVariable String nomeUsuario){
        return usuarioService.buscarPorNome(nomeUsuario);
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody UsuarioDTO dto) throws Exception {
        return usuarioService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable("id") long id, @RequestBody UsuarioDTO dto){
        return usuarioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluir(@PathVariable long id){
        return usuarioService.excluir(id);
    }
}