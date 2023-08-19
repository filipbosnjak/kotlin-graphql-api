package com.example.dgsproject.instrumentation

import com.netflix.graphql.dgs.context.DgsContext
import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.SimplePerformantInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.schema.DataFetcher
import org.springframework.stereotype.Component

@Component
class CustomPerformantInstrumentation: SimplePerformantInstrumentation() {


    /**
     * Instrumentation allows us to inspect and change graphql query while executing so at runtime
     */

    override fun beginExecution(
        parameters: InstrumentationExecutionParameters?,
        state: InstrumentationState?
    ): InstrumentationContext<ExecutionResult>? {
        println("query parameters: $parameters")
        println("query variables: ${parameters?.variables}")
        println(parameters?.query ?: "asd")
        println(state)
        return super.beginExecution(parameters, state)
    }
    override fun instrumentDataFetcher(
        dataFetcher: DataFetcher<*>,
        parameters: InstrumentationFieldFetchParameters?,
        state: InstrumentationState?
    ): DataFetcher<*> {
        println("executing")
        return dataFetcher
/*        return DataFetcher { environment -> {
            val result = dataFetcher?.get(environment)

            val dgsContext: DgsContext = DgsContext.from(environment)

            println(dgsContext.requestData)


            result
        } }*/
    }
}