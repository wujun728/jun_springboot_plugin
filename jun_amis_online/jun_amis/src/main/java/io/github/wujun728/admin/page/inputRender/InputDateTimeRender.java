package io.github.wujun728.admin.page.inputRender;

import io.github.wujun728.admin.page.data.InputField;

import java.util.Map;

public class InputDateTimeRender extends InputDefaultRender {
    @Override
    protected void extra(Map<String, Object> config, InputField field) {
        config.put("format","YYYY-MM-DD HH:mm:ss");
    }
}
