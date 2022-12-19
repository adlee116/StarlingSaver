package com.starling.core

import java.io.Reader

class CodeException(
    private val code: Int,
    private val errorMessageStream: Reader? = null
) : Exception() {

    fun getCode() = code
    fun getErrorMessage() = errorMessageStream
}