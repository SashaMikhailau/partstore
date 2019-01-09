package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.services.PartService;
import com.azya.partstore.services.PartViewMode;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private PartService partService;
    private PaginatedGrid<Part> grid = new PaginatedGrid<>();
    private TextField tvFilter = new TextField();
    private Button btnAdd = new Button("Добавить", VaadinIcon.PLUS.create());
    private RadioButtonGroup<PartViewMode> rgPartViewMode = new RadioButtonGroup<>();
    private PartFormView partFormView = new PartFormView(this);

    public PartService getPartService() {
        return partService;
    }

    public MainView(PartService partService) {
                this.partService = partService;
                initGrid();
                HorizontalLayout filterLine = new HorizontalLayout(tvFilter,rgPartViewMode,btnAdd);
                grid.setWidth("100%");
                partFormView.setWidth("100%");
                add(filterLine,grid,partFormView);
                setSizeFull();
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
        btnAdd.addClickListener(buttonClickEvent ->{
            partFormView.setEnabled(true);
            grid.asSingleSelect().clear();
            partFormView.setPart(new Part());
        });
        partFormView.setEnabled(false);
        grid.addColumn(Part::getId).setHeader("ID").setWidth("10%");
        grid.addColumn(part -> part.getType().toString()).setHeader("Тип").setWidth("20%");
        grid.addColumn(Part::getName).setHeader("Название").setWidth("40%");
        grid.addColumn(Part::getCount).setHeader("Количество").setWidth("10%");
        grid.addColumn(part -> part.getType().isNeeded() ? "Да" : "Нет").setHeader("Нужно").setWidth("10%");
        grid.addColumn(new ComponentRenderer<>((Part selectedPart) -> {
            Button btnRemove = new Button(VaadinIcon.TRASH.create(), event -> {
                partService.deletePart(selectedPart);
                updateGrid();
                partFormView.setEnabled(false);
                Notification.show("Запись удалена", 1000, Notification.Position.MIDDLE);

            });
            return new HorizontalLayout(btnRemove);
        })).setWidth("10%");
        grid.appendFooterRow();
        updateGrid();
        grid.setPageSize(10);
        grid.asSingleSelect().addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Grid<Part>, Part>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<Grid<Part>, Part> event) {
                if (event.getValue() != null) {
                    //Notification.show(event.getValue().toString(),1000, Notification.Position.MIDDLE);
                    partFormView.setEnabled(true);
                    partFormView.setPart(event.getValue());

                }

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
        grid.asSingleSelect().clear();
    }

    private void updateFooterInfo() {
        grid.getFooterRows().get(0).getCells().get(2).setText(String.format("Всего можно собрать %d компьютеров",
                partService.getAvailableComputersCount()));
    }


}
