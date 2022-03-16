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
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "la identificacion ya tiene un archivo"),
            @ApiResponse(code = 404, message = "la identificacion no esta registrada")
    })
    public ResponseEntity<?> guardarFile(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("cliente") FileDto fileDto
            ) throws Exception {
                manejadorFileImagen.guardar(fileDto.getIdentificacion(), file);
                return new ResponseEntity<>(new Mensaje("true"), HttpStatus.CREATED);
    }
}
