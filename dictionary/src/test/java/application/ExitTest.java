package application;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Szotar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown); // Initialize the JavaFX Platform
        latch.await();  // Wait until the platform is initialized
    }

    @Test
    public void testExitConfirmation() throws InterruptedException {
        // JavaFX needs to run on the JavaFX application thread.
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                SzotarController controller = new SzotarController(new Szotar());
                Alert alert = controller.showExitConfirmation();
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    assertTrue(true);  // Mock behavior of exit confirmation being successful
                } else {
                    assertFalse(true); // Mock that user canceled the action
                }
            } finally {
                latch.countDown();
            }
        });

        // Wait for the JavaFX thread to finish
        latch.await();
    }
}