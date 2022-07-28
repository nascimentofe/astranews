package com.fenascimento.astranews.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe define um use case generico.
 * Por enquanto nao há paramentros.
 */
abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param) : Flow<Source>

    /**
     * Executa ao invocar a instancia da classe.
     */
    suspend operator fun invoke(param: Param) = execute(param)

}