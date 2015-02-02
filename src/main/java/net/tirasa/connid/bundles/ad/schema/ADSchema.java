/* 
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2011 ConnId. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License").  You may not use this file
 * except in compliance with the License.
 * 
 * You can obtain a copy of the License at
 * http://opensource.org/licenses/cddl1.php
 * See the License for the specific language governing permissions and limitations
 * under the License.
 * 
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://opensource.org/licenses/cddl1.php.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package net.tirasa.connid.bundles.ad.schema;

import net.tirasa.connid.bundles.ad.ADConnection;
import org.identityconnectors.framework.common.objects.Schema;

public class ADSchema {

    private final ADConnection connection;

    private Schema schema;

    public ADSchema(final ADConnection connection) {
        this.connection = connection;
    }

    public Schema getSchema() {
        if (schema == null) {
            schema = new ADSchemaBuilder(connection).getSchema();
        }

        return schema;
    }
}
