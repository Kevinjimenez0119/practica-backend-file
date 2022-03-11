package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.ClienteDto;
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
    ) {
        if(manejadorFileImagen.existeFile(fileDto.getIdentificacion()) == true) {
            manejadorFileImagen.actualizar(fileDto.getIdentificacion(), file);
            return new ResponseEntity<>(new Mensaje("archivo del cliente " + fileDto.getIdentificacion() + " actualizado"), HttpStatus.OK);
        }else {
            throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(fileDto.getIdentificacion().toString()));
        }
    }
}
