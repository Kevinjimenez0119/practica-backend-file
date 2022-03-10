package com.pragma.file.dominio.service;

import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileImagenInterfaceService {

    List<FileImagenDto> findAll();

    void save(Integer identificacion, MultipartFile file);

    void delete(Integer identificacion);

    void update(Integer identificacion, MultipartFile file);

    FileImagenDto findByIdentificacion(Integer numero);

    FileImagenDto findById(Integer id);

    boolean existsByIdentificacion(Integer numero);
}
