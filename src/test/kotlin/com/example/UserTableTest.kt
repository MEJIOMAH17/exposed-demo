package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UserTableTest {
    companion object {
        lateinit var database: Database

        @BeforeAll
        @JvmStatic
        fun init() {
            val container = Postgres()
            container.start()
            database = Database.connect(HikariDataSource(
                HikariConfig().also {
                    it.jdbcUrl = container.jdbcUrl
                    it.username = "test"
                    it.password = "test"
                }
            ))
            transaction(database) {
                SchemaUtils.create(AccountTable,UserTable,)
            }
        }

    }

    @Test
    fun test(): Unit = transaction(database) {
        val mark = UserDao.create(
            User(
                name = "Mark",
                country = "Russia"
            )
        )
        val petr = UserDao.create(
            User(
                name = "Petr",
                country = "Russia"
            )
        )
        val jesus = UserDao.create(
            User(
                name = "Jesus",
                country = "Rome Empire"
            )
        )
        println(UserDao.allCountries())

        AccountDao.create(
            Account(
                amount = 3.0,
                userId = mark.id
            )
        )
        AccountDao.create(
            Account(
                amount = 4.0,
                userId = mark.id
            )
        )
        AccountDao.create(
            Account(
                amount = 2.0,
                userId = petr.id
            )
        )
        AccountDao.create(
            Account(
                amount = 2.0,
                userId = jesus.id
            )
        )
        val message = AccountDao.findUserNameToAccountAmountByCountries(listOf("russia","Rome Empire"))
        println(message)
    }
}