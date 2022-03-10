package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.modelo.Mensaje;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointListarFiles {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @GetMapping("/listAll")
    @ApiOperation("obtiene una lista de todos los archivos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = FileImagenDto.class),
            @ApiResponse(code = 204, message = "no hay ningun archivo registrado")
    })
    public ResponseEntity<?> listarFiles() {
        List<FileImagenDto> fileList = manejadorFileImagen.listarTodo();
        if(fileList.isEmpty())
        {
            return new ResponseEntity<>(new Mensaje("No hay registros"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }
}
