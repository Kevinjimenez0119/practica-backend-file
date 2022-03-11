package com.pragma.file.infraestructura.persistencia.service.impl;

import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.infraestructura.clientefeign.ClienteFeignInterfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteInterfaceServiceClient {

    @Autowired
    private ClienteFeignInterfaceClient clienteFeignInterfaceClient;

    @Override
    public ClienteDto findByIdentificacion(Integer identificacion) {
        ResponseEntity<Map<String, Object>> clienteResponseEntity = clienteFeignInterfaceClient.findByNumeroIdentificacion(identificacion);
        if (clienteResponseEntity.getStatusCodeValue() != 200) {
            return null;
        }
        ClienteDto clienteDto = maptoClienteDto(clienteResponseEntity.getBody());
        return clienteDto;
    }

    public ClienteDto maptoClienteDto(Map<String, Object> clienteMap) {
        ClienteDto clienteDto = ClienteDto.builder()
                .id(Integer.parseInt(clienteMap.get("id").toString()))
                .nombres(clienteMap.get("nombres").toString())
                .apellidos(clienteMap.get("apellidos").toString())
                .identificacion(Integer.parseInt(clienteMap.get("identificacion").toString()))
                .ciudadNacimiento(clienteMap.get("ciudadNacimiento").toString())
                .tipoDocumento(clienteMap.get("tipoDocumento").toString())
                .edad(Integer.parseInt(clienteMap.get("edad").toString()))
                .build();
        return clienteDto;
    }
}
