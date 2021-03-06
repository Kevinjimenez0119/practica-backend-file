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
public class EndpointActualizarFile {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @PutMapping()
    @ApiOperation("actualiza un cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "la identificacion no esta registrada")
    })
    public ResponseEntity<?> actualizar(
            @ModelAttribute("cliente") FileDto fileDto,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        manejadorFileImagen.actualizar(fileDto.getIdentificacion(), file);
        return new ResponseEntity<>(new Mensaje("archivo del cliente " + fileDto.getIdentificacion() + " actualizado"), HttpStatus.OK);
    }
}
