package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.Mensaje;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointGuardarFile {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @PostMapping()
    @ApiOperation("guarda el archivo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    public ResponseEntity<?> guardarFile(
            @RequestParam("numero de identificacion") Integer numero,
            @RequestParam("file") MultipartFile file
            ) {
        if(manejadorFileImagen.existeFile(numero) == true) {
            manejadorFileImagen.guardar(numero, file);
            return new ResponseEntity<>(new Mensaje("true"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Mensaje("el usuario con la identificacion " + numero + "ya tiene registrada una imagen"), HttpStatus.BAD_REQUEST);
        }

    }
}
