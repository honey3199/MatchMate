package com.example.matchmate.data.remote

import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                return Resource.success(response.body())
            }
            var errorMessage = ""
            response.errorBody()?.let {
                errorMessage = it.string() ?: OOPS_SOMETHING_WENT_WRONG
            }
            if (errorMessage.isEmpty()) errorMessage = OOPS_SOMETHING_WENT_WRONG
            return error(errorMessage)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }

    companion object {
        const val OOPS_SOMETHING_WENT_WRONG = "Oops! Something Went Wrong"
    }
}