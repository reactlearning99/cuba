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
import com.vaadin.server.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebFileImageResource extends WebImage.WebAbstractImageResource implements WebImageResource, Image.FileImageResource {

    private final Logger log = LoggerFactory.getLogger(WebFileImageResource.class);

    protected File file;

    @Override
    public Image.FileImageResource setFile(File file) {
        this.file = file;

        fireResourceUpdateEvent();

        return this;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    protected void createResource() {
        if (file == null || !file.exists()) {
            log.warn("Can't create FileImageResource, because its file is not defined or file does not exist");
            return;
        }

        resource = new FileResource(file);
    }
}
