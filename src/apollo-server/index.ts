import {ApolloServer} from "apollo-server-express"
import {ApolloGateway, IntrospectAndCompose} from "@apollo/gateway";
import express from "express";

( async ()=>{
    const app = express()

    const server = new ApolloServer({
        csrfPrevention: true,
        gateway: new ApolloGateway({
            supergraphSdl: new IntrospectAndCompose({
                subgraphs: [
                    {
                        name: "first",
                        url: "http://localhost:8080/graphql",
                    },
                ],
            }),
        })
    })

    await server.start()
    server.applyMiddleware({
        app,
        path: "/graphql"
    })

    app.listen(8082, () =>
        console.log(`playground started at: ${server.graphqlPath}`)
    );
})()