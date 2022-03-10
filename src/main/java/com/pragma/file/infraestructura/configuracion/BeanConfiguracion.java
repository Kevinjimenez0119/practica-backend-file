package com.pragma.file.infraestructura.configuracion;

import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.dominio.useCase.FileImagen.FileImagenUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguracion {

    //Bean de FileImagen
    @Bean
    public FileImagenUseCase fileImagenUseCase(FileImagenInterfaceService fileImagenInterfaceService) {
        return new FileImagenUseCase(fileImagenInterfaceService);
    }

    @Bean
    public ManejadorFileImagen manejadorFileImagen(FileImagenUseCase fileImagenUseCase) {
        return new ManejadorFileImagen(fileImagenUseCase);
    }
}
