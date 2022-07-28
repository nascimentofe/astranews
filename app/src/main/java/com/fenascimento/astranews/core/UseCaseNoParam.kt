package com.fenascimento.astranews.core

import kotlinx.coroutines.flow.Flow

abstract class UseCaseNoParam<Source> {

    abstract suspend fun execute() : Flow<Source>

    suspend operator fun invoke() = execute()

}