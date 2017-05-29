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

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Resources;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.web.gui.components.WebImage;
import com.vaadin.server.StreamResource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebClasspathImageResource extends WebImage.WebAbstractImageResource implements WebImageResource, Image.ClasspathImageResource {

    private final Logger log = LoggerFactory.getLogger(WebClasspathImageResource.class);

    protected String path;

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    protected void createResource() {
        if (StringUtils.isEmpty(path)) {
            log.warn("Can't create ClasspathImageResource, because its path is not defined");
            return;
        }

        String fileName = path.substring(path.lastIndexOf("/") + 1);
        resource = new StreamResource((StreamResource.StreamSource) () ->
                AppBeans.get(Resources.class).getResourceAsStream(path), fileName);
    }
}