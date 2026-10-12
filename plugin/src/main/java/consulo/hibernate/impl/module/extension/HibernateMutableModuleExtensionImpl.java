package consulo.hibernate.impl.module.extension;

import consulo.disposer.Disposable;
import consulo.hibernate.module.extension.HibernateModuleExtension;
import consulo.module.content.layer.ModuleRootLayer;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.layout.VerticalLayout;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;


/**
 * @author VISTALL
 * @since 2026-05-04
 */
public class HibernateMutableModuleExtensionImpl extends HibernateModuleExtensionImpl
        implements HibernateMutableModuleExtension {

    public HibernateMutableModuleExtensionImpl(@Nonnull String id, @Nonnull ModuleRootLayer moduleRootLayer) {
        super(id, moduleRootLayer);
    }

    @Override
    public void setEnabled(boolean enabled) {
        myIsEnabled = enabled;
    }

    @Override
    public boolean isModified(@Nonnull HibernateModuleExtension other) {
        return myIsEnabled != other.isEnabled();
    }

    @RequiredUIAccess
    @Nullable
    @Override
    public Component createConfigurationComponent(@Nonnull Disposable disposable, @Nonnull Runnable runnable) {
        return VerticalLayout.create().add(Label.create("Hibernate"));
    }
}
