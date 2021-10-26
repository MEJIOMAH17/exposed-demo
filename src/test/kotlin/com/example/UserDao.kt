package com.example

import org.jetbrains.exposed.sql.*

object UserDao {
    val table = UserTable
    fun create(user: User): User {
        return table.insert {
            it[name] = user.name
            it[country] = user.country
        }.resultedValues!!.map { it.toUser() }.single()
    }

    fun selectAll(): List<User> {
        return table.selectAll().map { it.toUser() }
    }

    fun selectByName(name: String): List<User> {
        return table.select {
            table.name.upperCase().eq(name.uppercase())
        }.map { it.toUser() }
    }

    fun allCountries(): List<String> {
        return table
            .slice(table.country)
            .selectAll()
            .withDistinct(true)
            .mapNotNull { it[table.country] }
    }


    private fun ResultRow.toUser(): User {
        return User(
            id = this[table.id],
            name = this[table.name],
            country = this[table.country]
        )
    }
}