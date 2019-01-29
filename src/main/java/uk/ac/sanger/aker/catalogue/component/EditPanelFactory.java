package uk.ac.sanger.aker.catalogue.component;

import uk.ac.sanger.aker.catalogue.CatalogueApp;
import uk.ac.sanger.aker.catalogue.model.*;

import java.util.Objects;

/**
 * @author dr6
 */
public class EditPanelFactory {
    private CatalogueApp app;

    public EditPanelFactory(CatalogueApp app) {
        this.app = app;
    }

    @SuppressWarnings("unchecked")
    public <E> EditPanel<E> makePanel(E item) {
        Objects.requireNonNull(item, "makePanel received null");
        if (item instanceof Module) {
            return (EditPanel) new ModulePanel((Module) item, app);
        }
        if (item instanceof AkerProcess) {
            return (EditPanel) new ProcessPanel((AkerProcess) item, app);
        }
        if (item instanceof Product) {
            return (EditPanel) new ProductPanel((Product) item, app);
        }
        throw new IllegalArgumentException("makePanel received unexpected item: "+item);
    }
}
