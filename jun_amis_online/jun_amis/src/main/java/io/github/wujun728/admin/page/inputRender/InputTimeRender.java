package io.github.wujun728.admin.page.inputRender;

import io.github.wujun728.admin.page.data.InputField;

import java.util.Map;

public class InputTimeRender  extends InputDefaultRender{
    @Override
    protected void extra(Map<String, Object> config, InputField field) {
        config.put("format","HH:mm:ss");
        config.put("timeFormat","HH:mm:ss");
        config.put("inputFormat","HH:mm:ss");
    }
}
