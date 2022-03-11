package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.Mensaje;
import com.pragma.file.infraestructura.exceptions.RequestException;
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

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointEliminarFile {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @DeleteMapping("/identificacion/{numero}")
    @ApiOperation("elimina el archivo por identificacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "la identificacion no esta registrada")
    })
    public ResponseEntity<?> eliminar(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) {
        if(manejadorFileImagen.existeFile(numero) == true) {
            manejadorFileImagen.eliminar(numero);
            return new ResponseEntity<>(new Mensaje("se elimino el archivo"), HttpStatus.OK);

        } else {
            throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(numero.toString()));
        }
    }

}
