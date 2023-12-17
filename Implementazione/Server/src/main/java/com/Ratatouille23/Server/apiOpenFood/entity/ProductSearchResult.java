package com.Ratatouille23.Server.apiOpenFood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResult {
    private int count;
    private int page;
    private int page_count;
    private List<Products> products = new ArrayList<>();
}
