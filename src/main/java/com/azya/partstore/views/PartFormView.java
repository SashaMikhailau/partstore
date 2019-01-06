package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.models.PartType;
import com.azya.partstore.services.PartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

public class PartFormView extends FormLayout {
    private ComboBox<PartType> type = new ComboBox<>("Тип комплектующего");
    private TextField name = new TextField("Название");
    private TextField count = new TextField("Количество");
    private MainView mainView;
    private Button btnSave = new Button("Сохранить");
    private Button btnDelete = new Button("Удалить");

    private Part part;


    private Binder<Part> binder = new Binder<>(Part.class);

    public PartFormView(MainView mainView) {
        this.mainView = mainView;
        //partService = mainView.getPartService();
        type.setItems(PartType.values());
        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
        add(type, name, count,buttons);
        binder.forField(count)
                .withConverter(Integer::valueOf, String::valueOf)
                .bind(Part::getCount, Part::setCount);
        binder.bind(name, Part::getName, Part::setName);
        binder.forField(type)
                .withConverter(partType -> partType,partType -> partType)
                .bind(Part::getType, Part::setType);
        setPart(null);
        btnSave.addClickListener((event) -> this.save());
        btnDelete.addClickListener((event) -> this.delete());


    }

    public void setPart(Part part) {
        this.part = part;
        binder.setBean(part);
        boolean enabled = part != null;
        btnSave.setEnabled(enabled);
        btnDelete.setEnabled(enabled);
        if (enabled) {
            name.focus();
        }
    }

    private void save(){
        mainView.getPartService().updatePart(part);
        mainView.updateGrid();
        setPart(null);
    }

    private void delete(){
        mainView.getPartService().deletePart(part);
        mainView.updateGrid();
        setPart(null);
    }
}
