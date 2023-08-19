package com.example.dgsproject.config

import com.example.dgsproject.schemadirectivewiring.CUSTOM_DIRECTIVE
import com.example.dgsproject.schemadirectivewiring.SecuredDirectiveWiring
import com.example.dgsproject.security.SecurityService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.schema.idl.RuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SchemaDirectiveWiringConfig(
    securityService: SecurityService
) {
    @DgsComponent
    class SecuredDirectiveRegistration(
        private val securedDirectiveWiring: SecuredDirectiveWiring
    ) {
        /**
         * Registers schema directive wiring for `@customDirective` directive.
         *
         * @param builder
         * @return RuntimeWiring.Builder
         */
        @DgsRuntimeWiring
        fun addSecuredDirective(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
            return builder.directive(
                CUSTOM_DIRECTIVE,
                securedDirectiveWiring,
            )
        }
    }

}