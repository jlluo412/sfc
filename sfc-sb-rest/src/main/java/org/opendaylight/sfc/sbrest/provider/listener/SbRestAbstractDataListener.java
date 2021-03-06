/*
 * Copyright (c) 2014, 2017 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.sfc.sbrest.provider.listener;

import java.util.concurrent.ExecutorService;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataTreeChangeListener;
import org.opendaylight.controller.md.sal.binding.api.DataTreeIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

public abstract class SbRestAbstractDataListener<T extends DataObject> implements DataTreeChangeListener<T>,
        AutoCloseable {
    private final DataBroker dataBroker;
    private final ListenerRegistration<SbRestAbstractDataListener<T>> dataChangeListenerRegistration;
    private final ExecutorService executor;

    protected SbRestAbstractDataListener(DataBroker dataBroker, InstanceIdentifier<T> instanceIdentifier,
            LogicalDatastoreType dataStoreType, ExecutorService executor) {
        this.dataBroker = dataBroker;
        this.executor = executor;

        dataChangeListenerRegistration = dataBroker.registerDataTreeChangeListener(new DataTreeIdentifier<>(
                dataStoreType, instanceIdentifier), this);
    }

    public DataBroker getDataBroker() {
        return dataBroker;
    }

    protected ExecutorService executor() {
        return executor;
    }

    @Override
    public void close() {
        dataChangeListenerRegistration.close();
    }
}
