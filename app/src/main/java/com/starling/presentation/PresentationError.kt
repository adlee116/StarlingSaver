package com.starling.presentation

sealed class PresentationError {
    class IntError(val error: Int): PresentationError()
    class StringError(val error: String): PresentationError()
}