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

package com.haulmont.cuba.gui.components;

import com.haulmont.cuba.core.entity.FileDescriptor;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Supplier;

public interface Image extends DatasourceComponent, Component.HasCaption {
    String NAME = "image";

    /**
     * Creates image resource implementation by its type.
     *
     * @param type image resource type
     * @return image resource instance with given type
     */
    <T extends ImageResource> T createResource(Class<T> type);

    /**
     * Marker interface to indicate that the implementing class can be used as a image resource.
     */
    interface ImageResource {
    }

    /**
     * A resource which represents an image which can be loaded from the given <code>URL</code>.
     */
    interface UrlImageResource extends ImageResource {
        void setUrl(URL url);

        URL getUrl();
    }


    /**
     * A resource which represents an image which stores in the filesystem as the given <code>File</code>.
     */
    interface FileImageResource extends ImageResource {
        void setFile(File file);

        File getFile();
    }

    /**
     * A resource which represents a theme image, e.g., <code>VAADIN/themes/yourtheme/path</code>
     */
    interface ThemeImageResource extends ImageResource {
        void setPath(String path);

        String getPath();
    }

    /**
     * A resource which represents an image, which can be obtained from the <code>FileStorage</code>
     * by the given FileDescriptor.
     */
    interface FileDescriptorImageResource extends ImageResource {
        void setFileDescriptor(FileDescriptor fileDescriptor);

        FileDescriptor getFileDescriptor();
    }

    /**
     * A resource which represents an image located in classpath with the given <code>path</code>.
     */
    interface ClasspathImageResource extends ImageResource {
        void setPath(String path);

        String getPath();
    }

    /**
     * A resource which is stream image representation.
     */
    interface StreamImageResource extends ImageResource {
        void setStreamSupplier(Supplier<InputStream> streamSupplier);

        Supplier<InputStream> getStreamSupplier();
    }
}