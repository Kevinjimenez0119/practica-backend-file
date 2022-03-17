package com.pragma.file.endpoints;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.data.DataTest;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.modelo.Mensaje;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
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
public class ManejadorTest {

    @InjectMocks
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
        when(fileImagenUseCase.obtenerPorIdentificacion(fileImagenDto.getIdentificacion())).thenReturn(fileImagenDto);

        FileImagenDto fileImagenDtoActual = manejadorFileImagen.obtenerPorIdentificacion(fileImagenDto.getIdentificacion());

        assertEquals(fileImagenDtoActual, fileImagenDtoActual);
    }

    @Test
    void findAll() throws Exception {
        List<FileImagenDto> fileImagenDtoList = new ArrayList<>();
        fileImagenDtoList.add(fileImagenDto);

        when(fileImagenUseCase.listarTodo()).thenReturn(fileImagenDtoList);

        List<FileImagenDto> fileImagenDtoListActual = manejadorFileImagen.listarTodo();

        assertEquals(fileImagenDtoList, fileImagenDtoListActual);
    }
    @Test
    void save() throws Exception {
        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        when(fileImagenUseCase.guardar(fileDto.getIdentificacion(), file)).thenReturn(true);

        boolean response = manejadorFileImagen.guardar(fileDto.getIdentificacion(), file);

        assertEquals(true, response);
    }

    @Test
    void delete() throws Exception {
        when(fileImagenUseCase.eliminar(fileImagenDto.getIdentificacion())).thenReturn(true);

        boolean response = manejadorFileImagen.eliminar(fileImagenDto.getIdentificacion());

        assertEquals(true, response);
    }

    @Test
    void update() throws Exception {
        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        when(fileImagenUseCase.actualizar(fileDto.getIdentificacion(), file)).thenReturn(true);

        boolean response = manejadorFileImagen.actualizar(fileDto.getIdentificacion(), file);

        assertEquals(true, response);
    }

}
