package com.pragma.file.dominio.useCase.fileImagen;

import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
public class FileImagenUseCase {

    private final FileImagenInterfaceService fileImagenInterfaceService;

    public void guardar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenInterfaceService.save(identificacion, file);
    }

    public void actualizar(Integer identificacion, MultipartFile file) throws Exception {
        fileImagenInterfaceService.update(identificacion, file);
    }

    public void eliminar(Integer identificacion) throws Exception {
        fileImagenInterfaceService.delete(identificacion);
    }

    public List<FileImagenDto> listarTodo() throws Exception {
        return fileImagenInterfaceService.findAll();
    }

    public  FileImagenDto obtenerPorIdentificacion(Integer identificacion) throws Exception {
        return fileImagenInterfaceService.findByIdentificacion(identificacion);
    }

    public boolean existeFile(Integer numero) throws Exception {
        return fileImagenInterfaceService.existsByIdentificacion(numero);
    }
}
