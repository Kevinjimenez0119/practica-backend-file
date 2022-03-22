package com.pragma.file.dominio.service;

import com.pragma.file.dominio.modelo.FileImagenDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileImagenInterfaceService {

    List<FileImagenDto> findAll();

    boolean save(Integer identificacion, MultipartFile file) throws Exception;

    boolean delete(Integer identificacion);

    boolean update(Integer identificacion, MultipartFile file) throws Exception;

    FileImagenDto findByIdentificacion(Integer numero);

    FileImagenDto findById(Integer id);

    boolean existsByIdentificacion(Integer numero);
}
