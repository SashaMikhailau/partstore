package com.azya.partstore.views;

import com.azya.partstore.PartRepository;
import com.azya.partstore.models.Part;
import com.azya.partstore.services.PartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.incubator.Paginator;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    private static final int ROWSNUMBERPERPAGE = 10;

    private PartService partService;

    final Grid<Part> grid = new Grid<>();
    final Paginator gridPaginator = new Paginator();

    public MainView(PartService partService) {
        this.partService = partService;

        grid.setSizeFull();
        grid.addColumn(Part::getId).setHeader("ID");
        grid.addColumn(part -> part.getType().toString()).setHeader("Тип");
        grid.addColumn(Part::getName).setHeader("Название");
        grid.addColumn(Part::getCount).setHeader("Количество");
        grid.addColumn(part->part.getType().isNeeded()?"Да":"Нет").setHeader("Нужно");
        grid.setPageSize(10);

        gridPaginator.setFirstLabel("В начало");
        gridPaginator.setLastLabel("В конец");
        gridPaginator.addChangeSelectedPageListener(event -> updateList(event.getPage()));
        grid.appendFooterRow();
        add(grid,gridPaginator);

        setHeight("50vh");
        updateList(1);
    }

    private void updateList(int pageNumber) {
        List<Part> parts = partService.getAllParts();
        int endCount = Math.min(pageNumber * ROWSNUMBERPERPAGE,parts.size());
        int startCount = Math.min(pageNumber * ROWSNUMBERPERPAGE-10,parts.size());
        grid.setItems(parts.subList(startCount,endCount));
        gridPaginator.setNumberOfPages(parts.size()/ROWSNUMBERPERPAGE+1);
        updateFooterInfo();
    }

    private void updateFooterInfo() {
        grid.getFooterRows().get(0).getCells().get(0).setText(String.format("Всего можно собрать %d компьютеров",
                partService.getAvailableComputersCount()));
    }


}
