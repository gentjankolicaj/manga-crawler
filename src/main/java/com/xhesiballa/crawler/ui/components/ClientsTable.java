package com.xhesiballa.crawler.ui.components;

import com.xhesiballa.crawler.interfaces.Client;
import com.xhesiballa.crawler.model.Manga;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ClientsTable extends TableView {
    private Consumer<Client> onSelectedClientChange;

    public ClientsTable(List<Client> clientList){
        TableColumn<Client, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("providerName"));
        nameCol.prefWidthProperty().bind(widthProperty().multiply(0.25));

        TableColumn<Client, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("providerURL"));
        addressCol.prefWidthProperty().bind(widthProperty().multiply(0.75));

        getColumns().addAll(nameCol, addressCol);

        ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(clientList);

        setItems(clientsObservableList);

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
               new Thread(() -> {
                    Client client = (Client) getSelectionModel().getSelectedItem();
                    onSelectedClientChange.accept(client);
                }).start();
            }
        });
    }

    public void setOnSelectedClientChange(Consumer<Client> onSelectedClientChange) {
        this.onSelectedClientChange = onSelectedClientChange;
    }
}
