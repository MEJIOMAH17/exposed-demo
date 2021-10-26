package com.example

import org.jetbrains.exposed.sql.Table

object AccountTable:Table() {
    val id = long("id").autoIncrement()
    val userId = reference("user_id",UserTable.id)
    val amount = double("amount")

    override val primaryKey: PrimaryKey = PrimaryKey(UserTable.id)
}