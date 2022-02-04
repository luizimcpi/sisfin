package com.devlhse

import io.micronaut.runtime.Micronaut.build
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.devlhse")
		.start()
}

