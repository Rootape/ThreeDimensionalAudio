package com.tcc.editor;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope (value = "session")
@Component (value = "devicesController")
@ELBeanName(value = "devicesController")
@Join(path = "/", to = "/views/devices-view.xhtml")
public class DevicesController {
    private String value = "This editor is provided by PrimeFaces";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
