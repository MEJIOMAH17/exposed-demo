package com.example

import org.testcontainers.containers.PostgreSQLContainer

class Postgres:PostgreSQLContainer<Postgres>("postgres:latest") {
}