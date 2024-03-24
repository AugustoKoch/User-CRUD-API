package com.augusto.usuarios.service;

import com.augusto.usuarios.dto.UsuarioDTO;
import com.augusto.usuarios.dto.UsuarioRespostaDTO;
import com.augusto.usuarios.model.Usuario;
import com.augusto.usuarios.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.augusto.usuarios.utils.SenhaUtils.validarSenha;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> usuariosPage = usuarioRepository.findAll();

        if (usuariosPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(usuariosPage, HttpStatus.OK);
        }
    }

    public ResponseEntity<Page<Usuario>> getUsuarios(Pageable pageable) {
        Page<Usuario> usuarioPage = usuarioRepository.findAll(pageable);

        if (usuarioPage.isEmpty()){
            return new ResponseEntity<>(usuarioPage, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarioPage, HttpStatus.OK);
    }

    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(long id){
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);

        return usuarioData.map(usuario -> new ResponseEntity<>(UsuarioRespostaDTO.TransformaEmDTO(usuario),
                HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Usuario>> buscarPorNome(String nomeUsuario){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioRepository.findByNome(nomeUsuario);

        if (usuarios.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    public ResponseEntity<Object> cadastrar(@RequestBody UsuarioDTO dto) throws Exception {

        try {
            validarSenha(dto.getSenha());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        Usuario usuario = usuarioRepository.save(dto.transformaParaObjeto());
        return new ResponseEntity<>(UsuarioRespostaDTO.TransformaEmDTO(usuario), HttpStatus.CREATED);
    }

    public ResponseEntity<UsuarioRespostaDTO> atualizar(long id, UsuarioDTO dto){
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

    public ResponseEntity<HttpStatus> excluir(long id){
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}