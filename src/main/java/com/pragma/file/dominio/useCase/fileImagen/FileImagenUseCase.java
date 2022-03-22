package com.pragma.file.dominio.useCase.fileImagen;

import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.modelo.FileImagenDto;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.dominio.useCase.clienteClient.ClienteClientUseCase;
import com.pragma.file.infraestructura.exceptions.LogicException;
import com.pragma.file.infraestructura.exceptions.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
public class FileImagenUseCase {

    private final FileImagenInterfaceService fileImagenInterfaceService;

    private final ClienteClientUseCase clienteClientUseCase;

    public boolean guardar(Integer identificacion, MultipartFile file) throws Exception {
        if(!fileImagenInterfaceService.existsByIdentificacion(identificacion)) {
            if (clienteClientUseCase.obtenerCliente(identificacion) != null) {
                return fileImagenInterfaceService.save(identificacion, file);
            } else {
                throw new RequestException(404, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
            }
        } else {
            throw new RequestException(400, ErrorsUtils.identificacionYaRegistrada(identificacion.toString()));
        }
    }

    public boolean actualizar(Integer identificacion, MultipartFile file) throws Exception {
        existsByIdentificacion(identificacion);
        return fileImagenInterfaceService.update(identificacion, file);
    }

    public boolean eliminar(Integer identificacion) throws Exception {
        existsByIdentificacion(identificacion);
        return fileImagenInterfaceService.delete(identificacion);
    }

    public List<FileImagenDto> listarTodo() throws Exception {
        List<FileImagenDto> fileImagenDtoList = fileImagenInterfaceService.findAll();
        if(fileImagenDtoList.isEmpty())
        {
            throw new LogicException(204, ErrorsUtils.sinRegistros());
        }
        return fileImagenDtoList;
    }

    public FileImagenDto obtenerPorIdentificacion(Integer identificacion) throws Exception {
        if(fileImagenInterfaceService.existsByIdentificacion(identificacion)) {
            return fileImagenInterfaceService.findByIdentificacion(identificacion);
        } else {
            ClienteDto clienteDto= clienteClientUseCase.obtenerCliente(identificacion);
            if(clienteDto != null) {
                throw new RequestException(204, ErrorsUtils.identificacionYaRegistradaSinFile(identificacion.toString()));
            } else {
                throw new RequestException(404, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
            }
        }
    }

    public boolean existsByIdentificacion(Integer identificacion) throws Exception {
        if(fileImagenInterfaceService.existsByIdentificacion(identificacion)) {
            return true;
        } else {
            throw new LogicException(404, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
        }
    }
}
