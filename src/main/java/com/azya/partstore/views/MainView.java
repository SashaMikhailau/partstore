package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.services.PartService;
import com.azya.partstore.services.PartViewMode;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private PartService partService;
    private final PaginatedGrid<Part> grid = new PaginatedGrid<>();
    private final TextField tvFilter = new TextField();
    private RadioButtonGroup<PartViewMode> rgPartViewMode = new RadioButtonGroup<>();
    private PartFormView partFormView = new PartFormView(this);

    public PartService getPartService() {
        return partService;
    }

    public MainView(PartService partService) {
                this.partService = partService;
                initGrid();
                HorizontalLayout filterLine = new HorizontalLayout(tvFilter,rgPartViewMode);
                grid.setSizeFull();
                add(filterLine,grid,partFormView);
                setSizeFull();
        ;
    }
    private void initGrid() {
        tvFilter.setPlaceholder("поиск части");
        tvFilter.setValueChangeMode(ValueChangeMode.EAGER);
        tvFilter.addValueChangeListener(value -> updateGrid());
        rgPartViewMode.setItems(PartViewMode.values());
        rgPartViewMode.addValueChangeListener(event -> {
            partService.setPartViewMode(event.getValue());
            updateGrid();
        });
        grid.addColumn(Part::getId).setHeader("ID");
        grid.addColumn(part -> part.getType().toString()).setHeader("Тип");
        grid.addColumn(Part::getName).setHeader("Название");
        grid.addColumn(Part::getCount).setHeader("Количество");
        grid.addColumn(part->part.getType().isNeeded()?"Да":"Нет").setHeader("Нужно");
        grid.appendFooterRow();
        updateGrid();
        grid.setPageSize(10);
        grid.asSingleSelect().addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Grid<Part>, Part>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<Grid<Part>, Part> event) {
                partFormView.setPart(event.getValue());
            }
        });

    }

    void updateGrid() {
        String searchText = tvFilter.getValue();
        List<Part> parts = partService.getAllParts(searchText);
        grid.setItems(parts);
        grid.setPaginatorSize(parts.size()/10+1);
        rgPartViewMode.setValue(partService.getPartViewMode());
        updateFooterInfo();
    }

    private void updateFooterInfo() {
        grid.getFooterRows().get(0).getCells().get(0).setText(String.format("Всего можно собрать %d компьютеров",
                partService.getAvailableComputersCount()));
    }


}
