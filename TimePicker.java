/* TimePicker Class
*
*   Written by Frances Belleza
*   Edited by Hari Sowmith Reddy
*
*   This class is used in TimeSlotsPage
*
* */

package s25.cs151.application;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class TimePicker extends HBox {
    private ComboBox<Integer> hourBox, minuteBox;
    private ComboBox<String> amPmBox;

    public TimePicker() {
        hourBox = new ComboBox<>();

        for(int x = 1; x <= 12; x++) {
            hourBox.getItems().add(x);
            hourBox.setValue(0);
        }

        minuteBox = new ComboBox<>();
        minuteBox.getItems().addAll(0, 15, 30, 45);
        minuteBox.setValue(0);

        amPmBox = new ComboBox<>();
        amPmBox.getItems().addAll("AM", "PM");
        amPmBox.setValue("AM");

        this.getChildren().addAll(hourBox, minuteBox, amPmBox);
        this.setSpacing(5);

    }

    public String getFormattedTime() {
        return String.format("%02d:%02d %s", hourBox.getValue(), minuteBox.getValue(), amPmBox.getValue());
    }

    public Integer getHour() {
        return hourBox.getValue();
    }

    public Integer getMinute() {
        return minuteBox.getValue();
    }

    public String getAmPm() {
        return amPmBox.getValue();
    }


}
