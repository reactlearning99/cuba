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

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.web.gui.components.WebImage;
import com.vaadin.server.StreamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebFileDescriptorImageResource extends WebImage.WebAbstractImageResource implements WebImageResource, Image.FileDescriptorImageResource {

    private final Logger log = LoggerFactory.getLogger(WebFileDescriptorImageResource.class);

    protected FileDescriptor fileDescriptor;

    @Override
    public Image.FileDescriptorImageResource setFileDescriptor(FileDescriptor fileDescriptor) {
        this.fileDescriptor = fileDescriptor;

        fireResourceUpdateEvent();

        return this;
    }

    @Override
    public FileDescriptor getFileDescriptor() {
        return fileDescriptor;
    }

    @Override
    protected void createResource() {
        if (fileDescriptor == null) {
            log.warn("Can't create FileDescriptorImageResource, because its FileDescriptor is not defined");
            return;
        }

        FileStorageService fileStorageService = AppBeans.get(FileStorageService.NAME);
        try {
            if (fileStorageService.fileExists(fileDescriptor)) {
                resource = new StreamResource((StreamResource.StreamSource) () -> {
                    try {
                        ByteArrayDataProvider provider = new ByteArrayDataProvider(fileStorageService.loadFile(fileDescriptor));
                        return provider.provide();
                    } catch (FileStorageException e) {
                        log.warn("Can't create FileDescriptorImageResource. An error occurred while loading file from file storage", e);
                    }
                    return null;
                }, fileDescriptor.getName());
            }
        } catch (FileStorageException e) {
            log.warn("Can't create FileDescriptorImageResource. An error occurred while finding file in file storage", e);
        }
    }
}
