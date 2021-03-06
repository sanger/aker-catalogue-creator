package uk.ac.sanger.aker.catalogue.component;

import uk.ac.sanger.aker.catalogue.CatalogueApp;
import uk.ac.sanger.aker.catalogue.model.Module;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static uk.ac.sanger.aker.catalogue.component.ComponentFactory.makeHeadline;
import static uk.ac.sanger.aker.catalogue.component.ComponentFactory.makeTextField;

/**
 * A panel for editing a module.
 * Includes fields for editing the name and parameter bounds.
 * @author dr6
 */
public class ModulePanel extends EditPanel {
    private Module module;

    private JLabel headlineLabel;
    private JTextField nameField;
    private CheckedSpinner minField;
    private CheckedSpinner maxField;

    private CatalogueApp app;

    private boolean loading;

    public ModulePanel(Module module, CatalogueApp app) {
        this.module = module;
        this.app = app;
        initComponents();
        layOut();
        updateState();
    }

    private void initComponents() {
        ChangeListener cl = getChangeListener();
        headlineLabel = makeHeadline("Module");
        nameField = makeTextField();
        minField = new CheckedSpinner(null);
        maxField = new CheckedSpinner(null);
        load();
        minField.addChangeListener(cl);
        maxField.addChangeListener(cl);
        nameField.getDocument().addDocumentListener(getDocumentListener());
    }

    private void layOut() {
        setLayout(new GridBagLayout());
        QuickConstraints constraints = new QuickConstraints(new Insets(10,0,10,0));
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(headlineLabel, constraints);
        constraints.insets.left = 10;
        constraints.gridwidth = 1;
        add("Name:", nameField, constraints.incy());
        add("Min value:", minField, constraints.incy());
        add("Max value:", maxField, constraints.incy());
    }

    @Override
    protected void updateState() {
        save();
    }

    @Override
    protected void load() {
        if (loading) {
            return;
        }
        loading = true;
        headlineLabel.setText("Module: "+module.getName());
        nameField.setText(module.getName());
        minField.setValue(module.getMinValue());
        maxField.setValue(module.getMaxValue());
        loading = false;
    }

    protected void save() {
        if (loading) {
            return;
        }
        module.setName(nameField.getText());
        module.setMinValue(minField.getValue());
        module.setMaxValue(maxField.getValue());
        headlineLabel.setText("Module: "+module.getName());
        app.modulesUpdated();
    }

    @Override
    public void fireOpen() {
        nameField.requestFocusInWindow();
    }

}
