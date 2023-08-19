package com.example.dgsproject.schemadirectivewiring

import com.example.dgsproject.security.SecurityService
import com.example.dgstut.generated.types.AppPermissions
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.language.EnumValue
import graphql.schema.DataFetcher
import graphql.schema.GraphQLDirective
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaDirectiveWiring
import graphql.schema.idl.SchemaDirectiveWiringEnvironment
import org.springframework.stereotype.Component
import java.security.Permission
import kotlin.concurrent.fixedRateTimer

const val CUSTOM_DIRECTIVE = "customDirective"

@Component
class SecuredDirectiveWiring(
    securityService: SecurityService
): SchemaDirectiveWiring {

    override fun onField(
        environment: SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition>,
    ): GraphQLFieldDefinition {

        val field = environment.element
        val parentType = environment.fieldsContainer
        val originalDataFetcher = environment.codeRegistry.getDataFetcher(parentType, field)

        val directive = field.directivesByName[CUSTOM_DIRECTIVE] ?: return field

        val authDataFetcher: DataFetcher<*> = DataFetcher { dataFetchingEnvironment ->
            println("""
                directive: ${directive.name}
                arguments: ${directive.arguments}
                arguments transformed: ${directive.getAppPermissionsFromArgument()}
            """.trimIndent())

            /**
             * Here we can read the app permissions required for the query/mutation
             * Compare it with the user permissions from the security context
             * And allow/deny the operation based on that
             */
            return@DataFetcher originalDataFetcher.get(dataFetchingEnvironment)
        }

        environment.codeRegistry.dataFetcher(parentType, field, authDataFetcher)
        return field
    }

    private fun GraphQLDirective.getAppPermissionsFromArgument(): AppPermissions {
        val enumValue: EnumValue = arguments[0].toAppliedArgument().argumentValue.value as EnumValue
        return AppPermissions.valueOf(enumValue.name)
    }

}