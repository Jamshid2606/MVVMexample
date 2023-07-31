package com.jama.mvvmexample.vm

import android.service.autofill.UserData

sealed class Resource <T> {
    class Loading<T>:Resource<T>()
    class Succes <T:Any>(val data: T):Resource<T>()
    class Error <T:Any>(val error:Throwable) : Resource<T>()
}