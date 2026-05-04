package consulo.hibernate.module.extension;

import com.intellij.persistence.model.PersistencePackage;
import consulo.annotation.access.RequiredReadAction;
import consulo.java.persistence.module.extension.PersistenceModuleExtension;
import consulo.language.util.ModuleUtilCore;
import consulo.module.Module;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Module-extension form of the legacy {@link com.intellij.hibernate.facet.HibernateFacet}.
 *
 * @author VISTALL
 * @since 2026-05-04
 */
public interface HibernateModuleExtension extends PersistenceModuleExtension<HibernateModuleExtension, PersistencePackage> {

    @Nullable
    @RequiredReadAction
    static HibernateModuleExtension getInstance(@Nonnull Module module) {
        return ModuleUtilCore.getExtension(module, HibernateModuleExtension.class);
    }
}
