package com.pragma.file.infraestructura.persistencia.service.impl;

import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.infraestructura.mappers.FileImagenInterfaceMapper;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import com.pragma.file.infraestructura.persistencia.repository.FileImagenInterfaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class FileImagenServiceImpl implements FileImagenInterfaceService {

    Logger logger = LoggerFactory.getLogger(FileImagenServiceImpl.class);

    @Autowired
    private FileImagenInterfaceRepository fileImagenInterfaceRepository;

    @Autowired
    private FileImagenInterfaceMapper fileImagenInterfaceMapper;


    @Override
    public List<FileImagenDto> findAll() {
        try {
            return fileImagenInterfaceMapper.toFileImagenListDto(fileImagenInterfaceRepository.findAll());
        } catch (Exception e) {
            logger.error("Listar todos las fotos", e);
        }
        return new ArrayList<>();
    }

    @Override
    public void save(Integer identificacion, MultipartFile file) {
        try {
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
        } catch (Exception e) {
            logger.error("Error al registrar la foto", e);
        }
    }

    @Override
    public void delete(Integer identificacion) {
        try {
            FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
            fileImagenInterfaceRepository.delete(fileImagenEntidad);
        } catch (Exception e) {
            logger.error("Error al eliminar la foto", e);
        }
    }

    @Override
    public void update(Integer identificacion, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(bytes);
            FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
            fileImagenEntidad.setFileName(file.getOriginalFilename());
            fileImagenEntidad.setFileType(file.getContentType());
            fileImagenEntidad.setBase64(encodedString);
            fileImagenEntidad.setIdentificacion(identificacion);
            fileImagenInterfaceRepository.save(fileImagenEntidad);
        } catch (Exception e) {
            logger.error("Error al actualizar la foto", e);
        }
    }

    @Override
    public FileImagenDto findByIdentificacion(Integer numero) {
        try {
            return fileImagenInterfaceMapper.toFileImagenDto(fileImagenInterfaceRepository.findByIdentificacion(numero));
        } catch (Exception e) {
            logger.error("Error en la busqueda de numero identificacion", e);
        }
        return null;
    }

    @Override
    public FileImagenDto findById(Integer id) {
        try {
            return fileImagenInterfaceMapper.toFileImagenDto(fileImagenInterfaceRepository.findById(id).get());
        } catch (Exception e) {
            logger.error("Error en la busqueda de numero identificacion", e);
        }
        return null;
    }

    @Override
    public boolean existsByIdentificacion(Integer numero) {
        try {
            return fileImagenInterfaceRepository.existsByIdentificacion(numero);
        } catch (Exception e) {
            logger.error("Error al buscar file por identificacion exist", e);
        }
        return false;
    }
}
