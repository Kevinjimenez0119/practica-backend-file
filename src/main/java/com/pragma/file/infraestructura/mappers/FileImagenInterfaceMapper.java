package com.pragma.file.infraestructura.mappers;

import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileImagenInterfaceMapper {

    @Mappings({
            @Mapping(source = "identificacion", target = "identificacion"),
            @Mapping(source = "base64", target = "base64"),
            @Mapping(source = "fileName", target = "fileName"),
            @Mapping(source = "fileType", target = "fileType")
    })
    FileImagenDto toFileImagenDto(FileImagenEntidad fileImagenEntidad);

    List<FileImagenDto> toFileImagenListDto(List<FileImagenEntidad> fileImagenEntidadList);

    @InheritInverseConfiguration
    FileImagenEntidad toFileImagenEntidad(FileImagenDto fileImagenDto);

    @InheritInverseConfiguration
    List<FileImagenEntidad> toFileImagenListEntidad(List<FileImagenDto> fileImagenDtoList);
}
