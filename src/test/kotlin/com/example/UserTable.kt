package com.example

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable:Table() {
    val id: Column<Long> = long("id").autoIncrement()
    val name: Column<String> = text("name")
    val country = text("country").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
