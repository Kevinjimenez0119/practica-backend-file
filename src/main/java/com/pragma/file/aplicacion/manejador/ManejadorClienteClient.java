package com.pragma.file.aplicacion.manejador;

import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.useCase.clienteClient.ClienteClientUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManejadorClienteClient {

    private final ClienteClientUseCase clienteClientUseCase;

    public ClienteDto obtenerCliente(Integer identificacion) throws Exception {
        return clienteClientUseCase.obtenerCliente(identificacion);
    }
}
