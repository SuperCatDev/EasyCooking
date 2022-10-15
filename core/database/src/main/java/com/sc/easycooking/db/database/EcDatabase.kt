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

package com.sc.easycooking.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sc.easycooking.db.database.dao.RecipesDao
import com.sc.easycooking.db.database.model.RecipeEntity
import com.sc.easycooking.db.database.util.IngredientsConverter
import com.sc.easycooking.db.database.util.RecipeTagsListConverter

@Database(
    entities = [
        RecipeEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    RecipeTagsListConverter::class,
    IngredientsConverter::class,
)
abstract class EcDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}
