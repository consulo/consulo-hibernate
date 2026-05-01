package com.intellij.jpa.model.annotations.mapping;

import com.intellij.jam.reflect.JamAnnotationMeta;
import com.intellij.jam.reflect.JamMemberArchetype;
import com.intellij.jam.reflect.JamMemberMeta;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import com.intellij.persistence.model.PersistentAttribute;

public class AttributeBaseImpl extends JamAttributeBase {
    @SuppressWarnings("unchecked")
    public static final JamMemberArchetype<PsiMember, JamAttributeBase> ATTRIBUTE_ARCHETYPE = new JamMemberArchetype<>();

    public static final JamAnnotationMeta ID_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.ID_ANNO);
    public static final JamAnnotationMeta EMBEDDED_ID_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.EMBEDDED_ID_ANNO);
    public static final JamAnnotationMeta VERSION_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.VERSION_ANNO);
    public static final JamAnnotationMeta BASIC_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.BASIC_ANNO);
    public static final JamAnnotationMeta ONE_TO_ONE_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.ONE_TO_ONE_ANNO);
    public static final JamAnnotationMeta ONE_TO_MANY_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.ONE_TO_MANY_ANNO);
    public static final JamAnnotationMeta MANY_TO_ONE_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.MANY_TO_ONE_ANNO);
    public static final JamAnnotationMeta MANY_TO_MANY_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.MANY_TO_MANY_ANNO);
    public static final JamAnnotationMeta EMBEDDED_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.EMBEDDED_ANNO);

    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> ID_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, IdImpl.class).addAnnotation(ID_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> EMBEDDED_ID_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, EmbeddedIdImpl.class).addAnnotation(EMBEDDED_ID_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> VERSION_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, VersionImpl.class).addAnnotation(VERSION_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> BASIC_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, BasicImpl.class).addAnnotation(BASIC_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> ONE_TO_ONE_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, OneToOneImpl.class).addAnnotation(ONE_TO_ONE_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> ONE_TO_MANY_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, OneToManyImpl.class).addAnnotation(ONE_TO_MANY_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> MANY_TO_ONE_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, ManyToOneImpl.class).addAnnotation(MANY_TO_ONE_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> MANY_TO_MANY_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, ManyToManyImpl.class).addAnnotation(MANY_TO_MANY_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> EMBEDDED_ATTR_META =
        new JamMemberMeta<>(ATTRIBUTE_ARCHETYPE, EmbeddedImpl.class).addAnnotation(EMBEDDED_ANNO_META);
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> BASIC_COLLECTION_ATTR_META = null;
    public static final JamMemberMeta<PsiMember, ? extends JamAttributeBase> EMBEDDED_COLLECTION_ATTR_META = null;

    private final PsiMember myMember;

    public AttributeBaseImpl(PsiMember member) {
        myMember = member;
    }

    @Override
    public PsiMember getPsiMember() {
        return myMember;
    }

    public static PersistentAttribute getAttribute(PsiMember member) {
        return null;
    }

    // Concrete inner implementations per annotation type

    public static class IdImpl extends AttributeBaseImpl {
        public IdImpl(PsiMember member) { super(member); }
    }

    public static class EmbeddedIdImpl extends AttributeBaseImpl {
        public EmbeddedIdImpl(PsiMember member) { super(member); }
    }

    public static class VersionImpl extends AttributeBaseImpl {
        public VersionImpl(PsiMember member) { super(member); }
    }

    public static class BasicImpl extends AttributeBaseImpl {
        public BasicImpl(PsiMember member) { super(member); }
    }

    public static class OneToOneImpl extends AttributeBaseImpl {
        public OneToOneImpl(PsiMember member) { super(member); }
    }

    public static class OneToManyImpl extends AttributeBaseImpl {
        public OneToManyImpl(PsiMember member) { super(member); }
    }

    public static class ManyToOneImpl extends AttributeBaseImpl {
        public ManyToOneImpl(PsiMember member) { super(member); }
    }

    public static class ManyToManyImpl extends AttributeBaseImpl {
        public ManyToManyImpl(PsiMember member) { super(member); }
    }

    public static class EmbeddedImpl extends AttributeBaseImpl {
        public EmbeddedImpl(PsiMember member) { super(member); }
    }
}
