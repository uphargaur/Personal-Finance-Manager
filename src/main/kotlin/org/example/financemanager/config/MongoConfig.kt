package org.example.financemanager.config

import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class MongoConfig {
    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(SimpleMongoClientDatabaseFactory(MongoClients.create("mongodb+srv://uphargaur:rz43UiKYQeVmMWil@syfe.ep6cb.mongodb.net/?retryWrites=true&w=majority&appName=Syfe"), "financeManager"))
    }
}