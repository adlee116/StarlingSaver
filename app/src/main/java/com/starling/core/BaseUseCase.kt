package com.starling.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Type, in Params> where Type : Any? {
    var enableTesting: Boolean = false
    abstract suspend fun run(params: Params): Result<Type, Exception>
    open operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Result<Type, Exception>) -> Unit = {}) {
        scope.launch {
            val job =
                if (enableTesting) withContext(scope.coroutineContext) { run(params) }
                else withContext(Dispatchers.IO) { run(params) }
            withContext(scope.coroutineContext) { onResult(job) }
        }
    }
}