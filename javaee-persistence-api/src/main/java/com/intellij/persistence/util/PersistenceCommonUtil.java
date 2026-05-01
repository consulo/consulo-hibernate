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

package com.intellij.persistence.util;

import gnu.trove.THashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.jetbrains.annotations.NonNls;
import com.intellij.jam.model.util.JamCommonUtil;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.project.Project;
import consulo.module.content.ModuleRootManager;
import consulo.module.content.ProjectRootManager;
import consulo.util.lang.Comparing;
import consulo.util.lang.function.Condition;
import consulo.util.dataholder.Key;
import consulo.util.lang.Pair;
import consulo.util.lang.StringUtil;
import consulo.virtualFileSystem.VirtualFile;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistenceQuery;
import com.intellij.persistence.model.PersistentEmbeddedAttribute;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.PersistentRelationshipAttribute;
import com.intellij.persistence.model.TableInfoProvider;
import com.intellij.persistence.roles.PersistenceClassRole;
import com.intellij.persistence.roles.PersistenceClassRoleEnum;
import com.intellij.persistence.roles.PersistenceRoleHolder;
import com.intellij.java.language.psi.JavaPsiFacade;
import com.intellij.java.language.psi.PsiArrayType;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiClassType;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiManager;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import com.intellij.java.language.psi.PsiTypeParameter;
import consulo.language.psi.scope.GlobalSearchScope;
import consulo.project.content.scope.ProjectScopes;
import consulo.application.util.CachedValue;
import consulo.application.util.CachedValueProvider;
import consulo.application.util.CachedValuesManager;
import com.intellij.java.language.psi.util.PropertyUtil;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.language.editor.util.PsiUtilBase;
import com.intellij.java.language.psi.util.TypeConversionUtil;
import consulo.application.util.query.ExecutorsQuery;
import java.util.function.Function;
import consulo.application.util.function.Processor;
import consulo.application.util.query.Query;
import consulo.application.util.query.QueryExecutor;
import consulo.util.collection.ContainerUtil;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.GenericValue;
import consulo.java.persistence.module.extension.PersistenceModuleExtension;
import consulo.module.extension.ModuleExtension;

/**
 * @author Gregory.Shrago
 */
public class PersistenceCommonUtil
{
	@NonNls
	public static final String PERSISTENCE_FRAMEWORK_GROUP_ID = "persistence";

	private PersistenceCommonUtil()
	{
	}

	@Nonnull
	public static List<PersistenceModuleExtension<?, ?>> getAllPersistenceFacets(@Nonnull final Project project)
	{
		final List<PersistenceModuleExtension<?, ?>> result = new ArrayList<>();
		for(Module module : ModuleManager.getInstance(project).getModules())
		{
			result.addAll(getAllPersistenceFacets(module));
		}
		return result;
	}

	@Nonnull
	public static List<PersistenceModuleExtension<?, ?>> getAllPersistenceFacets(@Nonnull final Module module)
	{
		final List<PersistenceModuleExtension<?, ?>> result = new ArrayList<>();
		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
		for(ModuleExtension extension : moduleRootManager.getExtensions())
		{
			if(extension instanceof PersistenceModuleExtension)
			{
				result.add((PersistenceModuleExtension<?, ?>) extension);
			}
		}
		return result;
	}

	private static Key<CachedValue<List<PersistenceModuleExtension<?, ?>>>> MODULE_PERSISTENCE_FACETS = Key.create("MODULE_PERSISTENCE_FACETS");

	@Nonnull
	public static List<PersistenceModuleExtension<?, ?>> getAllPersistenceFacetsWithDependencies(@Nonnull final Module module)
	{
		CachedValue<List<PersistenceModuleExtension<?, ?>>> cachedValue = module.getUserData(MODULE_PERSISTENCE_FACETS);
		if(cachedValue == null)
		{
			cachedValue = CachedValuesManager.getManager(module.getProject()).createCachedValue(() ->
			{
				final Set<Module> modules = new THashSet<>();
				modules.addAll(Arrays.asList(JamCommonUtil.getAllDependentModules(module)));
				modules.addAll(Arrays.asList(JamCommonUtil.getAllModuleDependencies(module)));
				final Set<PersistenceModuleExtension<?, ?>> facets = new THashSet<>();
				for(Module depModule : modules)
				{
					facets.addAll(getAllPersistenceFacets(depModule));
				}
				return new CachedValueProvider.Result<>(new ArrayList<>(facets), ProjectRootManager.getInstance(module.getProject()));
			}, false);
			module.putUserData(MODULE_PERSISTENCE_FACETS, cachedValue);
		}
		return cachedValue.getValue();
	}

	public static PersistenceModelBrowser createSameUnitsModelBrowser(@Nullable final PsiElement sourceElement)
	{
		final PsiClass sourceClass;
		final Set<PersistencePackage> unitSet;
		if(sourceElement == null || (sourceClass = PsiTreeUtil.getParentOfType(sourceElement, PsiClass.class, false)) == null)
		{
			unitSet = null;
		}
		else
		{
			unitSet = getAllPersistenceUnits(sourceClass, new THashSet<PersistencePackage>());
		}
		return createUnitsAndTypeMapper(unitSet);
	}

	public static PersistenceModelBrowser createSameUnitsModelBrowser(@Nullable final DomElement sourceDom)
	{
		final Set<PersistencePackage> unitSet;
		final DomElement rootElement;
		if(sourceDom == null || !((rootElement = DomUtil.getRoot(sourceDom)) instanceof PersistenceMappings))
		{
			unitSet = null;
		}
		else
		{
			unitSet = new THashSet<>(PersistenceHelper.getHelper().getSharedModelBrowser().getPersistenceUnits((PersistenceMappings) rootElement));
		}
		return createUnitsAndTypeMapper(unitSet);
	}

	public static PersistenceModelBrowser createUnitsAndTypeMapper(@Nullable final Set<PersistencePackage> unitSet)
	{
		return PersistenceHelper.getHelper().createModelBrowser().setRoleFilter(new Condition<PersistenceClassRole>()
		{
			public boolean value(final PersistenceClassRole role)
			{
				final PersistentObject object = role.getPersistentObject();
				final PersistenceClassRoleEnum roleType = role.getType();
				return roleType != PersistenceClassRoleEnum.ENTITY_LISTENER && object != null && (unitSet == null || unitSet.contains(role.getPersistenceUnit()));
			}
		});
	}

	public static PersistenceModelBrowser createFacetAndUnitModelBrowser(final PersistenceFacetBase facet, final PersistencePackage unit, final PersistenceClassRoleEnum type)
	{
		return PersistenceHelper.getHelper().createModelBrowser().setRoleFilter(new Condition<PersistenceClassRole>()
		{
			public boolean value(final PersistenceClassRole role)
			{
				final PersistentObject object = role.getPersistentObject();
				return object != null && (type == null || role.getType() == type) && (unit == null || unit.equals(role.getPersistenceUnit())) && (facet == null || facet.equals(role.getFacet()));
			}
		});
	}

	@Nullable
	public static PsiType getTargetEntityType(final PsiMember psiMember)
	{
		return getTargetEntityType(PropertyUtil.getPropertyType(psiMember));
	}

	@Nullable
	public static PsiType getTargetEntityType(final PsiType type)
	{
		final Pair<JavaContainerType, PsiType> containerType = getContainerType(type);
		return containerType.getSecond();
	}

	public static <T extends Collection<PersistencePackage>> T getAllPersistenceUnits(@Nullable final PsiClass sourceClass, @Nonnull final T result)
	{
		for(PersistenceClassRole role : getPersistenceRoles(sourceClass))
		{
			ContainerUtil.addIfNotNull(result, role.getPersistenceUnit());
		}
		return result;
	}


	@Nonnull
	public static PersistenceClassRole[] getPersistenceRoles(@Nullable final PsiClass aClass)
	{
		if(aClass == null || !aClass.isValid())
		{
			return PersistenceClassRole.EMPTY_ARRAY;
		}
		return PersistenceRoleHolder.getInstance(aClass.getProject()).getRoles(aClass);
	}

	@Nonnull
	public static <T extends PersistencePackage, V extends PersistenceMappings> Collection<V> getDomEntityMappings(final Class<V> mappingsClass, final T unit, final PersistenceFacetBase<?, T> facet)
	{
		final THashSet<V> result = new THashSet<>();
		for(PersistenceMappings mappings : facet.getDefaultEntityMappings(unit))
		{
			if(mappingsClass.isAssignableFrom(mappings.getClass()))
			{
				result.add((V) mappings);
			}
		}
		for(GenericValue<V> value : unit.getModelHelper().getMappingFiles(mappingsClass))
		{
			ContainerUtil.addIfNotNull(result, value.getValue());
		}
		return result;
	}

	public static boolean isSameTable(final TableInfoProvider table1, final TableInfoProvider table2)
	{
		if(table1 == null || table2 == null)
		{
			return false;
		}
		final String name1 = table1.getTableName().getValue();
		return StringUtil.isNotEmpty(name1) && Comparing.equal(name1, table2.getTableName().getValue()) && Comparing.equal(table1.getSchema().getValue(), table2.getSchema().getValue()) && Comparing
				.equal(table1.getCatalog().getValue(), table2.getCatalog().getValue());
	}

	public static String getUniqueId(final PsiElement psiElement)
	{
		final VirtualFile virtualFile = psiElement == null ? null : PsiUtilBase.getVirtualFile(psiElement);
		return virtualFile == null ? "" : virtualFile.getUrl();
	}

	public static String getMultiplicityString(final boolean optional, final boolean many)
	{
		final String first = (optional ? "0" : "1");
		final String last = (many ? "*" : "1");
		return first.equals(last) ? first : first + ".." + last;
	}

	public static <T, V extends Collection<T>> V mapPersistenceRoles(final V result,
			final Project project,
			final PersistenceModuleExtension<?, ?> facet,
			final PersistencePackage unit,
			final Function<PersistenceClassRole, T> mapper)
	{
		final Collection<PersistenceClassRole> allRoles = PersistenceRoleHolder.getInstance(project).getClassRoleManager().getUserData(PersistenceRoleHolder.PERSISTENCE_CLASS_ROLES_KEY,
				PersistenceRoleHolder.PERSISTENCE_ALL_ROLES_DATA_KEY);
		if(allRoles != null)
		{
			for(PersistenceClassRole role : allRoles)
			{
				if((facet == null || facet == role.getFacet()) && (unit == null || unit == role.getPersistenceUnit()))
				{
					ContainerUtil.addIfNotNull(result, mapper.apply(role));
				}
			}
		}
		return result;
	}

	public static boolean haveCorrespondingMultiplicity(final PersistentRelationshipAttribute a1, final PersistentRelationshipAttribute a2)
	{
		return a1.getAttributeModelHelper().getRelationshipType().corresponds(a2.getAttributeModelHelper().getRelationshipType());
	}

	public static <T extends PersistencePackage> boolean processNamedQueries(final PersistenceModuleExtension<?, ?> facet, final boolean nativeQueries, final Processor<PersistenceQuery> processor)
	{
		return processNamedQueries(nativeQueries ? PersistenceRoleHolder.PERSISTENCE_ALL_NATIVE_QUERIES_DATA_KEY : PersistenceRoleHolder.PERSISTENCE_ALL_QUERIES_DATA_KEY, facet, processor);
	}

	private static <T extends PersistencePackage> boolean processNamedQueries(final Key<Map<PersistenceModuleExtension<?, ?>, Collection<PersistenceQuery>>> queriesDataKey,
			final PersistenceModuleExtension<?, T> facet,
			final Processor<PersistenceQuery> processor)
	{
		final Map<PersistenceModuleExtension<?, ?>, Collection<PersistenceQuery>> namedQueriesMap = PersistenceRoleHolder.getInstance(facet.getModule().getProject()).getClassRoleManager().getUserData
				(PersistenceRoleHolder.PERSISTENCE_CLASS_ROLES_KEY, queriesDataKey);
		if(namedQueriesMap != null)
		{
			final Collection<PersistenceQuery> queries = namedQueriesMap.get(facet);
			if(queries != null && !ContainerUtil.process(queries, processor))
			{
				return false;
			}
		}
		return true;
	}

	@Nonnull
	public static Pair<JavaContainerType, PsiType> getContainerType(final PsiType type)
	{
		if(type instanceof PsiArrayType)
		{
			return Pair.create(JavaContainerType.ARRAY, ((PsiArrayType) type).getComponentType());
		}
		final PsiClassType.ClassResolveResult classResolveResult = type instanceof PsiClassType ? ((PsiClassType) type).resolveGenerics() : null;
		if(classResolveResult == null)
		{
			return Pair.create(null, type);
		}
		final PsiClass psiClass = classResolveResult.getElement();
		if(psiClass == null)
		{
			return Pair.create(null, type);
		}
		final PsiManager manager = psiClass.getManager();
		final GlobalSearchScope scope = (GlobalSearchScope) ProjectScopes.getAllScope(manager.getProject());
		for(JavaContainerType collectionType : JavaContainerType.values())
		{
			if(collectionType == JavaContainerType.ARRAY)
			{
				continue;
			}
			final PsiClass aClass = JavaPsiFacade.getInstance(manager.getProject()).findClass(collectionType.getJavaBaseClassName(), scope);
			if(aClass != null && (manager.areElementsEquivalent(aClass, psiClass) || psiClass.isInheritor(aClass, true)))
			{
				final PsiTypeParameter[] typeParameters = aClass.getTypeParameters();
				final PsiType entityType;
				if(typeParameters.length > 0)
				{
					entityType = TypeConversionUtil.getSuperClassSubstitutor(aClass, psiClass, classResolveResult.getSubstitutor()).substitute(typeParameters[typeParameters.length - 1]);
				}
				else
				{
					entityType = PsiType.getJavaLangObject(manager, scope);
				}
				return Pair.create(collectionType, entityType);
			}
		}
		return Pair.create(null, type);
	}

	public static Query<PersistentObject> queryPersistentObjects(final PersistenceMappings mappings)
	{
		return new ExecutorsQuery<>(mappings, Collections.<QueryExecutor<PersistentObject, PersistenceMappings>>singletonList(new QueryExecutor<PersistentObject, PersistenceMappings>()
		{

			public boolean execute(PersistenceMappings queryParameters, java.util.function.Predicate<? super PersistentObject> consumer)
			{
				if(!ContainerUtil.process(queryParameters.getModelHelper().getPersistentEntities(), consumer))
				{
					return false;
				}
				if(!ContainerUtil.process(queryParameters.getModelHelper().getPersistentSuperclasses(), consumer))
				{
					return false;
				}
				if(!ContainerUtil.process(queryParameters.getModelHelper().getPersistentEmbeddables(), consumer))
				{
					return false;
				}
				return true;
			}
		}));
	}

	@Nullable
	public static PsiClass getTargetClass(final PersistentRelationshipAttribute attribute)
	{
		final GenericValue<PsiClass> classValue = attribute.getTargetEntityClass();
		final PsiClass targetClass;
		if(classValue.getStringValue() != null)
		{
			targetClass = classValue.getValue();
		}
		else
		{
			final PsiType entityType = getTargetEntityType(attribute.getPsiMember());
			targetClass = entityType instanceof PsiClassType ? ((PsiClassType) entityType).resolve() : null;
		}
		return targetClass;
	}

	@Nullable
	public static PsiClass getTargetClass(final PersistentEmbeddedAttribute attribute)
	{
		final GenericValue<PsiClass> classValue = attribute.getTargetEmbeddableClass();
		final PsiClass targetClass;
		if(classValue.getStringValue() != null)
		{
			targetClass = classValue.getValue();
		}
		else
		{
			final PsiType entityType = PropertyUtil.getPropertyType(attribute.getPsiMember());
			targetClass = entityType instanceof PsiClassType ? ((PsiClassType) entityType).resolve() : null;
		}
		return targetClass;
	}
}
