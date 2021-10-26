package com.example

import org.jetbrains.exposed.sql.*

object AccountDao {
    val table = AccountTable
    fun create(account: Account) {
        table.insert {
            it[amount] = account.amount
            it[userId] = account.userId
        }
    }

    fun findUserNameToAccountAmountByCountries(countries: List<String>): Map<String?, List<UserNameToAccountAmount>> {
        return (AccountTable innerJoin UserTable)
            .slice(
                table.amount,
                UserTable.name,
                UserTable.country
            ).select {
                UserTable.country.upperCase().inList(countries.map { it.uppercase() })
            }.groupBy {
                it[UserTable.country]
            }.mapValues { (_, rows: List<ResultRow>) ->
                rows.map { row ->
                    UserNameToAccountAmount(
                        username = row[UserTable.name],
                        accountAmount = row[AccountTable.amount]
                    )
                }
            }
    }

    fun selectAll(): List<Account> {
        return table.selectAll().map {
            Account(
                id = it[table.id],
                amount = it[table.amount],
                userId = it[table.userId]
            )
        }
    }
}