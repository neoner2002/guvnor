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

package org.kie.guvnor.guided.backend.server;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.guvnor.commons.data.workingset.WorkingSetConfigData;
import org.kie.guvnor.commons.service.validation.model.BuilderResult;
import org.kie.guvnor.commons.service.verification.model.AnalysisReport;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.kie.guvnor.datamodel.service.DataModelService;
import org.kie.guvnor.guided.backend.server.util.BRDRLPersistence;
import org.kie.guvnor.guided.backend.server.util.BRDRTPersistence;
import org.kie.guvnor.guided.backend.server.util.BRLPersistence;
import org.kie.guvnor.guided.backend.server.util.BRXMLPersistence;
import org.kie.guvnor.guided.model.GuidedEditorContent;
import org.kie.guvnor.guided.model.GuidedEditorTContent;
import org.kie.guvnor.guided.model.RuleModel;
import org.kie.guvnor.guided.model.templates.TemplateModel;
import org.kie.guvnor.guided.service.GuidedEditorService;
import org.kie.guvnor.project.service.ProjectService;
import org.uberfire.backend.vfs.Path;
import org.uberfire.backend.vfs.VFSService;

@Service
@ApplicationScoped
public class GuidedEditorServiceImpl
        implements GuidedEditorService {

    @Inject
    private VFSService vfs;

    @Inject
    private ProjectService projectService;

    @Inject
    private DataModelService dataModelService;

    @Override
    public GuidedEditorContent loadContent( final Path path ) {
        final BRLPersistence p = BRXMLPersistence.getInstance();

        final RuleModel model = p.unmarshal( vfs.readAllString( path ) );
        final DataModelOracle dataModel = dataModelService.getDataModel( projectService.resolveProject( path ) );

        return new GuidedEditorContent( dataModel, model );
    }

    @Override
    public GuidedEditorTContent loadTemplateModel( final Path path ) {
        return null;
    }

    @Override
    public void save( final Path path,
                      final RuleModel model ) {
        if ( model instanceof TemplateModel ) {

        }
    }

    @Override
    public String[] loadDropDownExpression( final String[] valuePairs,
                                            final String expression ) {
        return new String[ 0 ];
    }

    @Override
    public String toSource( final RuleModel model ) {
        if ( model instanceof TemplateModel ) {
            return BRDRTPersistence.getInstance().marshal( model );
        }
        return BRDRLPersistence.getInstance().marshal( model );
    }

    @Override
    public AnalysisReport verify( final Path path,
                                  final RuleModel content,
                                  final Collection<WorkingSetConfigData> activeWorkingSets ) {
        //TODO {porcelli} verify
        return new AnalysisReport();
    }

    @Override
    public BuilderResult validate( final Path path,
                                   final RuleModel content ) {
        //TODO {porcelli} validate
        return new BuilderResult();
    }

    @Override
    public boolean isValid( final Path path,
                            final RuleModel content ) {
        return !validate( path, content ).hasLines();
    }
}