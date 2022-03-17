package com.pragma.file.dominio.service;

import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileImagenInterfaceService {

    List<FileImagenDto> findAll() throws Exception;

    boolean save(Integer identificacion, MultipartFile file) throws IOException, Exception;

    boolean delete(Integer identificacion) throws Exception;

    boolean update(Integer identificacion, MultipartFile file) throws IOException, Exception;

    FileImagenDto findByIdentificacion(Integer numero) throws Exception;

    FileImagenDto findById(Integer id);

    boolean existsByIdentificacion(Integer numero) throws Exception;
}
