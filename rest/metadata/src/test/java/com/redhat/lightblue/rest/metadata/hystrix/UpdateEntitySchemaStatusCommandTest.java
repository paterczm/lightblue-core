/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.lightblue.rest.metadata.hystrix;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author nmalik
 */
public class UpdateEntitySchemaStatusCommandTest extends AbstractRestCommandTest {
    @Test
    public void execute() {
        UpdateEntitySchemaStatusCommand command = new UpdateEntitySchemaStatusCommand(null, metadata, null, null, null, null);

        String output = command.execute();

        Assert.assertNotNull(output);
    }
}
