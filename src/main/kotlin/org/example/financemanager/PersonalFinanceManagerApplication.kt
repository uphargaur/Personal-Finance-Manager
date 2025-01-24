package org.example.financemanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@SpringBootApplication(exclude = [MongoAutoConfiguration::class])
@EnableMongoRepositories("org.example.financemanager.repository")
class PersonalFinanceManagerApplication

fun main(args: Array<String>) {
    runApplication<PersonalFinanceManagerApplication>(*args)
}
