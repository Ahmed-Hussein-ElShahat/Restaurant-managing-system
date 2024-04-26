package Backend;

import javafx.beans.property.SimpleBooleanProperty;

public interface Available {
    boolean checkIfAvailable();
    SimpleBooleanProperty getAvailableProperty();
}
