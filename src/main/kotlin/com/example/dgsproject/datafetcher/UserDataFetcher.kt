package com.example.dgsproject.datafetcher

import com.example.dgstut.generated.types.User
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import java.util.UUID

@DgsComponent
class UserDataFetcher {

    @DgsQuery
    fun getUserByUserName(
        @InputArgument username: String
    ): User {
        return User("username", "email")
    }
}