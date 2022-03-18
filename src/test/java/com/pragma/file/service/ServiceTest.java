package com.pragma.file.service;


import com.pragma.file.data.DataTest;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.dominio.useCase.clienteClient.ClienteClientUseCase;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
import com.pragma.file.infraestructura.exceptions.LogicException;
import com.pragma.file.infraestructura.exceptions.RequestException;
import com.pragma.file.infraestructura.mappers.FileImagenInterfaceMapper;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import com.pragma.file.infraestructura.persistencia.repository.FileImagenInterfaceRepository;
import com.pragma.file.infraestructura.persistencia.service.impl.FileImagenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    FileImagenUseCase fileImagenUseCase;

    @Mock
    FileImagenInterfaceService fileImagenInterfaceService;

    @Mock
    ClienteClientUseCase clienteClientUseCase;

    @InjectMocks
    FileImagenServiceImpl fileImagenService;

    @Mock
    FileImagenInterfaceRepository fileImagenInterfaceRepository;

    @Mock
    FileImagenInterfaceMapper fileImagenInterfaceMapper;

    @Mock
    ClienteInterfaceServiceClient clienteInterfaceServiceClient;

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
    void findAll() throws Exception {
        List<FileImagenDto> fileImagenDtoList = new ArrayList<>();
        fileImagenDtoList.add(fileImagenDto);

        List<FileImagenEntidad> fileImagenEntidadList = new ArrayList<>();
        fileImagenEntidadList.add(fileImagenEntidad);

        when(fileImagenInterfaceRepository.findAll()).thenReturn(fileImagenEntidadList);

        when(fileImagenInterfaceMapper.toFileImagenListDto(fileImagenEntidadList)).thenReturn(fileImagenDtoList);

        List<FileImagenDto> listaActual = fileImagenService.findAll();

        assertEquals(fileImagenDtoList, listaActual);

        assertEquals(1, listaActual.size());

        when(fileImagenService.findAll()).thenReturn(fileImagenDtoList);

        when(fileImagenInterfaceService.findAll()).thenReturn(fileImagenDtoList);

        List<FileImagenDto> fileImagenDtoListActual = fileImagenUseCase.listarTodo();

        assertEquals(fileImagenDtoList, fileImagenDtoListActual);
    }

    @Test
    void findAllException() throws Exception {
        List<FileImagenEntidad> fileImagenEntidadList = new ArrayList<>();

        List<FileImagenDto> fileImagenDtoList = new ArrayList<>();

        when(fileImagenInterfaceMapper.toFileImagenListDto(fileImagenEntidadList)).thenReturn(fileImagenDtoList);

        when(fileImagenInterfaceRepository.findAll()).thenReturn(fileImagenEntidadList);

        when(fileImagenInterfaceService.findAll()).thenReturn(fileImagenDtoList);

        assertThrows(LogicException.class, () -> fileImagenUseCase.listarTodo());
    }

    @Test
    void findByIdentificacion() throws Exception {
        when(fileImagenInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenEntidad);

        when(fileImagenInterfaceMapper.toFileImagenDto(fileImagenEntidad)).thenReturn(fileImagenDto);

        when(clienteInterfaceServiceClient.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        FileImagenDto fileActual = fileImagenService.findByIdentificacion(clienteDto.getIdentificacion());

        assertEquals(fileImagenDto, fileActual);

        when(fileImagenService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenService.findByIdentificacion(fileImagenDto.getIdentificacion())).thenReturn(fileImagenDto);

        when(fileImagenInterfaceService.findByIdentificacion(fileImagenDto.getIdentificacion())).thenReturn(fileImagenDto);

        FileImagenDto fileImagenDtoActual = fileImagenUseCase.obtenerPorIdentificacion(fileImagenDto.getIdentificacion());

        assertEquals(fileImagenDtoActual, fileImagenDtoActual);
    }

    @Test
    void findById() throws Exception {
        when(fileImagenInterfaceRepository.findById(fileImagenDto.getIdentificacion())).thenReturn(Optional.ofNullable(fileImagenEntidad));

        when(fileImagenInterfaceMapper.toFileImagenDto(fileImagenEntidad)).thenReturn(fileImagenDto);

        FileImagenDto fileImagenDtoActual = fileImagenService.findById(fileImagenDto.getIdentificacion());

        assertEquals(fileImagenDto, fileImagenDtoActual);
    }

    @Test
    void findByIdentificacionExceptionExist() throws Exception {
        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        when(clienteClientUseCase.obtenerCliente(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        assertThrows(RequestException.class, () -> fileImagenUseCase.obtenerPorIdentificacion(clienteDto.getIdentificacion()));

    }

    @Test
    void ExceptionExist() throws Exception {
        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        assertThrows(LogicException.class, () -> fileImagenUseCase.existsByIdentificacion(clienteDto.getIdentificacion()));

    }

    @Test
    void save() throws Exception {
        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        fileImagenInterfaceRepository.save(fileImagenEntidad);

        boolean response = fileImagenService.save(fileDto.getIdentificacion(), file);

        assertEquals(true, response);

        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        when(clienteClientUseCase.obtenerCliente(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        when(fileImagenService.save(fileDto.getIdentificacion(), file)).thenReturn(true);

        when(fileImagenInterfaceService.save(fileDto.getIdentificacion(), file)).thenReturn(true);

        boolean responseActual = fileImagenUseCase.guardar(fileDto.getIdentificacion(), file);

        assertEquals(true, responseActual);
    }

    @Test
    void saveExceptionExist() throws Exception {
        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        assertThrows(RequestException.class, () -> fileImagenUseCase.guardar(fileImagenDto.getIdentificacion(), file));
    }

    @Test
    void saveExceptionExistCliente() throws Exception {
        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        when(clienteClientUseCase.obtenerCliente(clienteDto.getIdentificacion())).thenReturn(clienteDto = null);

        assertThrows(RequestException.class, () -> fileImagenUseCase.guardar(fileImagenDto.getIdentificacion(), file));
    }

    @Test
    void delete() throws Exception {
        when(fileImagenInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenEntidad);

        fileImagenInterfaceRepository.delete(fileImagenEntidad);

        boolean response = fileImagenService.delete(clienteDto.getIdentificacion());

        assertEquals(true, response);

        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenUseCase.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenInterfaceService.delete(fileImagenDto.getIdentificacion())).thenReturn(true);

        boolean responseActual = fileImagenUseCase.eliminar(fileImagenDto.getIdentificacion());

        assertEquals(true, responseActual);
    }

    @Test
    void update() throws Exception {
        FileDto fileDto = FileDto.builder().identificacion(fileImagenDto.getIdentificacion()).build();

        when(fileImagenInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenEntidad);

        fileImagenInterfaceRepository.save(fileImagenEntidad);

        boolean response = fileImagenService.update(fileDto.getIdentificacion(), file);

        assertEquals(true, response);

        when(fileImagenInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenUseCase.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenService.update(fileDto.getIdentificacion(), file)).thenReturn(true);

        when(fileImagenInterfaceService.update(fileDto.getIdentificacion(), file)).thenReturn(true);

        boolean responseActual = fileImagenUseCase.actualizar(fileDto.getIdentificacion(), file);

        assertEquals(true, responseActual);
    }
}
