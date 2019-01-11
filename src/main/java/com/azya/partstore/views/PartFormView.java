package com.azya.partstore.views;

import com.azya.partstore.models.Part;
import com.azya.partstore.models.PartType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BinderValidationStatusHandler;
import com.vaadin.flow.data.binder.BindingValidationStatus;

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
        line.setWidth("100%");
        type.setWidth("25%");
        name.setWidth("25%");
        count.setWidth("25%");
        btnSave.setWidth("25%");
        add(line);
        binder.forField(count)
                .withValidator(fieldValue-> Integer.valueOf(fieldValue)>0,"Число должно быть больше 0")
                .withConverter(Integer::valueOf, String::valueOf)
                .bind(Part::getCount, Part::setCount);
        binder.forField(name)
                .withValidator(fieldValue-> fieldValue.length()>3,"Длина названия должна быть не менее трех символов")
                .bind(Part::getName, Part::setName);
        binder.forField(type)
                .asRequired("Тип должен быть определен")
                .bind(Part::getType, Part::setType);
        binder.setValidationStatusHandler(event -> handleValidationErrorMessage(event));
        setPart(null);
        btnSave.addClickListener((event) -> this.tryToSave());

    }

    private void handleValidationErrorMessage(BinderValidationStatus<Part> bindingValidationStatus) {
        if (bindingValidationStatus.hasErrors()&&part!=null) {
            bindingValidationStatus.getFieldValidationErrors()
                    .forEach(item -> item.getMessage().ifPresent(message->Notification.show(message, 1000, Notification.Position.MIDDLE)));
        }
    }

    public void setPart(Part part) {
        this.part = part;
        binder.setBean(part);
        boolean enabled = part != null;
        if (enabled) {
            name.focus();
        }
    }

    private void tryToSave(){
        if (binder.validate().hasErrors()) {
            return;
        }
        mainView.getPartService().updatePart(part);
        mainView.updateGrid();
        setPart(null);
        setEnabled(false);
        Notification.show("Запись сохранена", 1000, Notification.Position.MIDDLE);
    }



}
