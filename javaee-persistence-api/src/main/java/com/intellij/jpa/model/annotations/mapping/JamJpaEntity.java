package com.intellij.jpa.model.annotations.mapping;

import com.intellij.jam.JamElement;
import com.intellij.jam.reflect.JamAnnotationMeta;
import com.intellij.jam.reflect.JamClassMeta;
import com.intellij.java.language.codeInsight.AnnotationUtil;
import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import jakarta.annotation.Nullable;

/**
 * JAM class-level model for @Entity + @Table annotated persistence classes.
 */
public class JamJpaEntity implements JamElement {

    public static final JamAnnotationMeta ENTITY_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.ENTITY_ANNO);

    public static final JamAnnotationMeta TABLE_ANNO_META =
        new JamAnnotationMeta(JavaeePersistenceConstants.TABLE_ANNO);

    public static final JamClassMeta<JamJpaEntity> META =
        new JamClassMeta<>(JamJpaEntity.class)
            .addAnnotation(ENTITY_ANNO_META);

    private final PsiClass myClass;

    public JamJpaEntity(PsiClass psiClass) {
        myClass = psiClass;
    }

    public PsiClass getPsiClass() {
        return myClass;
    }

    /** Returns the entity name from @Entity(name=...), or null if not specified. */
    @Nullable
    public String getEntityName() {
        PsiAnnotation anno = AnnotationUtil.findAnnotation(myClass, JavaeePersistenceConstants.ENTITY_ANNO);
        if (anno == null) return null;
        return AnnotationUtil.getDeclaredStringAttributeValue(anno, "name");
    }

    /** Returns the effective entity name: from @Entity(name=...) or the simple class name. */
    public String getEffectiveEntityName() {
        String name = getEntityName();
        return (name != null && !name.isEmpty()) ? name : myClass.getName();
    }

    /** Returns the @Entity annotation, or null if not present. */
    @Nullable
    public PsiAnnotation getEntityAnnotation() {
        return AnnotationUtil.findAnnotation(myClass, JavaeePersistenceConstants.ENTITY_ANNO);
    }

    /** Returns the table name from @Table(name=...), or null if @Table is absent or name is not set. */
    @Nullable
    public String getTableName() {
        PsiAnnotation tableAnno = AnnotationUtil.findAnnotation(myClass, JavaeePersistenceConstants.TABLE_ANNO);
        if (tableAnno == null) return null;
        return AnnotationUtil.getDeclaredStringAttributeValue(tableAnno, "name");
    }

    /** Returns the effective table name: from @Table(name=...) or the entity name. */
    public String getEffectiveTableName() {
        String tableName = getTableName();
        return (tableName != null && !tableName.isEmpty()) ? tableName : getEffectiveEntityName();
    }

    /** Returns the catalog from @Table(catalog=...), or null. */
    @Nullable
    public String getCatalog() {
        PsiAnnotation tableAnno = AnnotationUtil.findAnnotation(myClass, JavaeePersistenceConstants.TABLE_ANNO);
        if (tableAnno == null) return null;
        return AnnotationUtil.getDeclaredStringAttributeValue(tableAnno, "catalog");
    }

    /** Returns the schema from @Table(schema=...), or null. */
    @Nullable
    public String getSchema() {
        PsiAnnotation tableAnno = AnnotationUtil.findAnnotation(myClass, JavaeePersistenceConstants.TABLE_ANNO);
        if (tableAnno == null) return null;
        return AnnotationUtil.getDeclaredStringAttributeValue(tableAnno, "schema");
    }
}
