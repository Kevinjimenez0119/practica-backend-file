package com.pragma.file.dominio.useCase.clienteClient;

import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.infraestructura.clientefeign.ClienteFeignInterfaceClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClienteClientUseCase {

    private final ClienteInterfaceServiceClient clienteInterfaceServiceClient;

    public ClienteDto obtenerCliente(Integer identificacion) throws Exception {
        return clienteInterfaceServiceClient.findByIdentificacion(identificacion);
    }
}
