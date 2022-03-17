package com.pragma.file.dominio.useCase.fileImagen;

import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
public class FileImagenUseCase {

    private final FileImagenInterfaceService fileImagenInterfaceService;

    public boolean guardar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenInterfaceService.save(identificacion, file);
        return true;
    }

    public boolean actualizar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenInterfaceService.update(identificacion, file);
        return true;
    }

    public boolean eliminar(Integer identificacion) throws Exception {
        fileImagenInterfaceService.delete(identificacion);
        return true;
    }

    public List<FileImagenDto> listarTodo() throws Exception {
        return fileImagenInterfaceService.findAll();
    }

    public  FileImagenDto obtenerPorIdentificacion(Integer identificacion) throws Exception {
        return fileImagenInterfaceService.findByIdentificacion(identificacion);
    }
}
