package com.pragma.file.aplicacion.manejador;

import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ManejadorFileImagen {

    private final FileImagenUseCase fileImagenUseCase;

    public void guardar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenUseCase.guardar(identificacion, file);
    }

    public void actualizar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenUseCase.actualizar(identificacion, file);
    }

    public void eliminar(Integer identificacion) throws Exception {
        fileImagenUseCase.eliminar(identificacion);
    }

    public List<FileImagenDto> listarTodo() throws Exception {
        return fileImagenUseCase.listarTodo();
    }

    public  FileImagenDto obtenerPorIdentificacion(Integer identificacion) throws Exception {
        return fileImagenUseCase.obtenerPorIdentificacion(identificacion);
    }

    public boolean existeFile(Integer numero) throws Exception {
        return fileImagenUseCase.existeFile(numero);
    }
}
