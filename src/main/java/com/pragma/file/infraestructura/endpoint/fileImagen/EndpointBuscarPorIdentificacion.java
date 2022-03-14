package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorClienteClient;
import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
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
public class EndpointBuscarPorIdentificacion {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @GetMapping("/identificacion/{numero}")
    @ApiOperation("obtiene un archivo por identificacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = FileDto.class),
            @ApiResponse(code = 204, message = "la identificacion no tiene ningun archivo"),
            @ApiResponse(code = 404, message = "la identificacion no esta registrada")
    })
    public ResponseEntity<FileImagenDto> obtenerPorIdentificacion(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {
        FileImagenDto fileImagenDto = manejadorFileImagen.obtenerPorIdentificacion(numero);
        return new ResponseEntity(fileImagenDto, HttpStatus.OK);
    }

}
