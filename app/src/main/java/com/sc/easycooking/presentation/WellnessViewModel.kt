package com.sc.easycooking.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.sc.easycooking.data.WellnessTask
import kotlinx.coroutines.suspendCancellableCoroutine

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked.value = checked
        }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }