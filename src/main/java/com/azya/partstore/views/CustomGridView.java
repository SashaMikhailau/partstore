package com.azya.partstore.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.ValueProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomGridView<T> extends VerticalLayout {
    private final Grid<T> grid = new Grid();
    private final MainView mainView;
    private HorizontalLayout buttonLine = new HorizontalLayout();
    private int itemsPerPage;
    private static final int BUTTONSBUFFER = 7;
    private int currentPage = 1;

    public CustomGridView(int itemsPerPage,MainView mainView) {
        super();
        this.mainView = mainView;
        grid.setHeightByRows(true);
        add(grid,buttonLine);
        this.itemsPerPage = itemsPerPage;

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setItems(Collection<T> items) {
        int maxPage = (items.size()-1)/itemsPerPage+1;
        List<T> partList = new ArrayList<>(items);
        if (currentPage >= maxPage) {
            currentPage = maxPage;
            if (!partList.isEmpty()) {
                grid.setItems(partList.subList((currentPage - 1) * itemsPerPage, partList.size()));
            } else {
                grid.setItems();
            }
        } else {
            grid.setItems(partList.subList((currentPage - 1) * itemsPerPage, currentPage*itemsPerPage));
        }
        setButtons(maxPage);
    }

    private void setButtons(int maxPage) {
        buttonLine.removeAll();
        int lowLimit = currentPage - BUTTONSBUFFER > 0 ? currentPage - BUTTONSBUFFER : 0;
        int highLimit = currentPage + BUTTONSBUFFER <maxPage ? currentPage + BUTTONSBUFFER : maxPage;
        for (int i = lowLimit; i < highLimit; i++) {
            Button button = new Button(String.valueOf(i + 1));
            button.addClickListener(event ->{
                currentPage = Integer.parseInt(button.getText());
                mainView.updateGrid();

            });
            buttonLine.add(button);
            if (i + 1 == currentPage) {
                button.setEnabled(false);
            }
        }
    }


    public Grid.Column<T> addColumn(ValueProvider<T,?> valueProvider) {
        return grid.addColumn(valueProvider);
    }

    public Grid<T> getGrid() {
        return grid;
    }

    public Grid.Column<T> addColumn(Renderer<T> renderer) {
        return grid.addColumn(renderer);
    }

    public SingleSelect<Grid<T>,T> asSingleSelect() {
        return grid.asSingleSelect();
    }

    public FooterRow appendFooterRow() {
        return grid.appendFooterRow();
    }

    public List<FooterRow> getFooterRows() {
        return grid.getFooterRows();
    }
}
