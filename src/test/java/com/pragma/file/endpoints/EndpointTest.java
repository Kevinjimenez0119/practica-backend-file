package com.pragma.file.endpoints;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.data.DataTest;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.modelo.Mensaje;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
import com.pragma.file.infraestructura.endpoint.fileImagen.*;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EndpointTest {

    @InjectMocks
    EndpointBuscarPorIdentificacion endpointBuscarPorIdentificacion;

    @InjectMocks
    EndpointListarFiles endpointListarFiles;

    @InjectMocks
    EndpointGuardarFile endpointGuardarFile;

    @InjectMocks
    EndpointEliminarFile endpointEliminarFile;

    @InjectMocks
    EndpointActualizarFile endpointActualizarFile;

    @Mock
    ManejadorFileImagen manejadorFileImagen;

    @Mock
    FileImagenUseCase fileImagenUseCase;

    FileImagenDto fileImagenDto;
    FileImagenEntidad fileImagenEntidad;
    ClienteDto clienteDto;
    MockMultipartFile file;

    @BeforeEach
    void setUp() throws ParseException {
        fileImagenEntidad = DataTest.fileImagenEntidad();
        fileImagenDto = DataTest.fileImagenDto();
        clienteDto = DataTest.cliente1();
        file = new MockMultipartFile(fileImagenDto.getFileName(), fileImagenDto.getFileName(), fileImagenDto.getFileType(), fileImagenDto.getBase64().getBytes());
    }

    @Test
    void findByIdentificacion() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(fileImagenDto, HttpStatus.OK);

        when(fileImagenUseCase.obtenerPorIdentificacion(fileImagenDto.getIdentificacion())).thenReturn(fileImagenDto);

        when(manejadorFileImagen.obtenerPorIdentificacion(fileImagenDto.getIdentificacion())).thenReturn(fileImagenDto);

        ResponseEntity<?> response = endpointBuscarPorIdentificacion.obtenerPorIdentificacion(fileImagenDto.getIdentificacion());

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void findAll() throws Exception {
        List<FileImagenDto> fileImagenDtoList = new ArrayList<>();
        fileImagenDtoList.add(fileImagenDto);

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(fileImagenDtoList, HttpStatus.OK);

        when(fileImagenUseCase.listarTodo()).thenReturn(fileImagenDtoList);

        when(manejadorFileImagen.listarTodo()).thenReturn(fileImagenDtoList);

        ResponseEntity<?> response = endpointListarFiles.listarFiles();

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void save() throws Exception {
        Mensaje mensaje = new Mensaje("true");

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(mensaje, HttpStatus.CREATED);

        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        ResponseEntity<?> response = endpointGuardarFile.guardarFile(file, fileDto);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void delete() throws Exception {
        Mensaje mensaje = new Mensaje("se elimino el archivo");

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(mensaje, HttpStatus.OK);

        ResponseEntity<?> response = endpointEliminarFile.eliminar(fileImagenDto.getIdentificacion());

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void update() throws Exception {
        Mensaje mensaje = new Mensaje("archivo del cliente " + fileImagenDto.getIdentificacion() + " actualizado");

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(mensaje, HttpStatus.OK);

        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        ResponseEntity<?> response = endpointActualizarFile.actualizar(fileDto, file);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }
}
