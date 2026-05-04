package consulo.hibernate.impl.module.extension;

import com.intellij.hibernate.icon.icon.HibernateIconGroup;
import consulo.annotation.component.ExtensionImpl;
import consulo.hibernate.module.extension.HibernateModuleExtension;
import consulo.localize.LocalizeValue;
import consulo.module.content.layer.ModuleExtensionProvider;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.module.extension.MutableModuleExtension;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author VISTALL
 * @since 2026-05-04
 */
@ExtensionImpl
public class HibernateModuleExtensionProviderImpl implements ModuleExtensionProvider<HibernateModuleExtension> {

    @Nonnull
    @Override
    public String getId() {
        return "hibernate";
    }

    @Nullable
    @Override
    public String getParentId() {
        return "java";
    }

    @Nonnull
    @Override
    public LocalizeValue getName() {
        return LocalizeValue.localizeTODO("Hibernate");
    }

    @Nonnull
    @Override
    public Image getIcon() {
        return HibernateIconGroup.hibernate();
    }

    @Nonnull
    @Override
    public HibernateModuleExtension createImmutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new HibernateModuleExtensionImpl(getId(), moduleRootLayer);
    }

    @Nonnull
    @Override
    public MutableModuleExtension<HibernateModuleExtension> createMutableExtension(@Nonnull ModuleRootLayer moduleRootLayer) {
        return new HibernateMutableModuleExtensionImpl(getId(), moduleRootLayer);
    }
}
