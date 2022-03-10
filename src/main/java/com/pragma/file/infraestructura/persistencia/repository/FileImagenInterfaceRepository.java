package com.pragma.file.infraestructura.persistencia.repository;

import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileImagenInterfaceRepository extends MongoRepository<FileImagenEntidad, Integer> {

    Optional<FileImagenEntidad> findById(Integer id);

    FileImagenEntidad findByIdentificacion(Integer numero);

    boolean existsByIdentificacion(Integer numero);

    List<FileImagenEntidad> findByIdentificacionOrderByIdentificacion(Integer numero);
}
