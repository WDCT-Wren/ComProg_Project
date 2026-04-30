package org.group1.GamePackage.UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.stage.Stage;
import org.group1.GamePackage.Factory.InstructionPageFactory;

public class InstructionController {

    @FXML
    private Label titleLabel;

    @FXML
    private Button closeButton;

    @FXML
    private Pagination pagination;

    /**
     * Initializes the InstructionController by creating an InstructionPageFactory object
     * and setting the pagination's page factory to display pages
     */
    public void initialize() {
        InstructionPageFactory Interface = new InstructionPageFactory();
        pagination.setPageFactory(pageIndex -> switch (pageIndex) {
            case 0 -> Interface.showFirstPage(titleLabel);
            case 1 -> Interface.showSecondPage(titleLabel);
            case 2 -> Interface.showThirdPage(titleLabel);
            case 3 -> Interface.showFourthPage(titleLabel);
            case 4 -> Interface.showFifthPage(titleLabel);
            case 5 -> Interface.showSixthPage(titleLabel);
            case 6 -> Interface.showSeventhPage(titleLabel);
            default -> null;
        });
    }

    /**
     * Navigates to the next page in the pagination by incrementing the current page index
     */
    @FXML
    void nextPage() {
        int nextPage = pagination.getCurrentPageIndex() + 1;
        if (nextPage < pagination.getPageCount()) {
            pagination.setCurrentPageIndex(nextPage);
        }
    }

    /**
     * Navigates to the previous page in the pagination by decrementing the current page index
     */
    @FXML
    void previousPage() {
        int prevPage = pagination.getCurrentPageIndex() - 1;
        if (prevPage >= 0) {
            pagination.setCurrentPageIndex(prevPage);
        }
    }

    /**
     * Closes the current window
     */
    @FXML
    void closeWindow() {
        ((Stage)closeButton.getScene().getWindow()).close();
    }
}
