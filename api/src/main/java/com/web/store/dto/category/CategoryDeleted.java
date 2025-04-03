package com.web.store.dto.category;

import lombok.Data;

@Data
public class CategoryDeleted {
    private Long id;
    private String name;
    private String msg = "Category deleted successfully";
}
