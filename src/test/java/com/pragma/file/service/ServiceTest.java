package com.pragma.file.service;


import com.pragma.file.data.DataTest;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
import com.pragma.file.infraestructura.exceptions.LogicException;
import com.pragma.file.infraestructura.exceptions.RequestException;
import com.pragma.file.infraestructura.mappers.FileImagenInterfaceMapper;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import com.pragma.file.infraestructura.persistencia.repository.FileImagenInterfaceRepository;
import com.pragma.file.infraestructura.persistencia.service.impl.ClienteServiceImpl;
import com.pragma.file.infraestructura.persistencia.service.impl.FileImagenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

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

    @BeforeEach
    void setUp() throws ParseException {
        fileImagenEntidad = DataTest.fileImagenEntidad();
        fileImagenDto = DataTest.fileImagenDto();
        clienteDto = DataTest.cliente1();
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
    }

    @Test
    void findAllException() throws Exception {

        when(fileImagenInterfaceRepository.findAll()).thenThrow(LogicException.class);

        assertThrows(LogicException.class, () -> fileImagenService.findAll());
    }

    @Test
    void findByIdentificacion() throws Exception {

        when(fileImagenInterfaceRepository.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(fileImagenInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenEntidad);

        when(fileImagenInterfaceMapper.toFileImagenDto(fileImagenEntidad)).thenReturn(fileImagenDto);

        when(clienteInterfaceServiceClient.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        FileImagenDto fileActual = fileImagenService.findByIdentificacion(clienteDto.getIdentificacion());

        assertEquals(fileImagenDto, fileActual);
    }

}
