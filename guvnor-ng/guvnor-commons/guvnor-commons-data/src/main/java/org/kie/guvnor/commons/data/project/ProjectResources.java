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

package org.kie.guvnor.commons.data.project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

import org.uberfire.backend.vfs.Path;

/**
 *
 */
@ApplicationScoped
public class ProjectResources {

    private Map<Path, Path> projectsAndResource = new HashMap<Path, Path>();
    private Map<Path, Path> resourceAndProject  = new HashMap<Path, Path>();

    public ProjectResources() {

    }

    public Path getProject( final Path resource ) {
        return resourceAndProject.get( resource );
    }

    public void setupProject( final Path project,
                              final Collection<Path> resources ) {
        for ( final Path resource : resources ) {
            projectsAndResource.put( project, resource );
            resourceAndProject.put( resource, project );
        }
        projectsAndResource.put( project, project );
        resourceAndProject.put( project, project );
    }
}