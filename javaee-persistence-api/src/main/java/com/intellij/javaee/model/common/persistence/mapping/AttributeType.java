package com.intellij.javaee.model.common.persistence.mapping;

import com.intellij.jam.reflect.JamMemberMeta;
import com.intellij.jpa.model.annotations.mapping.JamAttributeBase;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.RelationshipType;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class AttributeType {

    public static AttributeType getAttributeType(PersistentAttribute attribute) {
        return null;
    }

    private final Class myAttributeClass;
    private final String myTypeName;

    @SafeVarargs
    public AttributeType(Class attributeClass, String typeName, JamMemberMeta<PsiMember, ? extends JamAttributeBase>... jamMeta) {
        myAttributeClass = attributeClass;
        myTypeName = typeName;
    }

    public boolean isContainer() {
        return false;
    }

    public boolean isIdAttribute() {
        return false;
    }

    public boolean isBasic() {
        return false;
    }

    public boolean isEmbedded() {
        return false;
    }

    @Nullable
    public RelationshipType getRelationshipType() {
        return null;
    }

    protected boolean accepts(PersistentAttribute attribute) {
        return false;
    }

    @Nonnull
    public PsiType getDefaultPsiType(PersistentAttribute attribute) {
        return PsiType.VOID;
    }

    @Nonnull
    protected PsiType getDefaultElementPsiType(PersistentAttribute attribute) {
        return PsiType.VOID;
    }

    public Class getAttributeClass() {
        return myAttributeClass;
    }

    public String getTypeName() {
        return myTypeName;
    }

    public static AttributeType getAttributeTypeOrDefault(PersistentAttribute attribute) {
        AttributeType type = getAttributeType(attribute);
        return type != null ? type : new AttributeType(Object.class, "unknown");
    }

    public static AttributeType[] values() {
        return new AttributeType[0];
    }
}
