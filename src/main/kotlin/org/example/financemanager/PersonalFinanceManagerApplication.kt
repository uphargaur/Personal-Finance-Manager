package org.example.financemanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [MongoAutoConfiguration::class])
class PersonalFinanceManagerApplication

fun main(args: Array<String>) {
    runApplication<PersonalFinanceManagerApplication>(*args)
}
