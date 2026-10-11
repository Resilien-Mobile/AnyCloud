package com.baidaidai.anycloud.data.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration1To2 : Migration(1, 2) {
    override fun migrate(
        db: SupportSQLiteDatabase
    ) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS PolicyEntity (
                unixTimeStamp INTEGER NOT NULL,
                policyType TEXT NOT NULL,
                policyContent TEXT NOT NULL,
                policyGroup INTEGER NOT NULL,
                targetPackageName TEXT NOT NULL,
                PRIMARY KEY(unixTimeStamp)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS GroupEntity (
                unixTimeStamp INTEGER NOT NULL,
                groupName TEXT NOT NULL,
                targetPackageName TEXT NOT NULL,
                PRIMARY KEY(unixTimeStamp)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS LogEntity (
                unixTime INTEGER NOT NULL,
                host TEXT NOT NULL,
                isMatchedResult INTEGER NOT NULL,
                PRIMARY KEY(unixTime)
            )
            """.trimIndent()
        )
    }
}