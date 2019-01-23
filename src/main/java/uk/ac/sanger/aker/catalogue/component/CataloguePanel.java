package uk.ac.sanger.aker.catalogue.component;

import uk.ac.sanger.aker.catalogue.CatalogueApp;
import uk.ac.sanger.aker.catalogue.component.list.*;
import uk.ac.sanger.aker.catalogue.model.*;

import javax.swing.JTextField;
import java.awt.*;

import static uk.ac.sanger.aker.catalogue.component.ComponentFactory.makeHeadline;
import static uk.ac.sanger.aker.catalogue.component.ComponentFactory.makeTextField;

/**
 * @author dr6
 */
public class CataloguePanel extends EditPanel {
    private CatalogueApp app;
    private JTextField pipelineField;
    private JTextField urlField;
    private JTextField limsIdField;
    private ListComponent<Module> moduleList;
    private ListComponent<AkerProcess> processList;
    private ListComponent<Product> productList;

    public CataloguePanel(CatalogueApp app) {
        this.app = app;
        initComponents();
        load();
        layOut();
    }

    private void initComponents() {
        pipelineField = makeTextField();
        urlField = makeTextField();
        limsIdField = makeTextField();
        moduleList = new ListComponent<>("Modules:", new ModuleListActor(app));
        processList = new ListComponent<>("Processes:", new ProcessListActor(app));
        productList = new ListComponent<>("Products:", new ProductListActor(app));
    }

    public void load() {
        Catalogue catalogue = getCatalogue();
        pipelineField.setText(catalogue.getPipeline());
        urlField.setText(catalogue.getUrl());
        limsIdField.setText(catalogue.getLimsId());
        moduleList.setItems(catalogue.getModules());
        processList.setItems(catalogue.getProcesses());
        productList.setItems(catalogue.getProducts());
    }

    private void layOut() {
        setLayout(new GridBagLayout());
        Insets insets = new Insets(10,0,10,0);
        QuickConstraints constraints = new QuickConstraints(0,0,2,1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 0, 0);
        add(makeHeadline("Catalogue"), constraints);

        constraints.insets.left = 10;
        constraints.gridwidth = 1;
        add("Pipeline:", constraints.incy().left());
        add(pipelineField, constraints.right());

        add("URL:", constraints.incy().left());
        add(urlField, constraints.right());

        add("LIMS ID:", constraints.incy().left());
        add(limsIdField, constraints.right());

        constraints.left();
        constraints.gridwidth = 2;
        add(moduleList, constraints.incy());
        add(processList, constraints.incy());
        add(productList, constraints.incy());

        setMinimumSize(getPreferredSize());
    }

    @Override
    protected void updateState() {
        //TODO
    }

    public AkerProcess getSelectedProcess() {
        return processList.getSelectedItem();
    }

    public Catalogue getCatalogue() {
        return app.getCatalogue();
    }

    public void modulesUpdated() {
        moduleList.repaint();
    }

    public void productsUpdated() {
        productList.repaint();
    }
}
