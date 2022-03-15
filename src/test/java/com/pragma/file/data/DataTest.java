package com.pragma.file.data;

import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.infraestructura.persistencia.entity.FileImagenEntidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataTest {

    public static FileImagenDto fileImagenDto() {
        return new FileImagenDto("base1", 100505349, "image1", "jpg");
    }

    public static FileImagenEntidad fileImagenEntidad() {
        return new FileImagenEntidad("1",100505349, "base1", "image1", "jpg");
    }

    public static ClienteDto cliente1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ClienteDto(5, "kevin2", "jimenez", "CC", 100505349, 21, "Cucuta", sdf.parse("2001-01-19"));
    }
}
