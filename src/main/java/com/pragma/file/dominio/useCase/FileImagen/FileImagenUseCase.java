package com.pragma.file.dominio.useCase.FileImagen;

import com.pragma.file.dominio.modelo.FileDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
public class FileImagenUseCase {

    private final FileImagenInterfaceService fileImagenInterfaceService;

    public void guardar(Integer identificacion, MultipartFile file) {
        fileImagenInterfaceService.save(identificacion, file);
    }

    public void actualizar(Integer identificacion, MultipartFile file) {
        fileImagenInterfaceService.update(identificacion, file);
    }

    public void eliminar(Integer identificacion) {
        fileImagenInterfaceService.delete(identificacion);
    }

    public List<FileImagenDto> listarTodo() {
        return fileImagenInterfaceService.findAll();
    }

    public  FileImagenDto obtenerPorIdentificacion(Integer identificacion) {
        return fileImagenInterfaceService.findByIdentificacion(identificacion);
    }

    public boolean existeFile(Integer numero)
    {
        return fileImagenInterfaceService.existsByIdentificacion(numero);
    }
}
