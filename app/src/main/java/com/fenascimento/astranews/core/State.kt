package com.fenascimento.astranews.core

import com.fenascimento.astranews.data.Post

sealed class State<out T: Any> {

    /**
     * O estado de Loading pode ser um object porque nao possui atributos
     */
    object Loading : State<Nothing>()

    /**
     * Os casos de Success e Error possuem atributos entao é necessario
     * cria-los como data classes. O atributo é modificado conforme o estado
     * uma List<Post> no caso de sucesso e um Throwable no caso de falha.
     */
    data class Success<out T : Any>(val result : T) : State<T>()

    data class Error(val error : Throwable) : State<Nothing>()

}