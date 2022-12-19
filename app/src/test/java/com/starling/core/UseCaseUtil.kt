package com.starling.core

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import org.mockito.Mockito

fun <Type, Params> BaseUseCase<Type, Params>.mockSuccess(returnValue: Type) {
  whenever(invoke(any(), Mockito.any(), any())) doAnswer {
    val callback: (Result<Type, Exception>) -> Unit = it.getArgument(2)
    callback.invoke(Result.Success(returnValue))
  }
}