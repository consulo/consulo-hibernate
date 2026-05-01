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

package com.intellij.persistence;

import com.intellij.persistence.icon.icon.PersistenceIconGroup;
import consulo.ui.image.Image;

/**
 * @author Gregory.Shrago
 */
public interface PersistenceIcons {
	Image PROPERTIES_ICON = Image.empty(16);
	Image RELATIONSHIP_ICON = PersistenceIconGroup.relationship();
	Image SYNCHRONIZE_ICON = Image.empty(16);
	Image SELECT_ALL_ICON = Image.empty(16);
	Image UNSELECT_ALL_ICON = Image.empty(16);

	Image CONSOLE_ICON = PersistenceIconGroup.console();
	Image CONSOLE_OUTPUT_ICON = Image.empty(16);

	Image OVR_INHERITED_ATTRIBUTE = Image.empty(16);
	Image OVR_EMBEDDED_ATTRIBUTE = PersistenceIconGroup.ovrembeddedattribute();

	// Persistence model icons
	Image PERSISTENCE_UNIT_ICON = PersistenceIconGroup.persistenceunit();
	Image ENTITY_ICON = PersistenceIconGroup.persistenceentity();
	Image MAPPED_SUPERCLASS_ICON = PersistenceIconGroup.mappedsuperclass();
	Image EMBEDDABLE_ICON = PersistenceIconGroup.embeddable();
	Image ID_ATTRIBUTE_ICON = PersistenceIconGroup.idattribute();
	Image ATTRIBUTE_ICON = PersistenceIconGroup.attribute();
	Image ID_RELATIONSHIP_ICON = PersistenceIconGroup.relationship();
}
