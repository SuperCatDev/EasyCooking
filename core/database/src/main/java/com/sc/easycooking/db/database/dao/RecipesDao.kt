/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sc.easycooking.db.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sc.easycooking.db.database.model.RecipeEntity

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes ORDER BY creation_date DESC")
    fun observeAllRecipes(): DataSource.Factory<Int, RecipeEntity>

    @Query("DELETE FROM recipes WHERE id in (:ids)")
    suspend fun deleteByIds(ids: Set<Int>)

    @Query("SELECT * FROM recipes WHERE id == :id")
    suspend fun getRecipeForId(id: Int): RecipeEntity?
}
