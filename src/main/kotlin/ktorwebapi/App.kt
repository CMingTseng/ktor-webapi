package ktorwebapi

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.util.KtorExperimentalAPI
import ktorwebapi.controllers.FruitController
import ktorwebapi.entities.Fruits
import ktorwebapi.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

@KtorExperimentalAPI
fun Application.main() {
    val appConfig = environment.config

    val databaseUrl = appConfig.property("ktor.database.url").getString()
    val databaseDriver = appConfig.property("ktor.database.driver").getString()

    Database.connect(databaseUrl, databaseDriver)
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        SchemaUtils.create(Fruits)
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Routing) {
        api()
        fruits(FruitController())
    }
}
