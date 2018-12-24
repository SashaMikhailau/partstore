package com.azya.partstore.views;

import com.azya.partstore.PartRepository;
import com.azya.partstore.models.Part;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    private PartRepository partRepository;
    final Grid<Part> grid = new Grid<>();

    public MainView(PartRepository partRepository) {
        this.partRepository = partRepository;
        grid.setSizeFull();
        grid.addColumn(Part::getId).setHeader("ID");
        grid.addColumn(Part::getName).setHeader("Название");
        grid.addColumn(Part::getCount).setHeader("Количество");
        grid.addColumn(Part::getNeeded).setHeader("Нужно");
        add(grid);
        setHeight("100vh");
        updateList();
    }

    private void updateList() {
        grid.setItems((Collection<Part>)partRepository.findAll());
    }


}
