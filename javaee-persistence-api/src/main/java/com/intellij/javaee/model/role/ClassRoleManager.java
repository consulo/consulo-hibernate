package com.intellij.javaee.model.role;

import jakarta.annotation.Nonnull;

import consulo.util.dataholder.Key;
import consulo.util.dataholder.UserDataHolder;
import com.intellij.persistence.roles.PersistenceClassRole;

/**
 * TODO [VISTALL] STUB!!!
 */
public interface ClassRoleManager extends UserDataHolder
{
	<T> T getUserData(Key<PersistenceClassRole> key, @Nonnull Key<T> value);
}
