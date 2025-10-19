package org.cibertec.client;



import java.util.List;

import org.cibertec.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



@FeignClient(name = "producto-service", url = "https://producto-service-production.up.railway.app/productos/bajo-stock")
public interface ProductoClient {

    @GetMapping("producto-service/productos/bajo-stock")
    List<Producto> getProductosBajoStock();
}
