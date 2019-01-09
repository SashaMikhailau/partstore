package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.models.PartType;
import com.azya.partstore.services.PartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class PartFormView extends FormLayout {
    private ComboBox<PartType> type = new ComboBox<>("Тип комплектующего");
    private TextField name = new TextField("Название");
    private TextField count = new TextField("Количество");
    private MainView mainView;
    private Button btnSave = new Button("Сохранить");
    private Part part;


    private Binder<Part> binder = new Binder<>(Part.class);

    public PartFormView(MainView mainView) {
        this.mainView = mainView;
        type.setItems(PartType.values());
        HorizontalLayout line = new HorizontalLayout(type, name, count, btnSave);
        add(line);
        binder.forField(count)
                .withConverter(Integer::valueOf, String::valueOf)
                .bind(Part::getCount, Part::setCount);
        binder.bind(name, Part::getName, Part::setName);
        binder.bind(type,Part::getType, Part::setType);
        setPart(null);
        btnSave.addClickListener((event) -> this.save());

    }

    public void setPart(Part part) {
        this.part = part;
        binder.setBean(part);
        boolean enabled = part != null;
        btnSave.setEnabled(enabled);
        if (enabled) {
            name.focus();
        }
    }

    private void save(){
        mainView.getPartService().updatePart(part);
        mainView.updateGrid();
        setPart(null);
        setEnabled(false);
        Notification.show("Запись сохранена", 1000, Notification.Position.MIDDLE);
    }

}
