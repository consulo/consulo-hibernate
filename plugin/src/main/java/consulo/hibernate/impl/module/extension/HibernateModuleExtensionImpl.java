package consulo.hibernate.impl.module.extension;

import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import com.intellij.java.impl.util.descriptors.ConfigFile;
import com.intellij.java.impl.util.descriptors.ConfigFileContainer;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;
import com.intellij.javaee.JavaeePersistenceDescriptorsConstants;
import com.intellij.persistence.facet.PersistencePackageDefaults;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.validators.ModelValidator;
import consulo.hibernate.module.extension.HibernateModuleExtension;
import consulo.language.version.LanguageVersion;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.content.layer.extension.ModuleExtensionBase;
import consulo.sql.language.SqlLanguage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2026-05-04
 */
public class HibernateModuleExtensionImpl extends ModuleExtensionBase<HibernateModuleExtension> implements HibernateModuleExtension {

    public HibernateModuleExtensionImpl(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer) {
        super(id, moduleRootLayer);
    }

    @Override
    public ConfigFile[] getDescriptors() {
        return ConfigFile.EMPTY_ARRAY;
    }

    @Override
    public ConfigFileContainer getDescriptorsContainer() {
        return null;
    }

    @Nonnull
    @Override
    public List<PersistencePackage> getPersistenceUnits() {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public PersistenceMappings getAnnotationEntityMappings() {
        return null;
    }

    @Nonnull
    @Override
    public PersistenceMappings getEntityMappings(@Nonnull PersistencePackage unit) {
        throw new UnsupportedOperationException("getEntityMappings not yet wired in module-extension model");
    }

    @Nonnull
    @Override
    public List<? extends PersistenceMappings> getDefaultEntityMappings(@Nonnull PersistencePackage unit) {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public Class<? extends PersistencePackage> getPersistenceUnitClass() {
        return PersistencePackage.class;
    }

    @Nonnull
    @Override
    public Map<ConfigFileMetaData, Class<? extends PersistenceMappings>> getSupportedDomMappingFormats() {
        Map<ConfigFileMetaData, Class<? extends PersistenceMappings>> map = new LinkedHashMap<>();
        map.put(HibernateDescriptorsConstants.HIBERNATE_MAPPING_META_DATA, HbmHibernateMapping.class);
        map.put(JavaeePersistenceDescriptorsConstants.ORM_XML_META_DATA,
                com.intellij.javaee.model.xml.persistence.mapping.EntityMappings.class);
        return map;
    }

    @Override
    public String getDataSourceId(@Nonnull PersistencePackage unit) {
        return null;
    }

    @Override
    public void setDataSourceId(@Nonnull PersistencePackage unit, String dataSourceId) {
        // no-op until per-unit data sources are wired up
    }

    private static final String HQL_VERSION_ID = "HQL";

    @Nullable
    @Override
    public LanguageVersion getQlLanguage() {
        for (LanguageVersion version : SqlLanguage.INSTANCE.getVersions()) {
            if (HQL_VERSION_ID.equals(version.getId())) {
                return version;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public ModelValidator getModelValidator(@Nullable PersistencePackage unit) {
        return EMPTY_MODEL_VALIDATOR;
    }

    private static final ModelValidator EMPTY_MODEL_VALIDATOR = new ModelValidator() {
        @Override
        public boolean checkEmbeddable(com.intellij.java.language.psi.PsiClass aClass,
                                       com.intellij.persistence.model.PersistentEmbeddable embeddable,
                                       boolean isReportingErrors,
                                       consulo.language.editor.inspection.ProblemsHolder holder) {
            return true;
        }

        @Override
        public boolean checkEntity(com.intellij.java.language.psi.PsiClass aClass,
                                   com.intellij.persistence.model.PersistentEntity entity,
                                   boolean isReportingErrors,
                                   consulo.language.editor.inspection.ProblemsHolder holder) {
            return true;
        }

        @Override
        public boolean checkListener(com.intellij.java.language.psi.PsiClass aClass,
                                     com.intellij.persistence.model.PersistenceListener listener,
                                     boolean isReporingErrors,
                                     consulo.language.editor.inspection.ProblemsHolder holder) {
            return true;
        }

        @Override
        public boolean checkSuperclass(com.intellij.java.language.psi.PsiClass aClass,
                                       com.intellij.persistence.model.PersistentSuperclass superclass,
                                       boolean isReportingErrors,
                                       consulo.language.editor.inspection.ProblemsHolder holder) {
            return true;
        }

        @Override
        public String getRelationshipAttributeTypeProblem(com.intellij.java.language.psi.PsiType type,
                                                          com.intellij.persistence.model.RelationshipType relationshipType,
                                                          com.intellij.java.language.psi.PsiClass targetEntityClass,
                                                          String attributeTypeName) {
            return null;
        }

        @Override
        public String getAttributeTypeProblem(com.intellij.java.language.psi.PsiType type,
                                              boolean isContainer,
                                              boolean isEmbedded,
                                              boolean isLob,
                                              String attributeTypeName) {
            return null;
        }
    };

    @Nonnull
    @Override
    public Class[] getInspectionToolClasses() {
        return new Class[0];
    }

    @Nonnull
    @Override
    public PersistencePackageDefaults getPersistenceUnitDefaults(@Nonnull PersistencePackage unit) {
        return EMPTY_DEFAULTS;
    }

    private static final PersistencePackageDefaults EMPTY_DEFAULTS = new PersistencePackageDefaults() {
        @Override
        public String getSchema() {
            return null;
        }

        @Override
        public String getCatalog() {
            return null;
        }

        @Override
        public com.intellij.java.language.psi.util.PropertyMemberType getAccess() {
            return null;
        }
    };
}
