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

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private PartService partService;
    private CustomGridView<Part> gridView = new CustomGridView(10,this);
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
                gridView.setWidth("100%");
                partFormView.setWidth("100%");
                add(filterLine, gridView,partFormView);
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
            gridView.getGrid().asSingleSelect().clear();
            partFormView.setPart(new Part());
        });
        partFormView.setEnabled(false);
        gridView.addColumn(Part::getId).setHeader("ID").setWidth("10%");
        gridView.addColumn(part -> part.getType().toString()).setHeader("Тип").setWidth("20%");
        gridView.addColumn(Part::getName).setHeader("Название").setWidth("40%");
        gridView.addColumn(Part::getCount).setHeader("Количество").setWidth("10%");
        gridView.addColumn(part -> part.getType().isNeeded() ? "Да" : "Нет").setHeader("Нужно").setWidth("10%");
        gridView.addColumn(new ComponentRenderer<>((Part selectedPart) -> {
            Button btnRemove = new Button(VaadinIcon.TRASH.create(), event -> {
                partService.deletePart(selectedPart);
                updateGrid();
                partFormView.setEnabled(false);
                Notification.show("Запись удалена", 1000, Notification.Position.MIDDLE);

            });
            return new HorizontalLayout(btnRemove);
        })).setWidth("10%");
        gridView.appendFooterRow();
        updateGrid();

        gridView.asSingleSelect().addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Grid<Part>, Part>>() {
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
        gridView.setItems(parts);
        rgPartViewMode.setValue(partService.getPartViewMode());
        updateFooterInfo();
        gridView.asSingleSelect().clear();
    }

    private void updateFooterInfo() {
        gridView.getFooterRows().get(0).getCells().get(2).setText(String.format("Всего можно собрать %d компьютеров",
                partService.getAvailableComputersCount()));
    }


}
