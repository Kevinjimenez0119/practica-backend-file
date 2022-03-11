package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorClienteClient;
import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.Mensaje;
import com.pragma.file.infraestructura.exceptions.LogicException;
import com.pragma.file.infraestructura.exceptions.RequestException;
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

    @Autowired
    private ManejadorClienteClient manejadorClienteClient;

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
            ) {
        if(manejadorFileImagen.existeFile(fileDto.getIdentificacion()) != true) {
            if(manejadorClienteClient.obtenerCliente(fileDto.getIdentificacion()) !=null) {
                manejadorFileImagen.guardar(fileDto.getIdentificacion(), file);
                return new ResponseEntity<>(new Mensaje("true"), HttpStatus.CREATED);
            } else {
                throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(fileDto.getIdentificacion().toString()));
            }
        } else {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.identificacionYaRegistrada(fileDto.getIdentificacion().toString()));
        }

    }
}
