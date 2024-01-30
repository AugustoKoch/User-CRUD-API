package com.augusto.usuarios.controller;

import com.augusto.usuarios.dto.UsuarioDTO;
import com.augusto.usuarios.dto.UsuarioRespostaDTO;
import com.augusto.usuarios.model.Usuario;
import com.augusto.usuarios.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos(){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable long id){
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);

        return usuarioData.map(usuario -> new ResponseEntity<>(UsuarioRespostaDTO.TransformaEmDTO(usuario), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nomes/{nomeUsuario}")
    public ResponseEntity<List<Usuario>> buscarPorNome(@PathVariable String nomeUsuario){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioRepository.findByNome(nomeUsuario);

        if (usuarios.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrar(@RequestBody UsuarioDTO dto){
        Usuario usuario = usuarioRepository.save(dto.transformaParaObjeto());
        return new ResponseEntity<>(UsuarioRespostaDTO.TransformaEmDTO(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable("id") long id, @RequestBody UsuarioDTO dto){
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);

        if (usuarioData.isPresent()){
            Usuario _usuario = usuarioData.get();
            BeanUtils.copyProperties(dto, _usuario);
            usuarioRepository.save(_usuario);
            return new ResponseEntity<>(UsuarioRespostaDTO.TransformaEmDTO(_usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluir(@PathVariable long id){
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}