package com.web.store.dto.product;

import lombok.Data;

@Data
public class ProductDeleted {
    private Long id;
    private String name;
    private String msg = "Product Deleted successfully";

}
