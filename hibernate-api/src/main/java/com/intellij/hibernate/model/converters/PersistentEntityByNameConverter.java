package com.intellij.hibernate.model.converters;

import consulo.project.Project;
import consulo.util.lang.Comparing;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistentEntity;
import com.intellij.persistence.model.PersistentRelationshipAttribute;
import com.intellij.persistence.roles.PersistenceClassRole;
import com.intellij.persistence.roles.PersistenceClassRoleEnum;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.java.language.psi.PsiClass;
import java.util.function.Function;
import consulo.util.collection.ContainerUtil;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.ResolvingConverter;
import consulo.xml.dom.DomUtil;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.*;

/**
 * @author Gregory.Shrago
 */
public class PersistentEntityByNameConverter extends ResolvingConverter<PersistentEntity> {
  @Nonnull
  public Collection<? extends PersistentEntity> getVariants(final ConvertContext context) {
    final DomElement root = DomUtil.getFileElement(context.getInvocationElement()).getRootElement();
    if (!(root instanceof PersistenceMappings)) return Collections.emptyList();
    final DomElement parent = context.getInvocationElement().getParent();
    final PsiClass psiClass = parent instanceof PersistentRelationshipAttribute? ((PersistentRelationshipAttribute)parent).getTargetEntityClass().getValue() : null;
    final HashSet<PersistencePackage> units = new HashSet<PersistencePackage>(PersistenceHelper.getHelper().getSharedModelBrowser().getPersistenceUnits((PersistenceMappings)root));

    final Function<PersistenceClassRole, PersistentEntity> mapper = new Function<PersistenceClassRole, PersistentEntity>() {
      public PersistentEntity apply(final PersistenceClassRole role) {
        if (role.getType() == PersistenceClassRoleEnum.ENTITY && units.contains(role.getPersistenceUnit())) {
          return (PersistentEntity)role.getPersistentObject();
        }
        return null;
      }
    };
    return psiClass != null? ContainerUtil.mapNotNull(PersistenceCommonUtil.getPersistenceRoles(psiClass), mapper) :
           PersistenceCommonUtil.mapPersistenceRoles(new ArrayList<PersistentEntity>(), root.getManager().getProject(), null, null, mapper);
  }

  public PersistentEntity fromString(@Nullable @NonNls final String s, final ConvertContext context) {
    if (s == null) return null;
    final DomElement root = DomUtil.getFileElement(context.getInvocationElement()).getRootElement();
    if (!(root instanceof PersistenceMappings)) return null;
    final Project project = root.getManager().getProject();
    final HashSet<PersistencePackage> units = new HashSet<PersistencePackage>(PersistenceHelper.getHelper().getSharedModelBrowser().getPersistenceUnits((PersistenceMappings)root));
    final ArrayList<PersistentEntity> result = PersistenceCommonUtil.mapPersistenceRoles(new ArrayList<PersistentEntity>(), project, null, null, new Function<PersistenceClassRole, PersistentEntity>() {
      public PersistentEntity apply(final PersistenceClassRole role) {
        if (role.getType() == PersistenceClassRoleEnum.ENTITY &&
            Comparing.equal(s, ((PersistentEntity)role.getPersistentObject()).getName().getValue()) &&
            units.contains(role.getPersistenceUnit())) {
          return (PersistentEntity)role.getPersistentObject();
        }
        return null;
      }
    });
    return result.isEmpty()? null : result.get(0);
  }

  public String toString(@Nullable PersistentEntity persistentEntity, final ConvertContext context) {
    return persistentEntity == null? null : persistentEntity.getName().getValue();
  }
}
