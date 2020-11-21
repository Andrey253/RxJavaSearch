package com.boyko.rxjavasearch

import kotlin.random.Random

interface GetAnimalsListener {
    fun onSuccess(animals: List<Cat>)
    fun onError(error: Throwable)
}

interface CatsApi {
    fun getCats(listener: GetAnimalsListener)
}

interface DogsApi {
    fun getDogs(listener: GetAnimalsListener)
}

interface RatsApi {
    fun getRats(listener: GetAnimalsListener)
}

class CatsApiImpl : CatsApi {

    override fun getCats(listener: GetAnimalsListener) {
        if (Random.nextBoolean()) {
            listener.onSuccess(
                listOf(
                    Cat(
                        "John",
                        "10"
                    )
                )
            )
        } else {
            listener.onError(Throwable("No cats available"))
        }
    }
}