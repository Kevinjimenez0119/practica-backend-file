package com.pragma.file.infraestructura.clientefeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@Component
@FeignClient(name = "FileImagenClient", url = "http://localhost:8080/api/clientes")
public interface ClienteFeignInterfaceClient {

    @GetMapping(value = "/identificacion/{numero}")
    ResponseEntity<Map<String, Object>> findByNumeroIdentificacion(@PathVariable Integer numero);

}
