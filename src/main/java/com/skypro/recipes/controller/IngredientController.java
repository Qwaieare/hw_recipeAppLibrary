package com.skypro.recipes.controller;
import com.skypro.recipes.model.Ingredient;
import com.skypro.recipes.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Ингредиенты для составления рецептов")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/getingredient")
    @Operation(
            summary = "Получить ингредиент по id",
            description = "Получить ингредиент по id"
    )
    public Ingredient getIngredient(@RequestParam Long idIng) {
          return ingredientService.getIngredient(idIng);
    }


    @GetMapping("/addingredient")
    @Operation(
            summary = "Добавить ингредиенты",
            description = "Добавить ингредиенты"
    )
    public Ingredient addNewIngredient(@RequestParam Ingredient ingredient) {
        return ingredientService.addNewIngredient(ingredient);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех ингредиентов",
            description = "Получение полного списка ингредиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
   )
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredient() {
        Map<Long, Ingredient> ingredientsL = ingredientService.getAllIngredient();
        if (ingredientsL == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientsL);
    }

    @PutMapping
    @Parameters(value = {
            @Parameter(name = "idIng", example = "ingredient")
    }
  )
    public ResponseEntity<Ingredient> putIngredient(@PathVariable Long idIng, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.putIngredient(idIng, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
    @DeleteMapping
    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление ингредиента"
    )
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long idIng) {
        if (ingredientService.deleteIngredient(idIng)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
