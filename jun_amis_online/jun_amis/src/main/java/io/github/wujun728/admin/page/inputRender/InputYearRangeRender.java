package io.github.wujun728.admin.page.inputRender;

import io.github.wujun728.admin.page.data.InputField;

import java.util.Map;

public class InputYearRangeRender  extends InputDefaultRender{
    @Override
    protected void extra(Map<String, Object> config, InputField field) {
        config.put("format","YYYY");
        config.put("inputFormat","YYYY");
    }
}
