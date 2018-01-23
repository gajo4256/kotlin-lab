package com.comsysto.kotlinfullstack.backend

import org.springframework.core.ParameterizedTypeReference

inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}
