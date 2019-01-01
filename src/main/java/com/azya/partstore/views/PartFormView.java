package com.azya.partstore.views;

import com.azya.partstore.models.PartType;
import com.azya.partstore.services.PartService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

public class PartFormView extends FormLayout {
    private ComboBox<PartType> cmbPartType = new ComboBox<>("Тип комплектующего");
    private TextField tfName = new TextField("Название");
    private TextField tfCount = new TextField("Количество");
    private MainView mainView;
    private PartService partService;

    public PartFormView(MainView mainView) {
        this.mainView = mainView;
        partService = mainView.getPartService();
        cmbPartType.setItems(PartType.values());
        add(cmbPartType,tfName,tfCount);
    }
}
