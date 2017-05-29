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

package com.haulmont.cuba.web.gui.components;

import com.google.common.collect.ImmutableMap;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.chile.core.model.MetaPropertyPath;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.compatibility.ComponentValueListenerWrapper;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.ValueListener;
import com.haulmont.cuba.gui.export.ByteArrayDataProvider;
import com.haulmont.cuba.web.gui.components.imageresources.*;
import com.vaadin.server.Resource;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

public class WebImage extends WebAbstractComponent<com.vaadin.ui.Image> implements Image {

    protected ImageResource value;

    protected boolean editable;

    protected Datasource datasource;
    protected MetaPropertyPath metaPropertyPath;
    protected MetaProperty metaProperty;

    protected Datasource.ItemPropertyChangeListener itemPropertyChangeListener;

    protected static final Map<Class<? extends ImageResource>, Class<? extends ImageResource>> resourcesClasses;

    static {
        ImmutableMap.Builder<Class<? extends ImageResource>, Class<? extends ImageResource>> builder =
                new ImmutableMap.Builder<>();

        builder.put(UrlImageResource.class, WebUrlImageResource.class);
        builder.put(ClasspathImageResource.class, WebClasspathImageResource.class);
        builder.put(ThemeImageResource.class, WebThemeImageResource.class);
        builder.put(FileDescriptorImageResource.class, WebFileDescriptorImageResource.class);
        builder.put(FileImageResource.class, WebFileImageResource.class);
        builder.put(StreamImageResource.class, WebStreamImageResource.class);

        resourcesClasses = builder.build();
    }

    public WebImage() {
        component = new com.vaadin.ui.Image();
    }

    @Override
    public Datasource getDatasource() {
        return datasource;
    }

    @Override
    public MetaProperty getMetaProperty() {
        return metaPropertyPath.getMetaProperty();
    }

    @Override
    public MetaPropertyPath getMetaPropertyPath() {
        return metaPropertyPath;
    }

    @Override
    public void setDatasource(Datasource datasource, String property) {
        if ((datasource == null && property != null) || (datasource != null && property == null))
            throw new IllegalArgumentException("Datasource and property should be either null or not null at the same time");

        if (datasource == this.datasource && ((metaPropertyPath != null && metaPropertyPath.toString().equals(property)) ||
                (metaPropertyPath == null && property == null)))
            return;

        if (this.datasource != null) {
            metaProperty = null;
            metaPropertyPath = null;

            component.setSource(null);

            //noinspection unchecked
            this.datasource.removeItemPropertyChangeListener(itemPropertyChangeListener);
            itemPropertyChangeListener = null;

            this.datasource = null;
        }

        if (datasource != null) {
            //noinspection unchecked
            this.datasource = datasource;

            final MetaClass metaClass = datasource.getMetaClass();
            resolveMetaPropertyPath(metaClass, property);

            component.setSource(createSuitableResource(datasource, property));

            if (metaProperty.isReadOnly()) {
                setEditable(false);
            }

            itemPropertyChangeListener = e ->
                    component.setSource(createSuitableResource(datasource, property));

            //noinspection unchecked
            this.datasource.addItemPropertyChangeListener(itemPropertyChangeListener);
        }
    }

    protected Resource createSuitableResource(Datasource datasource, String property) {
        Entity item = datasource.getItem();
        if (item == null) {
            return null;
        }

        Object propertyValue = item.getValueEx(property);

        if (propertyValue instanceof String) {
            ThemeImageResource resource = createResource(ThemeImageResource.class);
            resource.setPath((String) propertyValue);
            return ((WebThemeImageResource) resource).getResource();
        }

        if (propertyValue instanceof FileDescriptor) {
            FileDescriptorImageResource resource = createResource(FileDescriptorImageResource.class);
            resource.setFileDescriptor((FileDescriptor) propertyValue);
            return ((WebFileDescriptorImageResource) resource).getResource();
        }

        if (propertyValue instanceof byte[]) {
            StreamImageResource resource = createResource(StreamImageResource.class);
            Supplier<InputStream> streamSupplier = () -> new ByteArrayDataProvider((byte[]) propertyValue).provide();
            resource.setStreamSupplier(streamSupplier);
            return ((WebStreamImageResource) resource).getResource();
        }

        return null;
    }

    protected void resolveMetaPropertyPath(MetaClass metaClass, String property) {
        metaPropertyPath = getResolvedMetaPropertyPath(metaClass, property);
        this.metaProperty = metaPropertyPath.getMetaProperty();
    }

    protected MetaPropertyPath getResolvedMetaPropertyPath(MetaClass metaClass, String property) {
        MetaPropertyPath metaPropertyPath = AppBeans.get(MetadataTools.NAME, MetadataTools.class)
                .resolveMetaPropertyPath(metaClass, property);
        Preconditions.checkNotNullArgument(metaPropertyPath, "Could not resolve property path '%s' in '%s'", property, metaClass);

        return metaPropertyPath;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
            return;
        }

        if (!(value instanceof WebAbstractImageResource)) {
            throw new IllegalArgumentException("setValue() accepts only ImageResource");
        }

        Resource resource = ((WebAbstractImageResource) value).getResource();
        component.setSource(resource);
    }

    @Override
    public void addListener(ValueListener listener) {
        addValueChangeListener(new ComponentValueListenerWrapper(listener));

    }

    @Override
    public void removeListener(ValueListener listener) {
        removeValueChangeListener(new ComponentValueListenerWrapper(listener));
    }

    @Override
    public void addValueChangeListener(ValueChangeListener listener) {
        getEventRouter().addListener(ValueChangeListener.class, listener);
    }

    @Override
    public void removeValueChangeListener(ValueChangeListener listener) {
        getEventRouter().removeListener(ValueChangeListener.class, listener);
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public <T extends ImageResource> T createResource(Class<T> type) {
        Class<? extends Image.ImageResource> imageResourceClass = resourcesClasses.get(type);
        if (imageResourceClass == null) {
            throw new IllegalStateException(String.format("Can't find image resource class for '%s'", type.getTypeName()));
        }

        try {
            return type.cast(imageResourceClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Error creating the '%s' image resource instance",
                    type.getTypeName()), e);
        }
    }

    public abstract static class WebAbstractImageResource implements WebImageResource {
        protected Resource resource;

        @Override
        public Resource getResource() {
            if (resource == null) {
                createResource();
            }
            return resource;
        }

        protected abstract void createResource();
    }
}
