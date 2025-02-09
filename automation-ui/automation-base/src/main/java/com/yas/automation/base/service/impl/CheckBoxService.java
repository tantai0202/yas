package com.yas.automation.base.service.impl;

import com.yas.automation.base.service.InputService;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class CheckBoxService implements InputService {
    @Override
    public void setValue(WebElement webElement, Object value) {
        webElement.click();
    }
}
