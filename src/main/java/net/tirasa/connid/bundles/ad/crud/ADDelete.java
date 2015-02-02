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
package net.tirasa.connid.bundles.ad.crud;

import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTGUID;

import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ldap.commons.LdapModifyOperation;
import net.tirasa.connid.bundles.ldap.search.LdapSearches;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;

public class ADDelete extends LdapModifyOperation {

    private final ObjectClass oclass;

    private final Uid uid;

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final ADConnection conn;

    public ADDelete(final ADConnection conn, final ObjectClass oclass, final Uid uid) {
        super(conn);
        this.oclass = oclass;
        this.uid = uid;
        this.conn = conn;
    }

    public void delete() {
        final String entryDN;
        if (OBJECTGUID.equals(conn.getSchemaMapping().getLdapUidAttribute(oclass))) {
            entryDN = String.format("<GUID=%s>", uid.getUidValue());
        } else {
            entryDN = LdapSearches.getEntryDN(conn, oclass, uid);
        }

        final Set<String> ldapGroups = new HashSet<String>(groupHelper.getLdapGroups(entryDN));
        groupHelper.removeLdapGroupMemberships(entryDN, ldapGroups);

        try {
            conn.getInitialContext().destroySubcontext(entryDN);
        } catch (NamingException e) {
            throw new ConnectorException(e);
        }
    }
}
