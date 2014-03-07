/*
 Copyright 2013 Red Hat, Inc. and/or its affiliates.

 This file is part of lightblue.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redhat.lightblue.crud.mongo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.redhat.lightblue.crud.CRUDOperationContext;
import com.redhat.lightblue.crud.CRUDFindResponse;

/**
 * Basic doc search operation
 */
public class BasicDocFinder implements DocFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDocFinder.class);

    @Override
    public List<DBObject> find(CRUDOperationContext ctx,
                               DBCollection coll,
                               CRUDFindResponse response,
                               DBObject mongoQuery,
                               DBObject mongoSort,
                               Long from,
                               Long to) {
        LOGGER.debug("Submitting query");
        DBCursor cursor = coll.find(mongoQuery);
        LOGGER.debug("Query evaluated");
        if (mongoSort != null) {
            cursor = cursor.sort(mongoSort);
            LOGGER.debug("Result set sorted");
        }
        LOGGER.debug("Applying limits: {} - {}", from, to);
        response.setSize(cursor.size());
        if (from != null) {
            cursor.skip(from.intValue());
        }
        if (to != null) {
            cursor.limit(to.intValue() - (from == null ? 0 : from.intValue()) + 1);
        }
        LOGGER.debug("Retrieving results");
        List<DBObject> mongoResults = cursor.toArray();
        LOGGER.debug("Retrieved {} results", mongoResults.size());
        return mongoResults;
    }
}