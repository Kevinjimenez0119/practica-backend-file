package com.pragma.file.infraestructura.persistencia.service.impl;

import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.infraestructura.exceptions.LogicException;
import com.pragma.file.infraestructura.exceptions.RequestException;
import com.pragma.file.infraestructura.mappers.FileImagenInterfaceMapper;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import com.pragma.file.infraestructura.persistencia.repository.FileImagenInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ClienteInterfaceServiceClient clienteInterfaceServiceClient;


    @Override
    public List<FileImagenDto> findAll() throws Exception{
        List<FileImagenDto> fileImagenDtoList =  fileImagenInterfaceMapper.toFileImagenListDto(fileImagenInterfaceRepository.findAll());
        if(fileImagenDtoList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.NO_CONTENT, ErrorsUtils.sinRegistros());
        }
        return fileImagenInterfaceMapper.toFileImagenListDto(fileImagenInterfaceRepository.findAll());
    }

    @Override
    public void save(Integer identificacion, MultipartFile file) throws Exception {
        if(!fileImagenInterfaceRepository.existsByIdentificacion(identificacion)) {
            if (clienteInterfaceServiceClient.findByIdentificacion(identificacion) != null) {
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
            } else {
                throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
            }
        } else {
            throw new RequestException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.identificacionYaRegistrada(identificacion.toString()));
        }
    }

    @Override
    public void delete(Integer identificacion) throws Exception{
        if(existsByIdentificacion(identificacion)) {
            FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
            fileImagenInterfaceRepository.delete(fileImagenEntidad);
        }
    }

    @Override
    public void update(Integer identificacion, MultipartFile file) throws Exception {
        if(existsByIdentificacion(identificacion)) {
            byte[] bytes = file.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(bytes);
            FileImagenEntidad fileImagenEntidad = fileImagenInterfaceRepository.findByIdentificacion(identificacion);
            fileImagenEntidad.setFileName(file.getOriginalFilename());
            fileImagenEntidad.setFileType(file.getContentType());
            fileImagenEntidad.setBase64(encodedString);
            fileImagenEntidad.setIdentificacion(identificacion);
            fileImagenInterfaceRepository.save(fileImagenEntidad);
        }
    }

    @Override
    public FileImagenDto findByIdentificacion(Integer numero) throws Exception{
        if(fileImagenInterfaceRepository.existsByIdentificacion(numero)) {
            return fileImagenInterfaceMapper.toFileImagenDto(fileImagenInterfaceRepository.findByIdentificacion(numero));
        } else {
            ClienteDto clienteDto= clienteInterfaceServiceClient.findByIdentificacion(numero);
            if(clienteDto != null) {
                throw new RequestException("code", HttpStatus.NO_CONTENT, ErrorsUtils.identificacionYaRegistradaSinFile(numero.toString()));
            }
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
    public boolean existsByIdentificacion(Integer numero) throws Exception {
        if(fileImagenInterfaceRepository.existsByIdentificacion(numero)) {
            return true;
        } else {
            throw new LogicException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionYaRegistrada(numero.toString()));
        }
    }
}
