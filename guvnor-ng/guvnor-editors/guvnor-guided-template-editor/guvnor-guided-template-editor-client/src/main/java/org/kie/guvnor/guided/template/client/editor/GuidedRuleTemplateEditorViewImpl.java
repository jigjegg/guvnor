/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.guided.template.client.editor;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import org.drools.guvnor.models.guided.template.shared.TemplateModel;
import org.kie.guvnor.commons.ui.client.resources.i18n.CommonConstants;
import org.kie.guvnor.commons.ui.client.widget.LoadingView;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.kie.guvnor.guided.rule.client.editor.RuleModeller;
import org.uberfire.backend.vfs.Path;

/**
 * Guided Rule Template Editor View implementation
 */
public class GuidedRuleTemplateEditorViewImpl extends Composite implements GuidedRuleTemplateEditorView {

    private final SimplePanel panel = new SimplePanel();
    private RuleModeller modeller = null;

    public GuidedRuleTemplateEditorViewImpl() {
        panel.setWidth( "100%" );
        initWidget( panel );
    }

    @Override
    public void setContent( final Path path,
                            final TemplateModel model,
                            final DataModelOracle dataModel,
                            final EventBus eventBus,
                            final boolean isReadOnly ) {
        this.modeller = new RuleModeller( path,
                                          model,
                                          dataModel,
                                          new TemplateModellerWidgetFactory(),
                                          eventBus,
                                          isReadOnly );
        panel.add( this.modeller );
    }

    @Override
    public TemplateModel getContent() {
        return (TemplateModel) modeller.getRuleModeller().getModel();
    }

    @Override
    public boolean isDirty() {
        //The Modeller widget isn't set until after the content has been loaded from an asynchronous call to
        //the server. It is therefore possible that the User attempts to close the tab before Modeller is set
        return ( modeller == null ) ? false : modeller.getRuleModeller().isDirty();
    }

    @Override
    public void setNotDirty() {
    }

    @Override
    public boolean confirmClose() {
        return Window.confirm( CommonConstants.INSTANCE.DiscardUnsavedData() );
    }

    @Override
    public void showBusyIndicator( final String message ) {
        LoadingView.show( message );
    }

    @Override
    public void hideBusyIndicator() {
        LoadingView.hide();
    }

}