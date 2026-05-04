package consulo.java.persistence.module.extension;

import com.intellij.java.impl.util.descriptors.ConfigFile;
import com.intellij.java.impl.util.descriptors.ConfigFileContainer;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;
import com.intellij.persistence.facet.PersistencePackageDefaults;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.validators.ModelValidator;
import consulo.annotation.access.RequiredReadAction;
import consulo.language.psi.PsiElement;
import consulo.language.util.ModuleUtilCore;
import consulo.language.version.LanguageVersion;
import consulo.module.extension.ModuleExtension;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author VISTALL
 * @since 2018-07-06
 */
public interface PersistenceModuleExtension<T extends PersistenceModuleExtension<T, Unit>, Unit extends PersistencePackage> extends ModuleExtension<T> {
    /**
     * Looks up the first enabled persistence extension on the module that owns {@code element}.
     * Used by language injectors to pick a dialect (HQL / JPQL / SQL) based on which
     * persistence framework is wired into the module.
     */
    @Nullable
    @RequiredReadAction
    @SuppressWarnings({"rawtypes", "unchecked"})
    static PersistenceModuleExtension<?, ?> findExtension(@Nonnull PsiElement element) {
        return ModuleUtilCore.getExtension(element, PersistenceModuleExtension.class);
    }

    public abstract ConfigFile[] getDescriptors();

    public abstract ConfigFileContainer getDescriptorsContainer();

    @Nonnull
    public abstract List<Unit> getPersistenceUnits();

    @Nullable
    public abstract PersistenceMappings getAnnotationEntityMappings();

    @Nonnull
    public abstract PersistenceMappings getEntityMappings(@Nonnull final Unit unit);

    @Nonnull
    public abstract List<? extends PersistenceMappings> getDefaultEntityMappings(@Nonnull final Unit unit);

    @Nonnull
    public abstract Class<? extends PersistencePackage> getPersistenceUnitClass();

    @Nonnull
    public abstract Map<ConfigFileMetaData, Class<? extends PersistenceMappings>> getSupportedDomMappingFormats();

    public abstract String getDataSourceId(@Nonnull final Unit unit);

    public abstract void setDataSourceId(@Nonnull final Unit unit, final String dataSourceId);

    /**
     * @return the SQL dialect ({@link LanguageVersion}) that the module's persistence
     * framework parses at runtime — e.g. HQL for Hibernate, JPQL for plain JPA. Used
     * by query-string injection to pick the right highlighter/parser. {@code null}
     * means no preference, in which case injectors use plain SQL.
     */
    @Nullable
    public abstract LanguageVersion getQlLanguage();

    @Nonnull
    public abstract ModelValidator getModelValidator(@Nullable final Unit unit);

    @Nonnull
    public abstract Class[] getInspectionToolClasses();

    @Nonnull
    public abstract PersistencePackageDefaults getPersistenceUnitDefaults(@Nonnull final Unit unit);
}
