package com.pragma.file.infraestructura.persistencia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "fileImagen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileImagenEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field
    private Integer identificacion;

    @Field
    private String base64;

    @Field
    private String fileName;

    @Field
    private String fileType;
}
