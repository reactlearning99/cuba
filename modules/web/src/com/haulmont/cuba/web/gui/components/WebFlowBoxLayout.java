/*
 * Copyright (c) 2008-2016 Haulmont.
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
 *
 */
package com.haulmont.cuba.web.gui.components;

import com.haulmont.cuba.gui.components.FlowBoxLayout;
import com.haulmont.cuba.web.toolkit.ui.CubaFlowLayout;
import com.vaadin.shared.ui.MarginInfo;
import org.apache.commons.lang.StringUtils;

public class WebFlowBoxLayout extends WebAbstractOrderedLayout<CubaFlowLayout> implements FlowBoxLayout {

    protected static final String FLOWLAYOUT_STYLENAME = "c-flowlayout";

    public WebFlowBoxLayout() {
        component = new CubaFlowLayout();
    }

    @Override
    public void setStyleName(String styleName) {
        super.setStyleName(styleName);

        component.addStyleName(FLOWLAYOUT_STYLENAME);
    }

    @Override
    public String getStyleName() {
        return StringUtils.normalizeSpace(super.getStyleName().replace(FLOWLAYOUT_STYLENAME, ""));
    }

    @Override
    public void setMargin(boolean enable) {
        component.setMargin(enable);
    }

    @Override
    public boolean hasMargin() {
        return component.getMargin().hasAll();
    }

    @Override
    public void setMargin(boolean topEnable, boolean rightEnable, boolean bottomEnable, boolean leftEnable) {
        component.setMargin(new MarginInfo(topEnable, rightEnable, bottomEnable, leftEnable));
    }

    @Override
    public boolean hasTopMargin() {
        return component.getMargin().hasTop();
    }

    @Override
    public boolean hasRightMargin() {
        return component.getMargin().hasRight();
    }

    @Override
    public boolean hasBottomMargin() {
        return component.getMargin().hasBottom();
    }

    @Override
    public boolean hasLeftMargin() {
        return component.getMargin().hasLeft();
    }

    @Override
    public void setSpacing(boolean enabled) {
        component.setSpacing(enabled);
    }

    @Override
    public boolean hasSpacing() {
        return component.isSpacing();
    }
}