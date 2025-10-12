package org.cibertec.client;



import java.util.List;

import org.cibertec.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



@FeignClient(name = "producto-service", url = "http://localhost:8084")
public interface ProductoClient {

    @GetMapping("/productos/bajo-stock")
    List<Producto> getProductosBajoStock();
}
