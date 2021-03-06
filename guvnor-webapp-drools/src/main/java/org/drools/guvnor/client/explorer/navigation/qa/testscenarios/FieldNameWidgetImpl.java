package org.drools.guvnor.client.explorer.navigation.qa.testscenarios;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.kie.uberfirebootstrap.client.widgets.SmallLabel;

public class FieldNameWidgetImpl implements IsWidget {

    private SmallLabel view = new SmallLabel();

    public FieldNameWidgetImpl(String fieldName) {
        view.setText(fieldName + ":");
        view.addClickHandler(createClickHandler());
    }

    private ClickHandler createClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                //TODO: -Rikkola-
            }
        };
    }

    @Override
    public Widget asWidget() {
        return view;
    }
}
