package ktor.demo.server.android

import org.junit.Test

import org.junit.Assert.*

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    //FIXME can not run server at Test ??!!
    @Test
    fun runKtorServer() = runBlocking {
        val environment = System.getenv("KTOR_ENVIRONMENT") ?: "development"
        val configName = "application.$environment.conf"

        val appEngineEnv = applicationEngineEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load(configName))
            log = LoggerFactory.getLogger("ktor.application")

            connector {
                host = config.property("ktor.deployment.host").getString()
                port = config.property("ktor.deployment.port").getString().toInt()
            }
        }

        embeddedServer(Netty, appEngineEnv).start(wait = true)
        assertEquals(4, 2 + 2)
    }
}