package com.pragma.file.infraestructura.persistencia.service.impl;

import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.infraestructura.mappers.FileImagenInterfaceMapper;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import com.pragma.file.infraestructura.persistencia.repository.FileImagenInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileImagenServiceImpl implements FileImagenInterfaceService {

    Logger logger = LoggerFactory.getLogger(FileImagenServiceImpl.class);

    @Autowired
    private FileImagenInterfaceRepository fileImagenInterfaceRepository;

    @Autowired
    private FileImagenInterfaceMapper fileImagenInterfaceMapper;

    @Override
    public List<FileImagenDto> findAll() {
        return fileImagenInterfaceMapper.toFileImagenListDto(fileImagenInterfaceRepository.findAll());
    }

    @Override
    public boolean save(Integer identificacion, MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        //creando salvando el objeto.
        FileImagenEntidad fileImagenEntidad = FileImagenEntidad.builder()
                .identificacion(identificacion)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .base64(encodedString)
                .build();
        fileImagenInterfaceRepository.save(fileImagenEntidad);
        return true;
    }

    @Override
    public boolean delete(Integer identificacion) {
        FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
        fileImagenInterfaceRepository.delete(fileImagenEntidad);
        return true;
    }

    @Override
    public boolean update(Integer identificacion, MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
        fileImagenEntidad.setFileName(file.getOriginalFilename());
        fileImagenEntidad.setFileType(file.getContentType());
        fileImagenEntidad.setBase64(encodedString);
        fileImagenEntidad.setIdentificacion(identificacion);
        fileImagenInterfaceRepository.save(fileImagenEntidad);
        return true;
    }

    @Override
    public FileImagenDto findByIdentificacion(Integer numero) {
        return fileImagenInterfaceMapper.toFileImagenDto(fileImagenInterfaceRepository.findByIdentificacion(numero));
    }

    @Override
    public FileImagenDto findById(Integer id) {
        return fileImagenInterfaceMapper.toFileImagenDto(fileImagenInterfaceRepository.findById(id).get());
    }

    @Override
    public boolean existsByIdentificacion(Integer numero) {
        return fileImagenInterfaceRepository.existsByIdentificacion(numero);
    }
}
