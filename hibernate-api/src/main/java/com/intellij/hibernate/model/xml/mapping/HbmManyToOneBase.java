/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.LazyTypeConverter;
import com.intellij.hibernate.model.enums.LazyType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface HbmManyToOneBase extends JavaeeDomModelElement, HbmRelationAttributeBase.AnyToOneBase,
                                         HbmColumnsHolderBase {

	@Nonnull
        @Convert(LazyTypeConverter.class)
        GenericAttributeValue<LazyType> getLazy();

	@Nonnull
	GenericAttributeValue<String> getForeignKey();

	@Nonnull
	List<HbmMeta> getMetas();
	HbmMeta addMeta();

}