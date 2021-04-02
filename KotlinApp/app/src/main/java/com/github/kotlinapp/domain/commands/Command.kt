package com.github.kotlinapp.domain.commands

interface Command<T> {
     fun execute(): T
}