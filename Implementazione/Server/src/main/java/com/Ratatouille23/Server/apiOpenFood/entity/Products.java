package com.Ratatouille23.Server.apiOpenFood.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private String _id;

    @JsonProperty("allergens")
    private String allergens;
    @JsonProperty("allergens_hierarchy")
    private List<String> allergens_hierarchy = new ArrayList<>();
    private String code;
    private String ingredients_text;
    private String product_name;
    @JsonProperty("quantity")
    private String quantity;
    private String product_quantity;
    private String ingredients_text_en;
}
