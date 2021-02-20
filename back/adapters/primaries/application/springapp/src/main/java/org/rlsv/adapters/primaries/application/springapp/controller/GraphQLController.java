package org.rlsv.adapters.primaries.application.springapp.controller;

import exceptions.CleanException;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthorizationController.class);

    GraphQL graphQL;

    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping
    public ResponseEntity graphql(@RequestBody  String query) throws CleanException {

        ExecutionResult executionResult = this.graphQL.execute(query);

        LOG.info("query : {} {}", query, executionResult);

        HttpStatus status = OK;
        if (CollectionUtils.isNotEmpty(executionResult.getErrors())) {
            status = BAD_REQUEST;
        }

        return new ResponseEntity(executionResult, status);

    }

}
