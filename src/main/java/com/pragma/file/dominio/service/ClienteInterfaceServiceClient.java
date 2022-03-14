package com.pragma.file.dominio.service;

import com.pragma.file.dominio.modelo.ClienteDto;

public interface ClienteInterfaceServiceClient {

    ClienteDto findByIdentificacion(Integer identificacion) throws Exception;
}
