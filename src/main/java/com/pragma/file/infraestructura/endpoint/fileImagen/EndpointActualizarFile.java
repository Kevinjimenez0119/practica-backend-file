package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.Mensaje;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
            @ApiResponse(code = 404, message = "no se encontro el archivo")
    })
    public ResponseEntity<?> actualizar(
            @RequestPart Integer identificacion,
            @RequestParam("file") MultipartFile file
    ) {
        if(manejadorFileImagen.existeFile(identificacion) == true) {
            manejadorFileImagen.actualizar(identificacion, file);
            return new ResponseEntity<>(new Mensaje("archivo del cliente " + identificacion + " actualizado"), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Mensaje("el cliente con la identificacion" + identificacion + " no tiene imagen"), HttpStatus.NOT_FOUND);
        }
    }
}
