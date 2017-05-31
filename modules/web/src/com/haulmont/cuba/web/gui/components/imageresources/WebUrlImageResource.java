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
import com.vaadin.server.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class WebUrlImageResource extends WebImage.WebAbstractImageResource implements WebImageResource, Image.UrlImageResource {

    private final Logger log = LoggerFactory.getLogger(WebUrlImageResource.class);

    protected URL url;

    @Override
    public Image.UrlImageResource setUrl(URL url) {
        this.url = url;

        fireResourceUpdateEvent();

        return this;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    protected void createResource() {
        if (url == null) {
            log.warn("Can't create UrlImageResource, because its URL is not defined");
            return;
        }

        resource = new ExternalResource(url);
    }
}