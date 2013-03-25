/**
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2011-2013 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License"). You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at https://oss.oracle.com/licenses/CDDL
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at https://oss.oracle.com/licenses/CDDL.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.connid.bundles.ad;

import java.util.ArrayList;
import java.util.List;
import org.connid.bundles.ldap.LdapConfiguration;
import org.identityconnectors.framework.spi.ConfigurationProperty;

public class ADConfiguration extends LdapConfiguration {

    private boolean retrieveDeletedUser = true;

    public static final String PROMPT_USER_FLAG = "pwdLastSet";

    public static final String PROMPT_USER_VALUE = "0";

    public static final String LOCK_OUT_FLAG = "lockoutTime";

    public static final String LOCK_OUT_DEFAULT_VALUE = "0";

    private List<String> memberships;

    private boolean trustAllCerts;

    private boolean loading = false;

    private boolean membershipsInOr = false;

    private String defaultPeopleContainer;

    private boolean startSyncFromToday = true;

    public ADConfiguration() {
        super();

        setUidAttribute("sAMAccountName");
        setSynchronizePasswords(true);

        setSynchronizePasswords(false);
        setAccountUserNameAttributes("sAMAccountName");
        setObjectClassesToSynchronize(new String[]{"user"});
        setGroupMemberAttribute("member");
        setAccountObjectClasses(new String[]{"top", "person", "organizationalPerson", "user"});

        setUsePagedResultControl(true);
        setBlockSize(100);
        setUseBlocks(true);

        setPasswordAttribute("unicodePwd");
        setSsl(true);
        setPort(636);

        memberships = new ArrayList<String>();
    }

    @ConfigurationProperty(displayMessageKey = "memberships.display",
    helpMessageKey = "memberships.help", required = true, order = 1)
    public String[] getMemberships() {
        return memberships.toArray(new String[memberships.size()]);
    }

    public void setMemberships(String... memberships) {
        this.memberships = new ArrayList<String>();

        if (memberships != null) {
            for (String membership : memberships) {
                this.memberships.add(membership.trim());
            }
        }
    }

    @ConfigurationProperty(displayMessageKey = "retrieveDeletedUser.display",
    helpMessageKey = "retrieveDeletedUser.help", order = 2)
    public boolean isRetrieveDeletedUser() {
        return retrieveDeletedUser;
    }

    public void setRetrieveDeletedUser(boolean retrieveDeletedUser) {
        this.retrieveDeletedUser = retrieveDeletedUser;
    }

    @ConfigurationProperty(displayMessageKey = "trustAllCerts.display",
    helpMessageKey = "trustAllCerts.help", order = 3)
    public boolean isTrustAllCerts() {
        return trustAllCerts;
    }

    public void setTrustAllCerts(final boolean trustAllCerts) {
        this.trustAllCerts = trustAllCerts;
    }

    @ConfigurationProperty(displayMessageKey = "loading.display",
    helpMessageKey = "loading.help", order = 4)
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isMembershipsInOr() {
        return membershipsInOr;
    }

    @ConfigurationProperty(displayMessageKey = "membershipsInOr.display",
    helpMessageKey = "membershipsInOr.help", order = 5)
    public void setMembershipsInOr(boolean membershipsInOr) {
        this.membershipsInOr = membershipsInOr;
    }

    @ConfigurationProperty(displayMessageKey = "defaultPeopleContainer.display",
    helpMessageKey = "defaultPeopleContainer.help", order = 6)
    public String getDefaultPeopleContainer() {
        return defaultPeopleContainer;
    }

    public void setDefaultPeopleContainer(String defaultPeopleContainer) {
        this.defaultPeopleContainer = defaultPeopleContainer;
    }

    @ConfigurationProperty(displayMessageKey = "startSyncFromToday.display",
    helpMessageKey = "startSyncFromToday.help", order = 7)
    public boolean isStartSyncFromToday() {
        return startSyncFromToday;
    }

    public void setStartSyncFromToday(boolean startSyncFromToday) {
        this.startSyncFromToday = startSyncFromToday;
    }
}