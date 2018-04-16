package com.zhihuishu.springboot.springboothello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(Map<String, Object> map) {
        map.put("name","美丽的天使...");
        return"index";
    }

    @RequestMapping("/freemarkerIndex")
    public String freemarkerIndex(Map<String, Object>result) {
        result.put("name", "yushengjun");
        result.put("sex", "0");
        List<String> listResult = new ArrayList<String>();
        listResult.add("zhangsan");
        listResult.add("lisi");
        listResult.add("itmayiedu");
        result.put("listResult", listResult);
        return"index";
    }



}
