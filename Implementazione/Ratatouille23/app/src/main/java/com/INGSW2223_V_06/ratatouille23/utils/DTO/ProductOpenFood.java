package com.INGSW2223_V_06.ratatouille23.utils.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductOpenFood implements Serializable {
    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("product_name")
    private String product_name;

    @Expose
    @SerializedName("allergens_hierarchy")
    private List<String> allergens_hierarchy;

    @Expose
    @SerializedName("ingredients_text")
    private String ingredients_text;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public List<String> getAllergens_hierarchy() {
        return allergens_hierarchy;
    }

    public void setAllergens_hierarchy(List<String> allergens_hierarchy) {
        this.allergens_hierarchy = allergens_hierarchy;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public void setIngredients_text(String ingredients_text) {
        this.ingredients_text = ingredients_text;
    }

    public ProductOpenFood() {
    }

    public ProductOpenFood(String _id, String product_name, List<String> allergens_hierarchy, String ingredients_text) {
        this._id = _id;
        this.product_name = product_name;
        this.allergens_hierarchy = allergens_hierarchy;
        this.ingredients_text = ingredients_text;
    }

    @Override
    public String toString() {
        return "ProductOpenFood{" +
                "_id='" + _id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", allergens_hierarchy=" + allergens_hierarchy +
                ", ingredients_text='" + ingredients_text + '\'' +
                '}';
    }
}
