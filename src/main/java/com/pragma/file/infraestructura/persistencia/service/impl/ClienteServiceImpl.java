package com.pragma.file.infraestructura.persistencia.service.impl;

import com.pragma.file.aplicacion.utils.ErrorsUtils;
import com.pragma.file.dominio.modelo.ClienteDto;
import com.pragma.file.dominio.service.ClienteInterfaceServiceClient;
import com.pragma.file.infraestructura.clientefeign.ClienteFeignInterfaceClient;
import com.pragma.file.infraestructura.exceptions.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteInterfaceServiceClient {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteFeignInterfaceClient clienteFeignInterfaceClient;

    @Override
    public ClienteDto findByIdentificacion(Integer identificacion) throws Exception {
        ResponseEntity<Map<String, Object>> clienteResponseEntity = clienteFeignInterfaceClient.findByNumeroIdentificacion(identificacion);
        if(clienteResponseEntity.getStatusCodeValue() !=200) {
            throw new RequestException(404, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
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
