package app.pages.home;

import java.io.IOException;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class page {
    public void switchRoute(ActionEvent event) throws IOException {
        Object source = event.getSource();
        if (source instanceof Button) {
            Button castSource = (Button) source;
            String route = castSource.getText().toLowerCase();
            FXRouter.goTo(route);
        }
    }
}
