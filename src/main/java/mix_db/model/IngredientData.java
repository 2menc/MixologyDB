package mix_db.model;

/**
 * DTO class representing ingredient details
 * @param name ingredient name
 * @param quantity amount of ingredient
 * @param unit unit of measurement
 */
public record IngredientData(String name, float quantity, String unit) {}
