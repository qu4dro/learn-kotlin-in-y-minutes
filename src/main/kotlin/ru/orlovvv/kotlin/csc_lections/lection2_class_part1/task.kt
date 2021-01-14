package ru.orlovvv.kotlin.csc_lections.lection2_class_part1

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.*
import java.util.logging.Logger
import kotlin.test.assertEquals

open class SqlConfig(sqlDialect: String) {
    val dialect = SqlDialect(sqlDialect)
    var hasCaching = false
        get() {
            println("Getting hasCaching: $field")
            return field
        }
        set(value) {
            if (value == field) return
            println("Setting hasCahing: $field")
            if (value) {
                //DO some caching
            } else {
                //DO some non-caching
            }
            field = value
        }
}
object DefaultSqlConfig: SqlConfig("") {
    init {
        hasCaching = false
    }
}
open class SqlQuery(val config: SqlConfig) {

    constructor() : this(DefaultSqlConfig) {
        config.hasCaching = false
    }
//    init {
//        config = SqlConfig()
//    }

}

class SelectQuery(sqlConfig: SqlConfig, val table: String, val fields: Array<String>) : SqlQuery(sqlConfig), LoggingExecutable {
    override fun execute(): Int = TODO()

    companion object Whatever {
        fun create(table: String, fields: Array<String>) = SelectQuery(DefaultSqlConfig, table, fields)
    }
}

class SqlRunner() {

    val pool: ExecutorService = Executors.newCachedThreadPool()

    fun run(exec: Executable): Int {
        return WorkItem(exec).call()
    }

    fun runAsync(exec: Executable): Future<Int> {
        return pool.submit(WorkItem(exec))
    }

    fun shutDown(): Unit = TODO()

    open class WorkItem(val exec: Executable) : Callable<Int> {
        override fun call(): Int {
            exec.beforeExecute()
            val res = exec.execute()
            exec.afterExecute()
            return res
        }
    }

    inner class AsyncWorkItem(exec: Executable) : WorkItem(exec) {
        fun submit(): Future<Int> =
            this@SqlRunner.pool.submit(this)
    }
}

class SqlTests {
    lateinit var runner: SqlRunner

    @BeforeEach
    fun before() {
        runner = SqlRunner()
    }

    @AfterEach
    fun after() {
        runner.shutDown()
    }

    @Test
    fun run() {
        assertEquals(42, runner.run(
            object : LoggingExecutable {
                override fun execute(): Int {
                    log.info("Look, ma!")
                    return 42
                }
            }
        ))
    }
}
interface Executable {
    fun execute(): Int

    fun beforeExecute() {}
    fun afterExecute() {}
}

interface Loggable {
    val log: Logger
        get() = Logger.getLogger(javaClass.name)
}

interface LoggingExecutable : Executable, Loggable {
    override fun beforeExecute() {
        log.info("Before executing: $this")
    }

    override fun afterExecute() {
        log.info("After executing: $this")
    }
}


class SqlDialect(val name: String) {
    val isDefault
        get() = "" == name
//        get() {
//            return "" == name
//        }
}



fun main() {


}