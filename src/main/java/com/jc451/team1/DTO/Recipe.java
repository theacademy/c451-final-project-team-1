package DTO;

import java.util.Objects;

public class Recipe {
    private int recipeId;
    private String title;
    private String instruction;
    private int prepTime;
    private String image;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId && prepTime == recipe.prepTime && Objects.equals(title, recipe.title) && Objects.equals(instruction, recipe.instruction) && Objects.equals(image, recipe.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, title, instruction, prepTime, image);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", instruction='" + instruction + '\'' +
                ", prepTime=" + prepTime +
                ", image='" + image + '\'' +
                '}';
    }
}
