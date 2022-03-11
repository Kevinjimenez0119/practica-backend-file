package com.pragma.file.infraestructura.configuracion;

import com.pragma.file.aplicacion.manejador.ManejadorClienteClient;
import com.pragma.file.aplicacion.manejador.ManejadorFileImagen;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.dominio.service.FileImagenInterfaceService;
import com.pragma.file.dominio.useCase.clienteClient.ClienteClientUseCase;
import com.pragma.file.dominio.useCase.fileImagen.FileImagenUseCase;
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

    //Bean de ClienteClient
    @Bean
    public ClienteClientUseCase clienteClientUseCase(ClienteInterfaceServiceClient clienteInterfaceServiceClient) {
        return new ClienteClientUseCase(clienteInterfaceServiceClient);
    }

    @Bean
    public ManejadorClienteClient manejadorClienteClient(ClienteClientUseCase clienteClientUseCase) {
        return new ManejadorClienteClient(clienteClientUseCase);
    }
}
