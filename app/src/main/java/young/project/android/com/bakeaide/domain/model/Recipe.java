package young.project.android.com.bakeaide.domain.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import young.project.android.com.bakeaide.domain.database.IngredientListTypeConverter;
import young.project.android.com.bakeaide.domain.database.StepListTypeConverter;

@Entity
public class Recipe {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @ColumnInfo
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo
    @SerializedName("ingredients")
    @Expose
    @TypeConverters(IngredientListTypeConverter.class)
    private List<Ingredient> ingredients = null;
    @ColumnInfo
    @SerializedName("steps")
    @Expose
    @TypeConverters(StepListTypeConverter.class)
    private List<Step> steps = null;
    @ColumnInfo
    @SerializedName("servings")
    @Expose
    private int servings;
    @ColumnInfo
    @SerializedName("image")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}