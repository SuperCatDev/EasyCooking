package com.sc.easycooking.recipes.impl.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sc.easycooking.db.database.dao.RecipesDao
import com.sc.easycooking.db.database.model.IngredientDbModel
import com.sc.easycooking.db.database.model.RecipeEntity
import com.sc.easycooking.recipes.api.models.IngredientModel
import com.sc.easycooking.recipes.api.models.QuantityType
import com.sc.easycooking.recipes.api.models.RecipeCategory
import com.sc.easycooking.recipes.api.models.RecipeModel
import com.sc.easycooking.recipes.api.models.RecipeTag
import com.sc.easycooking.recipes.impl.repository.mappers.toDomainModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RecipesRepository @Inject constructor(private val recipesDao: RecipesDao) {
    /*init {
        GlobalScope.launch {
            delay(5000L)
            recipesDao.addRecipe(
                RecipeEntity(
                    name = "Test recipe :)",
                    recipe = "heje",
                    creationDate = System.currentTimeMillis(),
                    cookingTime = 5,
                    categoryId = 1,
                    tagIds = emptyList(),
                    ingredients = listOf(
                        IngredientDbModel(
                            name = "Tomatos",
                            quantityType = 3,
                            amount = 4,
                        ),
                        IngredientDbModel(
                            name = "Onion",
                            quantityType = 4,
                            amount = 1,
                        )
                    )
                )0
            )
        }
    }*/

    fun observeAllRecipes(): Flow<PagingData<RecipeModel>> {
        return getTestData()
/*
        return Pager(
            PagingConfig(10),
            null,
            recipesDao.observeAllRecipes().map {
                it.toDomainModel()
            }
                .asPagingSourceFactory()
        ).flow*/
    }


    private fun getTestData(): Flow<PagingData<RecipeModel>> {
        val entity1 = RecipeModel(
            id = 1,
            name = "Tomatos with something",
            recipe = "Нарежьте крабовые палочки, перец и помидоры небольшими полосками. Добавьте к ним тёртый сыр и измельчённый чеснок. Заправьте салат майонезом и украсьте укропом.",
            creationDate = System.currentTimeMillis(),
            cookingTime = 5,
            category = RecipeCategory.SALAD,
            tags = emptyList(),
            ingredients = listOf(
                IngredientModel(
                    name = "Tomatos",
                    quantity = QuantityType.AMOUNT,
                    amount = 4,
                ),
                IngredientModel(
                    name = "Onion",
                    quantity = QuantityType.AMOUNT,
                    amount = 1,
                )
            )
        )

        val entity2 = RecipeModel(
            id = 2,
            name = "Очень очень длинное название вот так вот супер пупер мега стейк это вам ни хухрымухры паапа",
            recipe = "Стейки с чем-то очень интереснымю Бла бла Бла ООООООООЧ Большой рецепт пиздец просто Бла бла Бла ООООООООЧ Большой рецепт пиздец просто Бла бла Бла ООООООООЧ Большой рецепт пиздец просто Бла бла Бла ООООООООЧ Большой рецепт пиздец просто Бла бла Бла ООООООООЧ Большой рецепт пиздец просто Бла бла Бла ООООООООЧ Большой рецепт пиздец просто",
            creationDate = System.currentTimeMillis(),
            cookingTime = 5,
            category = RecipeCategory.MAIN,
            tags = listOf(RecipeTag.MEAT, RecipeTag.VEGETABLES),
            ingredients = listOf(
                IngredientModel(
                    name = "Meat",
                    quantity = QuantityType.KG,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Огурчики",
                    quantity = QuantityType.KG,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Специи",
                    quantity = QuantityType.GRAMS,
                    amount = 300,
                )
            )
        )

        val entity3 = RecipeModel(
            id = 3,
            name = "Вкусные круасанчики",
            recipe = "Мешаем молочко и збс, ооочень вкусненько получается \n а еще если добавить пирожочков то вообще бжественный пиздец)",
            creationDate = System.currentTimeMillis(),
            cookingTime = 5,
            category = RecipeCategory.DESERT,
            tags = listOf(RecipeTag.BAKERY),
            ingredients = listOf(
                IngredientModel(
                    name = "Мука",
                    quantity = QuantityType.KG,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Молоко,  Очень длинное название молока я даже не знаю какое это может такое быть молоко то",
                    quantity = QuantityType.LITRES,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Сливки",
                    quantity = QuantityType.ML,
                    amount = 1,
                ),
            )
        )

        val entity4 = RecipeModel(
            id = 4,
            name = "Вкусные круасанчики",
            recipe = "Мешаем молочко и збс, ооочень вкусненько получается \n а еще если добавить пирожочков то вообще бжественный пиздец)",
            creationDate = System.currentTimeMillis(),
            cookingTime = 5,
            category = RecipeCategory.DESERT,
            tags = listOf(RecipeTag.BAKERY),
            ingredients = listOf(
                IngredientModel(
                    name = "Мука",
                    quantity = QuantityType.KG,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Сливки топленые",
                    quantity = QuantityType.ML,
                    amount = 1,
                ),
                IngredientModel(
                    name = "Молоко,  Очень длинное название молока я даже не знаю какое это может такое быть молоко то",
                    quantity = QuantityType.LITRES,
                    amount = 1,
                ),
            )
        )



        return flowOf(
            PagingData.from(
                listOf(
                    entity1,
                    entity2,
                    entity3,
                    entity4,
                    entity1.copy(id = 5),
                    entity2.copy(id = 6),
                    entity3.copy(id = 7),
                    entity4.copy(id = 8),
                    entity2.copy(id = 9),
                    entity3.copy(id = 10),
                    entity1.copy(id = 11),
                    entity4.copy(id = 12),
                )
            )
        )

    }
}