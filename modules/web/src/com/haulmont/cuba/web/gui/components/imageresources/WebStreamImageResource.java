/*
 * Copyright (c) 2008-2017 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.cuba.web.gui.components.imageresources;

import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.web.gui.components.WebImage;
import com.vaadin.server.StreamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.function.Supplier;

public class WebStreamImageResource extends WebImage.WebAbstractImageResource implements WebImageResource, Image.StreamImageResource {

    private final Logger log = LoggerFactory.getLogger(WebStreamImageResource.class);

    protected Supplier<InputStream> streamSupplier;

    @Override
    public Image.StreamImageResource setStreamSupplier(Supplier<InputStream> streamSupplier) {
        this.streamSupplier = streamSupplier;

        fireResourceUpdateEvent();

        return this;
    }

    @Override
    public Supplier<InputStream> getStreamSupplier() {
        return streamSupplier;
    }

    @Override
    protected void createResource() {
        if (streamSupplier == null) {
            log.warn("Can't create StreamImageResource, because its stream supplier is not defined");
            return;
        }

        resource = new StreamResource((StreamResource.StreamSource) () ->
                streamSupplier.get(), null);
    }
}
