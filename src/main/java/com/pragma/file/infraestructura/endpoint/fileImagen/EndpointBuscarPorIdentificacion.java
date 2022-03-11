package com.pragma.file.infraestructura.endpoint.fileImagen;

import com.pragma.file.aplicacion.manejador.ManejadorClienteClient;
import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
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
public class EndpointBuscarPorIdentificacion {

    @Autowired
    private ManejadorFileImagen manejadorFileImagen;

    @Autowired
    private ManejadorClienteClient manejadorClienteClient;

    @GetMapping("/identificacion/{numero}")
    @ApiOperation("obtiene un archivo por identificacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = FileDto.class),
            @ApiResponse(code = 204, message = "la identificacion no tiene ningun archivo"),
            @ApiResponse(code = 404, message = "la identificacion no esta registrada")
    })
    public ResponseEntity<?> obtenerPorIdentificacion(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) {
        if(manejadorFileImagen.existeFile(numero) == true)
        {
            FileImagenDto fileImagenDto = manejadorFileImagen.obtenerPorIdentificacion(numero);
            return new ResponseEntity(fileImagenDto, HttpStatus.OK);

        }else{
            ClienteDto clienteDto= manejadorClienteClient.obtenerCliente(numero);
            if(clienteDto != null) {
                throw new RequestException("code", HttpStatus.NO_CONTENT, ErrorsUtils.identificacionYaRegistradaSinFile(numero.toString()));
            } else {
                throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(numero.toString()));
            }

        }
    }

}
