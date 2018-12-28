package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.services.PartService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    private PartService partService;

    private final PaginatedGrid<Part> grid = new PaginatedGrid<>();
    private final TextField filter = new TextField();

    public MainView(PartService partService) {
        this.partService = partService;
        initGrid();
        add(filter,grid);
        setHeight("100vh");
    }
    private void initGrid() {
        filter.setPlaceholder("поиск части");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(value -> updateGrid());
        grid.setSizeFull();
        grid.addColumn(Part::getId).setHeader("ID");
        grid.addColumn(part -> part.getType().toString()).setHeader("Тип");
        grid.addColumn(Part::getName).setHeader("Название");
        grid.addColumn(Part::getCount).setHeader("Количество");
        grid.addColumn(part->part.getType().isNeeded()?"Да":"Нет").setHeader("Нужно");
        grid.appendFooterRow();
        updateGrid();
        grid.setPageSize(10);
    }

    private void updateGrid() {
        String searchText = filter.getValue();
        List<Part> parts = partService.getAllParts(searchText);
        grid.setItems(parts);
        grid.setPaginatorSize(parts.size()/10+1);
        updateFooterInfo();
    }

    private void updateFooterInfo() {
        grid.getFooterRows().get(0).getCells().get(0).setText(String.format("Всего можно собрать %d компьютеров",
                partService.getAvailableComputersCount()));
    }


}
