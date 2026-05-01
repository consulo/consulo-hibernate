/*
 * Copyright 2000-2007 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.persistence.roles;

import com.intellij.java.language.psi.PsiClass;
import com.intellij.javaee.model.role.ClassRoleManager;
import com.intellij.persistence.model.PersistenceQuery;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.java.persistence.module.extension.PersistenceModuleExtension;
import consulo.project.Project;
import consulo.util.dataholder.Key;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Map;

@ServiceAPI(ComponentScope.PROJECT)
public abstract class PersistenceRoleHolder
{
	public static final Key<PersistenceClassRole> PERSISTENCE_CLASS_ROLES_KEY = Key.create("PERSISTENCE_CLASS_ROLES_KEY");
	public static final Key<Collection<PersistenceClassRole>> PERSISTENCE_ALL_ROLES_DATA_KEY = Key.create("PERSISTENCE_ALL_ROLES_DATA_KEY");
	public static final Key<Map<PersistenceModuleExtension<?, ?>, Collection<PersistenceQuery>>> PERSISTENCE_ALL_QUERIES_DATA_KEY = Key.create("PERSISTENCE_ALL_QUERIES_DATA_KEY");
	public static final Key<Map<PersistenceModuleExtension<?, ?>, Collection<PersistenceQuery>>> PERSISTENCE_ALL_NATIVE_QUERIES_DATA_KEY = Key.create("PERSISTENCE_ALL_NATIVE_QUERIES_DATA_KEY");

	public static PersistenceRoleHolder getInstance(Project project)
	{
		return project.getInstance(PersistenceRoleHolder.class);
	}

	@Nonnull
	public abstract ClassRoleManager getClassRoleManager();

	@Nonnull
	public abstract PersistenceClassRole[] getRoles(PsiClass aClass);

	@Nonnull
	public abstract PersistenceClassRole[] getRolesNoRebuild(final PsiClass aClass);

}
